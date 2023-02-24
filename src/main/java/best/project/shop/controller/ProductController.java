package best.project.shop.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import best.project.shop.helper.ValidatorResult;
import best.project.shop.model.Product;
import best.project.shop.model.Roles;
import best.project.shop.model.User;
import best.project.shop.model.Сommodity;
import best.project.shop.service.ProductService;
import best.project.shop.service.ProductValidationService;

@Controller
public class ProductController {
	
	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

	private ProductService productService;
	
	private ProductValidationService productValidationService;
	
	public ProductController(ProductService productService, ProductValidationService productValidationService) {
		this.productService = productService;
		this.productValidationService = productValidationService;
	}

	@GetMapping("/products")
	public String viewHomePage(Model model, HttpSession session) {
		List<Product> products = null;
		User user = (User) session.getAttribute("loginUser");
		if (null != user && Roles.ADMIN.roleName.equals(user.getRole())) {
			products = productService.findAllProduct();
		} else {
			products = productService.findAllEnableProduct();
		}
		model.addAttribute("products", products);
		return "products";
	}
	
	@GetMapping("/product/edit/{id}")
	public String editProduct(@PathVariable("id") Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		try {
			User user = (User) session.getAttribute("loginUser");
			if (null != user && Roles.ADMIN.roleName.equals(user.getRole())) {
				redirect = "productProfile";
				model.addAttribute("commodity", productService.getCommodityByProductId(id));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with edit product " + id + " operation: "+ e);
			redirect = "redirect:/products";
		}
		return redirect;
	}
	
	@PostMapping("/product/edit/{id}") 
	public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("commodity") Сommodity commodity, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";		
		try {
			ValidatorResult validatorResult = productValidationService.validateCommodity(commodity);
			if(!validatorResult.isValidationPass()) {
				redirectAttributes.addFlashAttribute("errorMessage", validatorResult.getError());        
				redirectAttributes.addFlashAttribute("commodity", commodity);	       
				redirect = "redirect:/product/edit/"+id;				
			} else {
				productService.updateProduct(id, commodity);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with update product operation: "+ e);
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred, please try update product later...");        
			redirectAttributes.addFlashAttribute("commodity", commodity);
			redirect = "redirect:/product/edit/"+id;
		}
        return redirect;
    }
	
	@GetMapping("/product")
	public String goToNewProductPage(HttpSession session, Model model) {
		String redirect = "redirect:/products";
		User user = (User) session.getAttribute("loginUser");
		if (null != user && Roles.ADMIN.roleName.equals(user.getRole())) {
			Сommodity commodity = (Сommodity) model.getAttribute("commodity");
			if (null == commodity) {
				commodity = new Сommodity();
			}
			model.addAttribute("commodity", commodity);
			redirect = "productNew";
		}
		return redirect;
	}
	
	@PostMapping("/product") 
	public String createProduct(Model model, @ModelAttribute("commodity") Сommodity commodity, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";		
		try {
			ValidatorResult validatorResult = productValidationService.validateCommodity(commodity);
			if(!validatorResult.isValidationPass()) {
				redirectAttributes.addFlashAttribute("errorMessage", validatorResult.getError());        
				redirectAttributes.addFlashAttribute("commodity", commodity);	       
				redirect = "redirect:/product";				
			} else {
				productService.createProduct(productService.getProductFromCommodity(commodity));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with create product operation: "+ e);
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred, please try create product later...");        
			redirectAttributes.addFlashAttribute("commodity", commodity);
			redirect = "redirect:/product";
		}
        return redirect;
    }
	
	@GetMapping("/product/disable/{id}")
	public String disableProduct(@PathVariable("id") Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		User user = (User) session.getAttribute("loginUser");
		if(null != user && Roles.ADMIN.roleName.equals(user.getRole())) {
			productService.disableProductById(id);
		}
		return redirect;
	}
	
	@GetMapping("/product/enable/{id}")
	public String enableProduct(@PathVariable("id") Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		User user = (User) session.getAttribute("loginUser");
		if(null != user && Roles.ADMIN.roleName.equals(user.getRole())) {
			productService.enableProductById(id);
		}
		return redirect;
	}

}

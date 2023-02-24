package best.project.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import best.project.shop.helper.CartUtilsHolder;
import best.project.shop.model.Cart;
import best.project.shop.service.ProductService;

@Controller
public class CartController {
	
	private ProductService productService;
	
	public CartController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/cart/increase/{id}")
	@ResponseBody
	public String increaseProduct(@PathVariable("id") Long id, HttpSession session) {
		Cart cart = getCart(session);
		cart.addProduct(id);
		session.setAttribute("cart", cart);
		return "Product with id " + id + " edded to cart";
	}
	
	@PostMapping("/cart/decrease/{id}")
	@ResponseBody
	public String decreaseProduct(@PathVariable("id") Long id, HttpSession session) {
		Cart cart = getCart(session);
		cart.decreaseProduct(id);
		session.setAttribute("cart", cart);
		return "products";
	}
	
	@GetMapping("/cart/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, HttpSession session, Model model) {
		Cart cart = getCart(session);
		cart.deleteProduct(id);
		CartUtilsHolder cartUnitsHolder = productService.getCartUnits(cart);
	    model.addAttribute("cartUnitsHolder", cartUnitsHolder);
		return "cart";
	}
	
	@GetMapping("/cart")
	public String goToCartView(HttpSession session, Model model) {
		Cart cart = getCart(session);		
		CartUtilsHolder cartUnitsHolder = productService.getCartUnits(cart);
	    model.addAttribute("cartUnitsHolder", cartUnitsHolder);
		return "cart";
	}

	private Cart getCart(HttpSession session) {
		Cart cart;
		if(null == session.getAttribute("cart")) {
			cart = new Cart();
		} else {
			cart = (Cart) session.getAttribute("cart");
		}
		return cart;
	}
}

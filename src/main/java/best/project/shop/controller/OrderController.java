package best.project.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import best.project.shop.helper.CartUtilsHolder;
import best.project.shop.model.Cart;
import best.project.shop.model.Order;
import best.project.shop.model.User;
import best.project.shop.service.OrderService;
import best.project.shop.service.ProductService;

@Controller
public class OrderController {
	
	private ProductService productService;
	private OrderService orderService;
	
	public OrderController(ProductService productService, OrderService orderService) {
		this.productService = productService;
		this.orderService = orderService;
	}
	
	@GetMapping("/orders")
	public String goToOrdersView(HttpSession session, Model model) {
		String response = "orders";
		User user = (User) session.getAttribute("loginUser");
		if(null != user) {
			List<Order> orders = orderService.getOrderByUserId(user.getId());
			if(null != orders && !orders.isEmpty()) {
				model.addAttribute("orders", orders);
			}
		} else {
			response = "redirect:/products";
		}
		return response;
	}

	@GetMapping("/order")
	public String goToNewOrderView(HttpSession session, Model model) {
		String response = "orderNew";
		Cart cart = getCart(session);
		User user = (User) session.getAttribute("loginUser");
		if(null == cart.getCartProducts() || cart.getCartProducts().isEmpty() || null == user) {
			response = "redirect:/products";
		} else {
			CartUtilsHolder cartUnitsHolder = productService.getCartUnits(cart);
			model.addAttribute("cartUnitsHolder", cartUnitsHolder);
			Order order = new Order();
			model.addAttribute("order", order);
		}
		return response;
	}
	
	@PostMapping("/order")
	public String createNewOrder(HttpSession session, Model model, @ModelAttribute("order") Order order) {
		String response = "redirect:/products";
		Cart cart = getCart(session);
		if(null != cart.getCartProducts() && !cart.getCartProducts().isEmpty()) {
			User user = (User) session.getAttribute("loginUser");
			if(null != user) {
				orderService.createOrder(order, cart, user);
				session.setAttribute("cart", null);
			}	
		}
		return response;
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

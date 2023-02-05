package best.project.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import best.project.shop.model.Cart;

@Controller
public class CartController {
	
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
	
	@PostMapping("/cart/delete/{id}")
	@ResponseBody
	public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
		Cart cart = getCart(session);
		cart.deleteProduct(id);
		session.setAttribute("cart", cart);
		return "products";
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

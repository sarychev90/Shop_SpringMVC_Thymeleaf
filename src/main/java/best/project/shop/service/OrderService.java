package best.project.shop.service;

import java.util.List;

import best.project.shop.model.Cart;
import best.project.shop.model.Order;
import best.project.shop.model.User;

public interface OrderService {
	
	public void createOrder(Order order);
	public void createOrder(Order order, Cart cart, User user);
	public List<Order> getOrderByUserId(Long userId);

}

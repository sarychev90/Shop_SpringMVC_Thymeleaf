package best.project.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import best.project.shop.helper.CostCalculator;
import best.project.shop.model.Cart;
import best.project.shop.model.Order;
import best.project.shop.model.OrderItem;
import best.project.shop.model.Product;
import best.project.shop.model.User;
import best.project.shop.repository.OrderRepository;
import best.project.shop.repository.ProductRepository;
import best.project.shop.repository.UserRepository;
import best.project.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
		
	}

	@Override
	public void createOrder(Order order, Cart cart, User user) {
		User existUser = userRepository.getReferenceById(user.getId());
		order.setUser(existUser);
		order.setExecuteFlag(Boolean.FALSE);
		order.setOrderCreateDate(new Date());
		order.setOrderItems(getOrderItem(cart, order));
		createOrder(order);
	}
	
	public List<OrderItem> getOrderItem(Cart cart, Order order) {
		List<OrderItem> orderItems = null;
		Map<Long, Integer> cartProducts = cart.getCartProducts();
		if(!cartProducts.isEmpty()) {
			orderItems = new ArrayList<>();
			for(Map.Entry<Long, Integer> entry : cartProducts.entrySet()) {
				Long productId = entry.getKey();
				Integer productQty = entry.getValue();
				Product product = productRepository.getReferenceById(productId);
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProduct(product);
				orderItem.setProductQuantity(productQty);
				orderItem.setProductsTotalCost(CostCalculator.calculateOrderItemCosts(product, productQty));
				orderItems.add(orderItem);				
			}
		}
		
		return orderItems;
	}

	@Override
	public List<Order> getOrderByUserId(Long userId) {
		return orderRepository.getOrderByUserId(userId);
	}
	
	
	
}

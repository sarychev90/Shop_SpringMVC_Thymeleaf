package best.project.shop.service.impl;

import org.springframework.stereotype.Service;

import best.project.shop.model.Order;
import best.project.shop.repository.OrderRepository;
import best.project.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
		
	}
	
	
	
}

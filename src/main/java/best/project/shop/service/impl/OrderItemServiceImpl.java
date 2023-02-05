package best.project.shop.service.impl;

import org.springframework.stereotype.Service;

import best.project.shop.model.OrderItem;
import best.project.shop.repository.OrderItemRepository;
import best.project.shop.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	private OrderItemRepository orderItemRepository;
	
	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public void createOrderItem(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
		
	}
	
	
	
	
	
}

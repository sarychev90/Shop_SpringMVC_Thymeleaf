package best.project.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> getOrderByUserId(Long userId);

}

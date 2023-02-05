package best.project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

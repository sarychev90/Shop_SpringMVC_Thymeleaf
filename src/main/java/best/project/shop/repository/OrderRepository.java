package best.project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

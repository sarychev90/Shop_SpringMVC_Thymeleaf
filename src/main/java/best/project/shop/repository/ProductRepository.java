package best.project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

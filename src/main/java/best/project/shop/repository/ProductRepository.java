package best.project.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public List<Product> getProductByEnable(Boolean enable);

}

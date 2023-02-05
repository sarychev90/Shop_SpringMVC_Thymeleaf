package best.project.shop.service;

import java.io.IOException;
import java.util.List;

import best.project.shop.model.Product;
import best.project.shop.model.Сommodity;

public interface ProductService {
	
	public void createProduct(Product product);
	
	public List<Product> findAllProduct();
	public Product getProductById(Long id);
	public void deleteProductById(Long id);
	public Product getProductFromCommodity(Сommodity commodity) throws IOException;

}

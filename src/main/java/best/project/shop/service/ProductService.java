package best.project.shop.service;

import java.io.IOException;
import java.util.List;

import best.project.shop.helper.CartUtilsHolder;
import best.project.shop.model.Cart;
import best.project.shop.model.Product;
import best.project.shop.model.Сommodity;

public interface ProductService {
	
	public void createProduct(Product product);
	public void updateProduct(Long id, Сommodity commodity) throws IOException;
	public List<Product> findAllProduct();
	public Product getProductById(Long id);
	public void disableProductById(Long id);
	public void enableProductById(Long id);
	public Product getProductFromCommodity(Сommodity commodity) throws IOException;
	public Сommodity getCommodityFromProduct(Product product);
	public Сommodity getCommodityByProductId(Long id);
	public CartUtilsHolder getCartUnits(Cart cart);
	public List<Product> findAllEnableProduct();

}

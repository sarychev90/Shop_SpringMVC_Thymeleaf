package best.project.shop.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import best.project.shop.model.Product;
import best.project.shop.model.ProductPicture;

public class ProductOperationTests {
	
	@Test
	void sortProductTest() {
		Product product = createProduct("Apple",20.50, "Fresh apple", "Apple1.jpg", "Apple2.jpg", "Apple3.jpg");
		product.sortProductPictures();
		assertEquals(product.getProductPictures().get(0).getPicture(), "Apple2.jpg");
	}
	
	private Product createProduct(String name, double price, String description, String image1, String image2, String image3) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(new BigDecimal(price));
		product.setDescription(description);
		
		List<ProductPicture> productPictures = new ArrayList<>();
		
		ProductPicture productPicture1 = new ProductPicture();
		productPicture1.setMain(Boolean.FALSE);
		productPicture1.setPicture(image1);
		productPicture1.setProduct(product);
		
		ProductPicture productPicture2 = new ProductPicture();
		productPicture2.setMain(Boolean.TRUE);
		productPicture2.setPicture(image2);
		productPicture2.setProduct(product);
		
		ProductPicture productPicture3 = new ProductPicture();
		productPicture3.setMain(Boolean.FALSE);
		productPicture3.setPicture(image3);
		productPicture3.setProduct(product);
		
		productPictures.add(productPicture1);
		productPictures.add(productPicture2);
		productPictures.add(productPicture3);
		
		product.setProductPictures(productPictures);
		return product;
	}

}

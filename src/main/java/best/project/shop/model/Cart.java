package best.project.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	private Map<Long, Integer> cartProducts;
	
	public Cart() {
		cartProducts = new HashMap<>();
	}
	
	public Map<Long, Integer> getCartProducts() {
		return cartProducts;
	}
	
	public Integer getProductCount() {
		Integer productCount = 0;
		for (Integer qty : cartProducts.values()) {
			productCount += qty;
	    }
		return productCount;
	}

	public void addProduct(Long id) {
		Integer productQty = cartProducts.get(id);
		if(null == productQty) {
			cartProducts.put(id, 1);
		} else {
			cartProducts.put(id, ++productQty);
		}
	}
	
	public void decreaseProduct(Long id) {
		Integer productQty = cartProducts.get(id);
		if (null != productQty) {
			if (productQty > 1) {
				cartProducts.put(id, --productQty);
			} else {
				cartProducts.remove(id);
			}
		}
	}
	
	public void deleteProduct(Long id) {
		cartProducts.remove(id);
	}
	
}

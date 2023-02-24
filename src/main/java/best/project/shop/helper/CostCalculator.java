package best.project.shop.helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import best.project.shop.model.OrderItem;
import best.project.shop.model.Product;

public class CostCalculator {
	
	private static final Logger LOGGER = Logger.getLogger(CostCalculator.class.getName());
	
	private CostCalculator() {
		//
	}
	
	public static BigDecimal calculateOrderItemCosts(Product product, Integer quantity) {
		BigDecimal productsTotalCosts = BigDecimal.ZERO;
		try {
			productsTotalCosts = product.getPrice().multiply(new BigDecimal(quantity));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with calculate order item costs. Cause: " + e);
		}
		return productsTotalCosts;
	}
	
	public static BigDecimal calculateTotalOrderCosts(List<OrderItem> orderItems) {
		BigDecimal orderItemsTotalCosts = BigDecimal.ZERO;
		try {
			for (OrderItem orderItem : orderItems) {
				orderItemsTotalCosts = orderItemsTotalCosts.add(orderItem.getProductsTotalCost());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with calculate total order costs. Cause: " + e);
		}
		return orderItemsTotalCosts;
	}
	
	public static Integer calculateTotalOrderQuantity(List<OrderItem> orderItems) {
		Integer productsQty = 0;
		try {
			for (OrderItem orderItem : orderItems) {
				productsQty += orderItem.getProductQuantity();
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with calculate total order quantity. Cause: " + e);
		}
		return productsQty;
	}

}

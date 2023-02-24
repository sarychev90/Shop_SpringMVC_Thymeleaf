package best.project.shop.helper;

import java.math.BigDecimal;
import java.util.List;

import best.project.shop.model.CartUnit;
import lombok.Getter;

@Getter
public class CartUtilsHolder {
	
	private List<CartUnit> cartUnits;
	private Integer totalQuantity;
	private BigDecimal totalCosts;

	public void setCartUnits(List<CartUnit> cartUnits) {
		this.cartUnits = cartUnits;
		setTotalParameters();
	}

	public void setTotalParameters() {
		totalQuantity = 0;
		totalCosts = BigDecimal.ZERO;
		if(null != cartUnits) {
			for(CartUnit cartUtil : cartUnits) {
				totalQuantity += cartUtil.getQuantity();
				totalCosts = totalCosts.add(cartUtil.getTotalCost());
			}
		}
	}
}

package best.project.shop.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUnit extends Product {
	
	private Integer quantity;
	private BigDecimal totalCost;

}

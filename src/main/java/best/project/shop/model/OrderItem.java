package best.project.shop.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "order_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;
	
	@Column(name = "product_quantity", nullable = false)
	private Integer productQuantity;
	
	@Column(name = "products_total_cost", nullable = false)
	private BigDecimal productsTotalCost;
	
	@JoinColumn(name = "product_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

}

package best.project.shop.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import best.project.shop.helper.CostCalculator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
	
	@Column(name = "order_create_date", nullable = false)
	private Date orderCreateDate;
	
	@Column(name = "order_execute_date")
	private Date orderExecuteDate;
	
	@Column(name = "execute_flag")
	private Boolean executeFlag;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "comments")
	private String comments;
	
	public BigDecimal getOrderTotalCosts() {
		return CostCalculator.calculateTotalOrderCosts(orderItems);
	}
	
	public Integer getOrderTotalQuantity() {
		return CostCalculator.calculateTotalOrderQuantity(orderItems);
	}
	
}

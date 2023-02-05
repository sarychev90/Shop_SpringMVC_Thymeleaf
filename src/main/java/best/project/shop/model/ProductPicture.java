package best.project.shop.model;

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
@Table(name = "product_pictures")
@Getter
@Setter
public class ProductPicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "product_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Product product;
	
	@Column(name = "picture", columnDefinition="text")
	private String picture;
	
	@Column(name = "main_flag", columnDefinition="BOOLEAN DEFAULT false")
	private Boolean main;

}

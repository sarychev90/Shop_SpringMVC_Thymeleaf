package best.project.shop.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "enable")
	private Boolean enable;
	
	@Column(name = "description", columnDefinition="text")
	private String description;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPicture> productPictures;
	
	public String getMainPicture() {
		String image = null;
		if (null != productPictures && productPictures.size() == 1) {
			image = productPictures.get(0).getPicture();
		} else if (null != productPictures && productPictures.size() > 1) {
			sortProductPictures();
			image = productPictures.get(0).getPicture();
		}
		return image;
	}
	
	public String getProductPicturesImages() {
		StringBuilder productPicturesImages = new StringBuilder();
		try {
			sortProductPictures();
			List<ProductPicture> productPictures = getProductPictures();
			if(null != productPictures && !productPictures.isEmpty()) {
				for(int i = 0; i< productPictures.size(); i++) {
					productPicturesImages.append(productPictures.get(i).getPicture());
					if(i != (productPictures.size()-1)) {
						productPicturesImages.append('^');
					}
				}
			}
		} catch (Exception e) {
			//
		}		
		return productPicturesImages.toString();
	}
	
	public void sortProductPictures(){
		try {
			if(null != productPictures && !productPictures.isEmpty()) {
				Collections.sort(productPictures, Comparator.comparing(ProductPicture::getMain).reversed());				
			}
		} catch (Exception e) {
			//
		}	
	}
	
	public ProductPicture getProductPictureByIndex(int index) {
		ProductPicture productPicture = new ProductPicture();
		try {
			productPicture = productPictures.get(index);
		} catch (Exception e) {
			//
		}	
		return productPicture;
	}
	

}

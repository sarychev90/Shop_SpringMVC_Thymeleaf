package best.project.shop.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import best.project.shop.service.impl.ImageConverterServiceImpl;
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
			List<ProductPicture> mainPictures = productPictures.stream().filter(pp -> Boolean.TRUE.equals(pp.getMain())).collect(Collectors.toList());
			if(mainPictures.isEmpty()) {
				image = productPictures.get(0).getPicture();
			} else {
				image = mainPictures.get(0).getPicture();
			}
		}
//		if (null == image) {
//			image = ImageConverterServiceImpl.getInstance().getNoImageObject();
//		}
		return image;
	}
	
	public String getProductPicturesImages() {
		StringBuilder productPicturesImages = new StringBuilder();
		try {
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

}

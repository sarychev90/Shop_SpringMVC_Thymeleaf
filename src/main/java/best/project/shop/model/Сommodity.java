package best.project.shop.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Сommodity extends Product {
	
	private MultipartFile image1;
	private MultipartFile image2;
	private MultipartFile image3;
	private MultipartFile image4;
	private MultipartFile image5;
	
	private String picture1;
	private String picture2;
	private String picture3;
	private String picture4;
	private String picture5;
	
	private Long image1Id;
	private Long image2Id;
	private Long image3Id;
	private Long image4Id;
	private Long image5Id;	
	
	public List<PictureUnit> getPictureUnits(){
		List<PictureUnit> pictureUnits = new ArrayList<>();
		pictureUnits.add(new PictureUnit(image1, image1Id));
		pictureUnits.add(new PictureUnit(image2, image2Id));
		pictureUnits.add(new PictureUnit(image3, image3Id));
		pictureUnits.add(new PictureUnit(image4, image4Id));
		pictureUnits.add(new PictureUnit(image5, image5Id));
		return pictureUnits;
	}
	
	
	
	
}

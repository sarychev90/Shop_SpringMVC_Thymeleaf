package best.project.shop.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PictureUnit {
	
	private MultipartFile image;
	private Long imageId;

}

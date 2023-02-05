package best.project.shop.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import best.project.shop.service.ImageConverterService;

@Service
public class ImageConverterServiceImpl implements ImageConverterService {
	
	private static final Logger LOGGER = Logger.getLogger(ImageConverterServiceImpl.class.getName());
	
	private String noImageObject;
	
	@Value("${shop.max.file.size}")
	private Integer maxFileSize;

	@PostConstruct
	public void init() {
		noImageObject = imageToBase64Convertor("NoImage.jpg");		
		LOGGER.log(Level.INFO, "ImageConvertor initialization done");
	}
	
	@Override
	public String imageToBase64Convertor(String imageName) {
		byte[] fileContent = null;
		String encodedImage = null;
		try (InputStream imageInputeStream = ImageConverterServiceImpl.class.getResourceAsStream("/static/images/"+imageName)) {
			fileContent = imageInputeStream.readAllBytes();
			encodedImage = Base64.getEncoder().encodeToString(fileContent);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with converting image to base64 "+ e);
		}
		LOGGER.log(Level.INFO, "Base64 photo: "+encodedImage.substring(0,  20));
		return "data:image/jpg;base64,"+encodedImage;
	}
	
	@Override
	public String getBase64CompressedImage(byte[] bytes) {
		String encodedImage = null;
		try {
			LOGGER.log(Level.INFO, "File size of original image: " + bytes.length);
			if(bytes.length <= maxFileSize) {
				encodedImage = fileToBase64Convertor(bytes);
			} else {
				encodedImage = fileToBase64Convertor(getBytesFromBufferedImage(resizeImage(bytes)));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with converting compressed file to base64 "+ e);
		}
		return encodedImage;
	}
	
	private String fileToBase64Convertor(byte[] byles) {
		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(byles);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with converting file to base64 "+ e);
		}
		LOGGER.log(Level.INFO, "Base64 photo: "+encodedImage.substring(0,  20));
		return "data:image/jpg;base64,"+encodedImage;
	}
	
	private BufferedImage resizeImage(byte[] bytes) {
		BufferedImage resizedImage = null;
		try (InputStream is = new ByteArrayInputStream(bytes)) {
			BufferedImage originalImage = ImageIO.read(is);
			int originalWidth = originalImage.getWidth();
			LOGGER.log(Level.INFO, "originalWidth " + originalWidth);
			int originalHeight = originalImage.getHeight();
			LOGGER.log(Level.INFO, "originalHeight " + originalHeight);
			int targetWidth = originalWidth * maxFileSize / bytes.length;
			LOGGER.log(Level.INFO, "targetWidth " + targetWidth);
			int targetHeight = originalHeight * maxFileSize / bytes.length;
			LOGGER.log(Level.INFO, "targetHeight " + targetHeight);			
			resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics2D = resizedImage.createGraphics();
		    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		    graphics2D.dispose();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with resize image image "+ e);
		}
	    return resizedImage;
	}
	
	private byte[] getBytesFromBufferedImage(BufferedImage image) {
		byte[] bytes = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(image, "jpg", baos);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with getting bytes from buffered image "+ e);
		}
		LOGGER.log(Level.INFO, "File size of buffered image: " + bytes.length);
		return bytes;
	}

	@Override
	public String getNoImageObject() {
		return noImageObject;
	}
}

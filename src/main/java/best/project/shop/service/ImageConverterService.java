package best.project.shop.service;

public interface ImageConverterService {

	String imageToBase64Convertor(String imageName);
	String getBase64CompressedImage(byte[] bytes);
	String getNoImageObject();

}
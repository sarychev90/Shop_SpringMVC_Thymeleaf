package best.project.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import best.project.shop.model.Product;
import best.project.shop.model.ProductPicture;
import best.project.shop.model.Сommodity;
import best.project.shop.repository.ProductRepository;
import best.project.shop.service.ImageConverterService;
import best.project.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class.getName());
	
	private ProductRepository productRepository;
	private ImageConverterService imageConverterService;
	
	public ProductServiceImpl(ProductRepository productRepository, ImageConverterService imageConverterService) {
		this.productRepository = productRepository;
		this.imageConverterService = imageConverterService;
	}

	@Override
	public void createProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		Product product = null;
		try {
			product = productRepository.getReferenceById(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with getProductById exception: "+ e);
		}
		return product;
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getProductFromCommodity(Сommodity commodity) throws IOException {
		Product product = new Product();
		product.setName(commodity.getName());
		product.setPrice(commodity.getPrice());
		product.setDescription(commodity.getDescription());
		List<ProductPicture> productPictures = setProductPictures(product, commodity);
		product.setProductPictures(productPictures);
		return product;
	}
	
	private List<ProductPicture> setProductPictures(Product product, Сommodity commodity) throws IOException {
		List<MultipartFile> pictures = Arrays.asList(
				commodity.getImage1(), commodity.getImage2(), commodity.getImage3(), commodity.getImage4(), commodity.getImage5());
		List<ProductPicture> productPictures = new ArrayList<>();
		for (int i = 0; i < pictures.size(); i++) {
			if(null != pictures.get(i) && null != pictures.get(i).getBytes() && pictures.get(i).getBytes().length > 0) {
				ProductPicture productPicture = new ProductPicture();
				if(i == 0) {
					productPicture.setMain(Boolean.TRUE);
				} else {
					productPicture.setMain(Boolean.FALSE);
				}
				productPicture.setPicture(imageConverterService.getBase64CompressedImage(pictures.get(i).getBytes()));
				productPicture.setProduct(product);
				productPictures.add(productPicture);
			}
		}
		return productPictures;
		
	}

}

package best.project.shop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import best.project.shop.helper.CartUtilsHolder;
import best.project.shop.helper.CostCalculator;
import best.project.shop.model.Cart;
import best.project.shop.model.CartUnit;
import best.project.shop.model.PictureUnit;
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
	public void disableProductById(Long id) {
		Product product = getProductById(id);
		if(null != product) {
			product.setEnable(Boolean.FALSE);
			createProduct(product);
		}
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

	@Override
	public Сommodity getCommodityFromProduct(Product product) {
		Сommodity commodity = new Сommodity();
		commodity.setId(product.getId());
		commodity.setName(product.getName());
		commodity.setPrice(product.getPrice());
		commodity.setDescription(product.getDescription());
		return commodity;
	}

	@Override
	public Сommodity getCommodityByProductId(Long id) {
		Product product = getProductById(id);
		Сommodity commodity = new Сommodity();
		commodity.setId(product.getId());
		commodity.setName(product.getName());
		commodity.setPrice(product.getPrice());
		commodity.setDescription(product.getDescription());
		setPictures(product, commodity);		
		return commodity;
	}

	private void setPictures(Product product, Сommodity commodity) {
		product.sortProductPictures();
		
		commodity.setImage1Id(product.getProductPictureByIndex(0).getId());
		commodity.setPicture1(product.getProductPictureByIndex(0).getPicture());
		
		commodity.setImage2Id(product.getProductPictureByIndex(1).getId());
		commodity.setPicture2(product.getProductPictureByIndex(1).getPicture());
		
		commodity.setImage3Id(product.getProductPictureByIndex(2).getId());
		commodity.setPicture3(product.getProductPictureByIndex(2).getPicture());
		
		commodity.setImage4Id(product.getProductPictureByIndex(3).getId());
		commodity.setPicture4(product.getProductPictureByIndex(3).getPicture());
		
		commodity.setImage5Id(product.getProductPictureByIndex(4).getId());
		commodity.setPicture5(product.getProductPictureByIndex(4).getPicture());
	}

	@Override
	public void updateProduct(Long id, Сommodity commodity) throws IOException {
		Product product = getProductById(id);
		product.setName(commodity.getName());
		product.setPrice(commodity.getPrice());
		product.setDescription(commodity.getDescription());
		List<ProductPicture> productPictures = null;
		if(null == product.getProductPictures() || product.getProductPictures().isEmpty()) {
			productPictures = new ArrayList<>();
		}
		List<PictureUnit> pictureUnits = commodity.getPictureUnits();
		for (int i = 0; i < pictureUnits.size(); i++) {
			if(null != pictureUnits.get(i).getImage() && null != pictureUnits.get(i).getImage().getBytes() && pictureUnits.get(i).getImage().getBytes().length > 0) {
				if(null == pictureUnits.get(i).getImageId()) {
					ProductPicture productPicture = new ProductPicture();
					if(i == 0) {
						productPicture.setMain(Boolean.TRUE);
					} else {
						productPicture.setMain(Boolean.FALSE);
					}
					productPicture.setPicture(imageConverterService.getBase64CompressedImage(pictureUnits.get(i).getImage().getBytes()));
					productPicture.setProduct(product);
					if(null == product.getProductPictures()) {
						productPictures.add(productPicture);
					} else {
						product.getProductPictures().add(productPicture);
					}
				} else {
					for(ProductPicture productPicture : product.getProductPictures()) {
						if(pictureUnits.get(i).getImageId() == productPicture.getId()) {
							productPicture.setPicture(imageConverterService.getBase64CompressedImage(pictureUnits.get(i).getImage().getBytes()));
						}
					}
				}
			}
		}
		if(null == product.getProductPictures() || product.getProductPictures().isEmpty()) {
			product.setProductPictures(productPictures);
		}
		createProduct(product);	
	}
	
	public CartUtilsHolder getCartUnits(Cart cart) {
		CartUtilsHolder cartUtilsHolder = new CartUtilsHolder();
		Map<Long, Integer> cartProducts = cart.getCartProducts();
		List<CartUnit> cartUnits = null;
		if(!cartProducts.isEmpty()) {
			cartUnits = new ArrayList<>();
			for(Map.Entry<Long, Integer> entry : cartProducts.entrySet()) {
				Long productId = entry.getKey();
				Integer productQty = entry.getValue();
				Product product = getProductById(productId);
				CartUnit cartUnit = new CartUnit();
				cartUnit.setId(product.getId());
				cartUnit.setName(product.getName());
				cartUnit.setPrice(product.getPrice());
				cartUnit.setDescription(product.getDescription());
				cartUnit.setProductPictures(product.getProductPictures());
				cartUnit.setQuantity(productQty);
				cartUnit.setTotalCost(CostCalculator.calculateOrderItemCosts(product, productQty));
				cartUnits.add(cartUnit);				
			}
		}
		cartUtilsHolder.setCartUnits(cartUnits);
		return cartUtilsHolder;
	}

	@Override
	public List<Product> findAllEnableProduct() {
		return productRepository.getProductByEnable(Boolean.TRUE);
	}

	@Override
	public void enableProductById(Long id) {
		Product product = getProductById(id);
		if(null != product) {
			product.setEnable(Boolean.TRUE);
			createProduct(product);
		}	
	}
	
	
	

}

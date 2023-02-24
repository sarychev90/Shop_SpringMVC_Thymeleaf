package best.project.shop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import best.project.shop.helper.CostCalculator;
import best.project.shop.model.Order;
import best.project.shop.model.OrderItem;
import best.project.shop.model.Product;
import best.project.shop.model.ProductPicture;
import best.project.shop.model.Roles;
import best.project.shop.model.User;
import best.project.shop.service.ImageConverterService;
import best.project.shop.service.InitialShopDataInputService;
import best.project.shop.service.OrderItemService;
import best.project.shop.service.OrderService;
import best.project.shop.service.ProductService;
import best.project.shop.service.UserService;

@Service
public class InitialShopDataInputServiceImpl implements InitialShopDataInputService{

	private static final Logger LOGGER = Logger.getLogger(InitialShopDataInputServiceImpl.class.getName());
	
	@Value("${shop.admin.name}")
	private String adminName;
	
	@Value("${shop.admin.email}")
	private String adminEmail;
	
	@Value("${shop.admin.password}")
	private String adminPassword;
	
	@Value("${shop.test.user.name}")
	private String testUserName;
	
	@Value("${shop.test.user.email}")
	private String testUserEmail;
	
	@Value("${shop.test.user.password}")
	private String testUserPassword;
	
	private OrderService orderService;
	
	private ProductService productService;
	
	private UserService userService;
	
	private OrderItemService orderItemService;
	
	private PasswordEncoder encoder;

	private ImageConverterService imageConverterService;
	
	public InitialShopDataInputServiceImpl(OrderService orderService, ProductService productService,
			UserService userService, OrderItemService orderItemService, PasswordEncoder encoder,
			ImageConverterService imageConverterService) {
		super();
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
		this.orderItemService = orderItemService;
		this.encoder = encoder;
		this.imageConverterService = imageConverterService;
	}

	@Override
	public void populateInitialShopData() {
		try {
			if (productService.findAllProduct().isEmpty()) {
				LOGGER.log(Level.INFO, "DB is empty, start population tables...");

				//create users
				User client = createInitialUsers();
				
				//create product
				Product product = createInitialProducts();
				
				//create order
				createInitialOrder(client, product);
				
				LOGGER.log(Level.INFO, "Tables population done.");
			} else {
				LOGGER.log(Level.INFO, "DB isn't empty. No DB population needed.");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with table population. Cause: " + e);
		}

	}

	private void createInitialOrder(User client, Product product) {
		Order order = new Order();
		order.setPhone("067-777-22-33");
		order.setAddress("st. 23, room 5");
		order.setOrderCreateDate(new Date());
		order.setUser(client);
		
		List<OrderItem> orderItems = new ArrayList<>();
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setProductQuantity(2);
		orderItem.setProductsTotalCost(CostCalculator.calculateOrderItemCosts(product, 2));
		orderItems.add(orderItem);
		
		order.setOrderItems(orderItems);
		
		orderService.createOrder(order);
		
		orderItem = new OrderItem();
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem.setProductQuantity(3);
		orderItem.setProductsTotalCost(CostCalculator.calculateOrderItemCosts(product, 3));
		
		orderItemService.createOrderItem(orderItem);
	}

	private Product createInitialProducts() {
		Product product1 = createProduct("Apple",20.50, "Fresh apple", "Apple1.jpg", "Apple2.jpg");
		createProduct("Banana", 32.75, "Fresh banana", "Banana1.jpg", "Banana2.jpg");
		createProduct("Pear", 25.00, "Fresh pear", "Pear1.jpg", "Pear2.jpg");
		createProduct("Tangerine", 45.10, "Exotic fresh tangerine", "Tangerine1.jpg", "Tangerine2.jpg");
		createProduct("Watermelon", 10.30, "Juicy fresh watermelon", "Watermelon1.jpg", "Watermelon2.jpg");
		return product1;
	}
	
	private Product createProduct(String name, double price, String description, String image1, String image2) {
		Product product = new Product();
		product.setName(name);
		product.setPrice(new BigDecimal(price));
		product.setDescription(description);
		product.setEnable(Boolean.TRUE);
		
		List<ProductPicture> productPictures = new ArrayList<>();
		
		ProductPicture productPicture1 = new ProductPicture();
		productPicture1.setMain(Boolean.TRUE);
		productPicture1.setPicture(imageConverterService.imageToBase64Convertor(image1));
		productPicture1.setProduct(product);
		
		ProductPicture productPicture2 = new ProductPicture();
		productPicture2.setMain(Boolean.FALSE);
		productPicture2.setPicture(imageConverterService.imageToBase64Convertor(image2));
		productPicture2.setProduct(product);
		
		productPictures.add(productPicture1);
		productPictures.add(productPicture2);
		
		product.setProductPictures(productPictures);
		
		productService.createProduct(product);
		return product;
	}

	private User createInitialUsers() {
		List<User> users = new ArrayList<>();
		
		User admin = new User();
		admin.setName(adminName);
		admin.setPassword(encoder.encode(adminPassword));
		admin.setEmail(adminEmail);
		admin.setRole(Roles.ADMIN.roleName);
		
		User client = new User();
		client.setName(testUserName);
		client.setPassword(encoder.encode(testUserPassword));
		client.setEmail(testUserEmail);
		client.setRole(Roles.CLIENT.roleName);
		
		users.add(client);
		users.add(admin);
		
		userService.createUsers(users);
		
		return client;
	}

}

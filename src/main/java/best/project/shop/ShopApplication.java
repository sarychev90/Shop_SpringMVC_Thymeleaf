package best.project.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import best.project.shop.service.InitialShopDataInputService;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner populateInitialShopData(InitialShopDataInputService initialShopDataInputService) {
		return args -> initialShopDataInputService.populateInitialShopData();
	}

}

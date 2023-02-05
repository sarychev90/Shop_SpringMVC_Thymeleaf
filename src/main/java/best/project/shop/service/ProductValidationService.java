package best.project.shop.service;

import java.io.IOException;

import best.project.shop.helper.ValidatorResult;
import best.project.shop.model.Сommodity;

public interface ProductValidationService {
	
	public ValidatorResult validateCommodity(Сommodity commodity) throws IOException;
	
}

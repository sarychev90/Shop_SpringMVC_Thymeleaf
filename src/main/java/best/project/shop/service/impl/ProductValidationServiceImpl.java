package best.project.shop.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import best.project.shop.helper.ValidatorResult;
import best.project.shop.model.Сommodity;
import best.project.shop.service.ProductValidationService;

@Service
public class ProductValidationServiceImpl implements ProductValidationService{

	@Override
	public ValidatorResult validateCommodity(Сommodity commodity) throws IOException {
		ValidatorResult validationResult = new ValidatorResult(Boolean.TRUE, null);
		if(!StringUtils.hasLength(commodity.getName())) {
			validationResult.setNotValidResult("Name couldn't be null or empty string!");
		} else if(!StringUtils.hasLength(commodity.getDescription())) {
			validationResult.setNotValidResult("Description couldn't be null or empty string!");
		} else if(null == commodity.getPrice()) {
			validationResult.setNotValidResult("Price couldn't be null or empty!");
		}
		return validationResult;
	}
	
	

	
	

}

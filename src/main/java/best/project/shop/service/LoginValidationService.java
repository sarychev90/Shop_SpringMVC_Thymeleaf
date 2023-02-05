package best.project.shop.service;

import best.project.shop.helper.LoginValidatorResult;
import best.project.shop.model.Registration;
import best.project.shop.model.User;

public interface LoginValidationService {
	
	public LoginValidatorResult validateRegistration(Registration registration);
	public LoginValidatorResult validateEditProfile(Registration registration);
	public LoginValidatorResult validateSingIn(User user);
}

package best.project.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import best.project.shop.helper.LoginValidatorResult;
import best.project.shop.model.Registration;
import best.project.shop.model.User;
import best.project.shop.service.LoginValidationService;
import best.project.shop.service.UserService;

@Service
public class LoginValidationServiceImpl implements LoginValidationService{
	
	private UserService userService;

	public LoginValidationServiceImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public LoginValidatorResult validateRegistration(Registration registration) {
		LoginValidatorResult validationResult = validateRegistrationNotNullFields(registration);
		if(validationResult.isValidationPass()) {
			validationResult = validatePasswordEquivalents(validationResult, registration);
			if(validationResult.isValidationPass()) {
				validationResult = validateUserExist(validationResult, registration);
			}
		}
		return validationResult;
		
	}
	
	private LoginValidatorResult validateRegistrationNotNullFields(Registration registration) {
		LoginValidatorResult validationResult = new LoginValidatorResult(Boolean.TRUE, null, null, Boolean.FALSE);
		if(!StringUtils.hasLength(registration.getName())) {
			validationResult.setNotValidResult("Name couldn't be null or empty string!");
		} else if(!StringUtils.hasLength(registration.getEmail())) {
			validationResult.setNotValidResult("Email couldn't be null or empty string!");
		} else if(!StringUtils.hasLength(registration.getPassword())) {
			validationResult.setNotValidResult("Password couldn't be null or empty string!");
		}
		return validationResult;
	}
	
	private LoginValidatorResult validatePasswordEquivalents(LoginValidatorResult validationResult, Registration registration) {
		if(!registration.getPassword().equals(registration.getRepeatPassword())) {
			validationResult.setNotValidResult("Entering passwords are different!");
		} 
		return validationResult;
	}
	
	private LoginValidatorResult validateUserExist(LoginValidatorResult validationResult, Registration registration) {
		List<User> users = userService.getAllUser();
		for (User user : users) {
			if (registration.getEmail().equalsIgnoreCase(user.getEmail())) {
				validationResult.setNotValidResult("User with an email " + registration.getEmail() + " already exist!");
				break;
			}
		}
		return validationResult;
	}

	@Override
	public LoginValidatorResult validateSingIn(User user) {
		LoginValidatorResult validationResult = validateSingInNotNullFields(user);
		if(validationResult.isValidationPass()) {
			User foundUser = userService.getUserByEmail(user.getEmail());
			if(null == foundUser) {
				validationResult.setNotValidResult("User with an email " + user.getEmail() + " has not registrated!");
			} else {
				validationResult = validatePassword(validationResult, user, foundUser);
			}
		}
		return validationResult;
	}

	private LoginValidatorResult validatePassword(LoginValidatorResult validationResult, User user, User foundUser) {
		if(!userService.comparePasswords(user.getPassword(), foundUser.getPassword())) {
			validationResult.setNotValidResult("Incorrect password!");
		} else {
			validationResult.setLoginUser(foundUser);
		}
		return validationResult;
	}

	private LoginValidatorResult validateSingInNotNullFields(User user) {
		LoginValidatorResult validationResult = new LoginValidatorResult(Boolean.TRUE, null, null, Boolean.FALSE);
		if(!StringUtils.hasLength(user.getEmail())) {
			validationResult.setNotValidResult("Email couldn't be null or empty string!");
		} else if(!StringUtils.hasLength(user.getPassword())) {
			validationResult.setNotValidResult("Password couldn't be null or empty string!");
		}
		return validationResult;
	}

	@Override
	public LoginValidatorResult validateEditProfile(Registration registration) {
		LoginValidatorResult validationResult = validateEditProfileNotNullFields(registration);
		if(validationResult.isValidationPass()) {
			validationResult = validateEditProfilePasswordEquivalents(validationResult, registration);
		}
		return validationResult;
	}
	
	private LoginValidatorResult validateEditProfileNotNullFields(Registration registration) {
		LoginValidatorResult validationResult = new LoginValidatorResult(Boolean.TRUE, null, null, Boolean.FALSE);
		if(!StringUtils.hasLength(registration.getName())) {
			validationResult.setNotValidResult("Name couldn't be null or empty string!");
		} else if(!StringUtils.hasLength(registration.getEmail())) {
			validationResult.setNotValidResult("Email couldn't be null or empty string!");
		}
		return validationResult;
	}
	
	private LoginValidatorResult validateEditProfilePasswordEquivalents(LoginValidatorResult validationResult, Registration registration) {
		if(StringUtils.hasLength(registration.getPassword())) {
			if(!registration.getPassword().equals(registration.getRepeatPassword())) {
				validationResult.setNotValidResult("Entering passwords are different!");
			} else {
				validationResult.setChangePassword(Boolean.TRUE);
			}
		} else if (StringUtils.hasLength(registration.getRepeatPassword())) {
			validationResult.setNotValidResult("Password couldn't be null or empty string!");
		}
		return validationResult;
	}

}

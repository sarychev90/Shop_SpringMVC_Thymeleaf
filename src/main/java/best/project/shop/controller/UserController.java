package best.project.shop.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import best.project.shop.helper.LoginValidatorResult;
import best.project.shop.model.Registration;
import best.project.shop.model.User;
import best.project.shop.service.LoginValidationService;
import best.project.shop.service.UserService;

@Controller
public class UserController {
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	private LoginValidationService loginValidationService;
	
	private UserService userService;

	public UserController(LoginValidationService loginValidationService, UserService userService) {
		this.loginValidationService = loginValidationService;
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String goToRegistrationPage(Model model) {
		Registration registration = (Registration) model.getAttribute("registration");
		if(null == registration) {
			registration = new Registration();
		}
		model.addAttribute("registration", registration);
		return "registration";
	}
	
	@GetMapping("/login")
	public String goToLoginPage(Model model) {
		User user = (User) model.getAttribute("user");
		if(null == user) {
			user = new User();
		}
		model.addAttribute("user", user);
		return "login";
	}
	
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		session.removeAttribute("loginUser");
		return "redirect:/products";
	}
	
	@PostMapping("/user")
	public String saveUser(@ModelAttribute("registration") Registration registration, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		try {
			LoginValidatorResult loginValidatorResult = loginValidationService.validateRegistration(registration);
			if(!loginValidatorResult.isValidationPass()) {
				redirectAttributes.addFlashAttribute("errorMessage", loginValidatorResult.getError());        
				redirectAttributes.addFlashAttribute("registration", registration);	       
				redirect = "redirect:/registration";
			} else {
				userService.createClient(registration.getUser());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with save user operation: "+ e);
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred, please try registering later...");        
			redirectAttributes.addFlashAttribute("registration", registration);	       
			redirect = "redirect:/registration";
		}
		return redirect;
	}
	
	@PostMapping("/user/login")
	public String loginUser(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		try {
			LoginValidatorResult loginValidatorResult = loginValidationService.validateSingIn(user);
			if(!loginValidatorResult.isValidationPass()) {
				redirectAttributes.addFlashAttribute("errorMessage", loginValidatorResult.getError());        
				redirectAttributes.addFlashAttribute("user", user);	       
				redirect = "redirect:/login";
			} else {
				session.setAttribute("loginUser", loginValidatorResult.getLoginUser());
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with login user operation: "+ e);
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred, please try login later...");        
			redirectAttributes.addFlashAttribute("user", user);	       
			redirect = "redirect:/login";
		}
		
		return redirect;
	}
	
	@GetMapping("/user/{id}")
	public String getUserProfile(@PathVariable("id") Long id, Model model) {
		User user = userService.getUserById(id);
		Registration registration = user.getRegistration();
		model.addAttribute("registration", registration);
		return "userProfile";
	}
	
	@PostMapping("/user/edit/{id}")
	public String editUserProfile(@PathVariable("id") Long id, @ModelAttribute("registration") Registration registration, HttpSession session, RedirectAttributes redirectAttributes) {
		String redirect = "redirect:/products";
		try {
			LoginValidatorResult loginValidatorResult = loginValidationService.validateEditProfile(registration);
			if(!loginValidatorResult.isValidationPass()) {
				redirectAttributes.addFlashAttribute("errorMessage", loginValidatorResult.getError());        
				redirectAttributes.addFlashAttribute("registration", registration);	       
				redirect = "redirect:/user/"+id;
			} else {
				User existUserForEdit = userService.getUserById(id);
				User newUserData = registration.getUser();				
				existUserForEdit.setName(newUserData.getName());
				existUserForEdit.setEmail(newUserData.getEmail());
				if(loginValidatorResult.isChangePassword()) {
					existUserForEdit.setPassword(newUserData.getPassword());
				}
				userService.updateClient(existUserForEdit, loginValidatorResult.isChangePassword());
				session.setAttribute("loginUser", existUserForEdit);				
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with update user operation: "+ e);
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred, please try edit profile later...");        
			redirectAttributes.addFlashAttribute("registration", registration);	       
			redirect = "redirect:/user/"+id;
		}
		return redirect;
	}
	
}

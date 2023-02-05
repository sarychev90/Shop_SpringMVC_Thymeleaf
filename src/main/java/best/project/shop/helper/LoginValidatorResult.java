package best.project.shop.helper;

import best.project.shop.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginValidatorResult extends ValidatorResult{
	
	private User loginUser;
	private boolean changePassword;
	
	public LoginValidatorResult(boolean isValidationPass, String error, User loginUser, boolean changePassword) {
		super(isValidationPass, error);
		this.loginUser = loginUser;
		this.changePassword = changePassword;
	}
	
}



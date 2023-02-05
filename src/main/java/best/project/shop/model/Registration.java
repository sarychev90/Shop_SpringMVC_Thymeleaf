package best.project.shop.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Registration extends User {

	private static final long serialVersionUID = 6656132477015333917L;

	private String repeatPassword;
	
	public User getUser(){
		User user = new User();
		user.setName(getName());
		user.setEmail(getEmail());
		user.setPassword(getPassword());
		return user;
	}

}

package best.project.shop.service;

import java.util.List;

import best.project.shop.model.User;

public interface UserService {
	
	public void createUser(User user);
	public void createClient(User user);
	public void updateUser(User user);
	public void updateClient(User user);
	public void updateClient(User user, boolean changePassword);
	public void createUsers(List<User> users);
	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public List<User> getAllUser();
	public boolean comparePasswords(String passwordForCheck, String encodedPassword);

}

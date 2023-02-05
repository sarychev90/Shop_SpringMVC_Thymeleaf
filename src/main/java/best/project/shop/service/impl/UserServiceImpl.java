package best.project.shop.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import best.project.shop.model.Roles;
import best.project.shop.model.User;
import best.project.shop.repository.UserRepository;
import best.project.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	private PasswordEncoder encoder;
	
	private UserRepository userRepository;
	
	public UserServiceImpl(PasswordEncoder encoder, UserRepository userRepository) {
		this.encoder = encoder;
		this.userRepository = userRepository;
	}

	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void createUsers(List<User> users) {
		userRepository.saveAll(users);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public void createClient(User user) {
		user.setRole(Roles.CLIENT.roleName);
		user.setPassword(encoder.encode(user.getPassword()));
		createUser(user);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	@Override
	public boolean comparePasswords(String passwordForCheck, String encodedPassword) {
		return encoder.matches(passwordForCheck, encodedPassword);
	}

	@Override
	public User getUserById(Long id) {
		User user = null;
		try {
			user = userRepository.getReferenceById(id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Problem with getUserById exception: "+ e);
		}
		return user;
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateClient(User user) {		
		user.setPassword(encoder.encode(user.getPassword()));
		updateUser(user);
	}

	@Override
	public void updateClient(User user, boolean changePassword) {
		if(changePassword) {
			updateClient(user);
		} else {	
			updateUser(user);
		}
		
	}

	
}

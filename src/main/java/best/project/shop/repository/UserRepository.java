package best.project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import best.project.shop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User getUserByEmail(String email);

}

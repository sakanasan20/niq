package tw.niq.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import tw.niq.domain.User;

public interface UserService extends UserDetailsService {

	List<User> getAllUsers();

	User getUserById(Long id);

}

package tw.niq.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.User;
import tw.niq.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	@Override
	public User saveUser(User user) {

		User savedUser;
		
		if (user.getId() != null) {
			User foundUser = userRepository.findById(user.getId()).get();
			foundUser.setUsername(user.getUsername());
			foundUser.setAccountNonExpired(user.getAccountNonExpired());
			foundUser.setAccountNonLocked(user.getAccountNonLocked());
			foundUser.setCredentialsNonExpired(user.getCredentialsNonExpired());
			foundUser.setEnabled(user.getEnabled());
			savedUser = userRepository.save(foundUser);
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			savedUser = userRepository.save(user);
		}

		return savedUser;
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

}

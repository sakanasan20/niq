package tw.niq.bootstrap;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Authority;
import tw.niq.domain.Role;
import tw.niq.domain.User;
import tw.niq.repository.AuthorityRepository;
import tw.niq.repository.RoleRepository;
import tw.niq.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		loadAuthorities();
		loadroles();
		loadUsers();
	}

	private void loadUsers() {
		Role admin = roleRepository.findByRoleName("ADMIN").orElseThrow();
		Role user = roleRepository.findByRoleName("USER").orElseThrow();
		Role guest = roleRepository.findByRoleName("GUEST").orElseThrow();
		userRepository.saveAllAndFlush(Arrays.asList(
				User.builder().username("admin").password(bCryptPasswordEncoder.encode("admin")).role(admin).build(), 
				User.builder().username("user").password(bCryptPasswordEncoder.encode("user")).role(user).build(), 
				User.builder().username("guest").password(bCryptPasswordEncoder.encode("guest")).role(guest).build()
		));
	}

	private void loadroles() {
		Authority read = authorityRepository.findByPermission("read").orElseThrow();
		Authority write = authorityRepository.findByPermission("write").orElseThrow();
		Authority delete = authorityRepository.findByPermission("delete").orElseThrow();
		roleRepository.saveAllAndFlush(Arrays.asList(
				Role.builder().roleName("ADMIN").authority(read).authority(write).authority(delete).build(), 
				Role.builder().roleName("USER").authority(read).authority(write).build(), 
				Role.builder().roleName("GUEST").authority(read).build()
		));
	}

	private void loadAuthorities() {
		authorityRepository.saveAllAndFlush(Arrays.asList(
				Authority.builder().permission("read").build(), 
				Authority.builder().permission("write").build(), 
				Authority.builder().permission("delete").build()
		));
	}

}

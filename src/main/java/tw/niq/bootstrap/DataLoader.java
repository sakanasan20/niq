package tw.niq.bootstrap;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Authority;
import tw.niq.domain.Beer;
import tw.niq.domain.BeerStyle;
import tw.niq.domain.Customer;
import tw.niq.domain.Role;
import tw.niq.domain.User;
import tw.niq.repository.AuthorityRepository;
import tw.niq.repository.BeerRepository;
import tw.niq.repository.CustomerRepository;
import tw.niq.repository.RoleRepository;
import tw.niq.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
	
	@Transactional
	@Override
	public void run(String... args) throws Exception {
		
		loadAuthorities();
		loadroles();
		loadUsers();
		
		loadBeers();
		loadCustomers();
	}
	
	private void loadCustomers() {

		if (customerRepository.count() == 0) {
			
			Customer customer1 = Customer.builder()
					.name("Customer 1")
					.build();

			Customer customer2 = Customer.builder()
					.name("Customer 2")
					.build();

			Customer customer3 = Customer.builder()
					.name("Customer 3")
					.version(1)
					.build();

			customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
		}
	}
	
	private void loadBeers() {
		
		if (beerRepository.count() == 0) {
			
			Beer beer1 = Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle(BeerStyle.PALE_ALE)
					.upc("12356")
					.price(new BigDecimal("12.99"))
					.quantityOnHand(122)
					.build();

			Beer beer2 = Beer.builder()
					.beerName("Crank")
					.beerStyle(BeerStyle.PALE_ALE)
					.upc("12356222")
					.price(new BigDecimal("11.99"))
					.quantityOnHand(392)
					.build();

			Beer beer3 = Beer.builder()
					.beerName("Sunshine City")
					.beerStyle(BeerStyle.IPA)
					.upc("12356")
					.price(new BigDecimal("13.99"))
					.quantityOnHand(144)
					.build();

			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);
		}
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

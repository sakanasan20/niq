package tw.niq.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tw.niq.repository.CustomerRepository;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	
}

package tw.niq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tw.niq.service.CustomerService;

@RequiredArgsConstructor
@RestController
@RequestMapping(CustomerController.PATH_V1_CUSTOMERS)
public class CustomerController {

	public static final String PATH_V1_CUSTOMERS= "/api/v1/customers";
	public static final String PATH_V1_CUSTOMERS_ID = PATH_V1_CUSTOMERS + "/{id}";

	private final CustomerService customerService;

//	@GetMapping
//	public List<CustomerDto> getCustomers() {
//
//		return customerService.getCustomers();
//	}
//
//	@GetMapping(value = "/{customerId}")
//	public CustomerDto getCustomerById(@PathVariable("customerId") UUID id) {
//
//		return customerService.getCustomerById(id);
//	}
//
//	@PostMapping
//	public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customer) {
//
//		CustomerDto savedCustomer = customerService.createCustomer(customer);
//
//		HttpHeaders headers = new HttpHeaders();
//
//		headers.add(HttpHeaders.LOCATION, CustomerController.PATH_V1_CUSTOMER + "/" + savedCustomer.getId().toString());
//
//		return new ResponseEntity<>(headers, HttpStatus.CREATED);
//	}
//
//	@PutMapping("{customerId}")
//	public ResponseEntity<Void> updateCustomerById(@PathVariable("customerId") UUID id, @RequestBody CustomerDto customer) {
//
//		if (customerService.updateCustomerById(id, customer).isEmpty()) {
//			throw new NotFoundException();
//		}
//
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//
//	@PatchMapping("{customerId}")
//	public ResponseEntity<Void> patchCustomerById(@PathVariable("customerId") UUID id, @RequestBody CustomerDto customer) {
//
//		if (customerService.patchCustomerById(id, customer).isEmpty()) {
//			throw new NotFoundException();
//		}
//
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
//
//	@DeleteMapping("{customerId}")
//	public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") UUID id) {
//
//		if (!customerService.deleteCustomerById(id)) {
//        	throw new NotFoundException();
//        }
//
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

}

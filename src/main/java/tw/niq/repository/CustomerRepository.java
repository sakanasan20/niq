package tw.niq.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}

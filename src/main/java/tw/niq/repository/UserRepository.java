package tw.niq.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Optional<List<User>> findAllByAccountNonLockedAndLastModifiedDateIsBefore(Boolean accountNonLocked, Timestamp timestamp);
	
}

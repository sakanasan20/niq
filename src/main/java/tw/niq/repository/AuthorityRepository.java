package tw.niq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByPermission(String permission);
	
}

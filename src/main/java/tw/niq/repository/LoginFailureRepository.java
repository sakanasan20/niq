package tw.niq.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.LoginFailure;
import tw.niq.domain.User;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Long> {
	
	List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);

}

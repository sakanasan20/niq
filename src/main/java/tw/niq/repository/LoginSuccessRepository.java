package tw.niq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.LoginSuccess;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Long> {

}

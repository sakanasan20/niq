package tw.niq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	Optional<Menu> findByMenuName(String menuName);

}

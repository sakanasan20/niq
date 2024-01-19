package tw.niq.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.niq.domain.Beer;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

	List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
	
}

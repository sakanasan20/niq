package tw.niq.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import tw.niq.domain.Beer;

public interface BeerService {

	Optional<List<Beer>> getBeers();

	Optional<Beer> getBeerById(UUID id);

	Optional<Beer> createBeer(Beer beer);

	Optional<Beer> updateBeerById(UUID id, Beer beer);

	Optional<Beer> patchBeerById(UUID id, Beer beer);

	Boolean deleteBeerById(UUID id);

}

package tw.niq.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Beer;
import tw.niq.exception.NotFoundException;
import tw.niq.repository.BeerRepository;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
	
	private final BeerRepository beerRepository;

	@Override
	public Optional<List<Beer>> getBeers() {
		
		List<Beer> foundBeers = beerRepository.findAll();
		
		return Optional.of(foundBeers);
	}

	@Override
	public Optional<Beer> getBeerById(UUID id) {
		
		Beer foundBeer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
		
		return Optional.of(foundBeer);
	}

	@Override
	public Optional<Beer> createBeer(Beer beer) {
		
		Beer savedBeer = beerRepository.save(beer);
		
		return Optional.of(savedBeer);
	}

	@Override
	public Optional<Beer> updateBeerById(UUID id, Beer beer) {
		
		AtomicReference<Optional<Beer>> atomicReference = new AtomicReference<>();
		
		beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
			
			foundBeer.setBeerName(beer.getBeerName());
			foundBeer.setPrice(beer.getPrice());
			foundBeer.setUpc(beer.getUpc());
			foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
			
			atomicReference.set(Optional.of(beerRepository.save(foundBeer)));
		}, () -> {
			atomicReference.set(Optional.empty());
		});

		return atomicReference.get();
	}

	@Override
	public Optional<Beer> patchBeerById(UUID id, Beer beer) {

		AtomicReference<Optional<Beer>> atomicReference = new AtomicReference<>();
		
		beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
			
			if (StringUtils.hasText(beer.getBeerName())) foundBeer.setBeerName(beer.getBeerName());
			if (beer.getBeerStyle() != null) foundBeer.setBeerStyle(beer.getBeerStyle());
			if (beer.getPrice() != null) foundBeer.setPrice(beer.getPrice());
			if (beer.getQuantityOnHand() != null) foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
			if (StringUtils.hasText(beer.getUpc())) foundBeer.setUpc(beer.getUpc());
			
			atomicReference.set(Optional.of(beerRepository.save(foundBeer)));
		}, () -> {
			atomicReference.set(Optional.empty());
		});

		return atomicReference.get();
	}

	@Override
	public Boolean deleteBeerById(UUID id) {

		if (!beerRepository.existsById(id)) {
			return false;
		}
		
		beerRepository.deleteById(id);
		
		return true;
	}

}

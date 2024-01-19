package tw.niq.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tw.niq.domain.Beer;
import tw.niq.exception.NotFoundException;
import tw.niq.service.BeerService;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.PATH_V1_BEERS)
public class BeerController {
	
	public static final String PATH_V1_BEERS = "/api/v1/beers";
	public static final String PATH_V1_BEERS_ID = PATH_V1_BEERS + "/{id}";

	private final BeerService beerService;
	
	@GetMapping
	public List<Beer> getBeers() {
		return beerService.getBeers().get();
	}
	
	@GetMapping("/{id}")
	public Beer getBeerById(@PathVariable("id") UUID id) {
		return beerService.getBeerById(id).get();
	}
	
	@PostMapping
	public ResponseEntity<Void> createBeer(@RequestBody Beer beer) {
		
		Beer createdBeer = beerService.createBeer(beer).get();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add(HttpHeaders.LOCATION, BeerController.PATH_V1_BEERS + "/" + createdBeer.getId().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateBeerById(@PathVariable("id") UUID id, @Validated @RequestBody Beer beer) {
		
		if (beerService.updateBeerById(id, beer).isEmpty()) {
			throw new NotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchBeerById(@PathVariable("id") UUID id, @Validated @RequestBody Beer beer) {
		
		if (beerService.patchBeerById(id, beer).isEmpty()) {
			throw new NotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeerById(@PathVariable("id") UUID id) {

        if (!beerService.deleteBeerById(id)) {
        	throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
}

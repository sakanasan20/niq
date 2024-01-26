package tw.niq.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.niq.domain.Beer;
import tw.niq.domain.BeerStyle;
import tw.niq.exception.NotFoundException;
import tw.niq.service.BeerService;

//@WithMockUser("admin")
//@WebMvcTest(BeerController.class)
class BeerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BeerService beerService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	List<Beer> testBeers;

	Beer testBeer;
	
	String testBeerName;

	@BeforeEach
	void setUp() throws Exception {
		
		testBeers = Arrays.asList(
				Beer.builder()
						.id(UUID.randomUUID())
						.beerName("Galaxy Cat")
						.beerStyle(BeerStyle.PALE_ALE)
						.upc("12356")
						.price(new BigDecimal("12.99"))
						.quantityOnHand(122)
						.createdDate(LocalDateTime.now())
						.updateDate(LocalDateTime.now())
						.build(),
				Beer.builder()
						.id(UUID.randomUUID())
						.beerName("Crank")
						.beerStyle(BeerStyle.PALE_ALE)
						.upc("12356222")
						.price(new BigDecimal("11.99"))
						.quantityOnHand(392)
						.createdDate(LocalDateTime.now())
						.updateDate(LocalDateTime.now())
						.build(),
				Beer.builder()
						.id(UUID.randomUUID())
						.beerName("Sunshine City")
						.beerStyle(BeerStyle.IPA)
						.upc("12356")
						.price(new BigDecimal("13.99"))
						.quantityOnHand(144)
						.createdDate(LocalDateTime.now())
						.updateDate(LocalDateTime.now())
						.build());

		testBeer = testBeers.get(0);
		
		testBeerName = "New Beer Name";
	}

//	@Test
	void testGetBeers() throws Exception {
		
		given(beerService.getBeers())
			.willReturn(Optional.of(testBeers));

		mockMvc
			.perform(
				get(BeerController.PATH_V1_BEERS)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(testBeers.size())));
	}

//	@Test
	void testGetBeerById() throws Exception {
		
		given(beerService.getBeerById(any(UUID.class)))
			.willReturn(Optional.of(testBeer));
		
		mockMvc
			.perform(
				get(BeerController.PATH_V1_BEERS_ID, UUID.randomUUID())
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
			.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}
	
//	@Test
	void testGetBeerById_whenIdNotFound_shouldThrowNotFoundException() throws Exception {

		given(beerService.getBeerById(any(UUID.class)))
			.willThrow(NotFoundException.class);

		mockMvc
			.perform(
				get(BeerController.PATH_V1_BEERS_ID, UUID.randomUUID()))
			.andExpect(status().isNotFound());
	}

//	@Test
	void testCreateBeer() throws JsonProcessingException, Exception {
		
		Beer beer = testBeer;
		beer.setId(null);

		given(beerService.createBeer(any(Beer.class)))
			.willReturn(Optional.of(testBeers.get(1)));

		mockMvc
			.perform(
				post(BeerController.PATH_V1_BEERS)
					.with(csrf())
					.content(objectMapper.writeValueAsString(beer))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().exists(HttpHeaders.LOCATION));
	}

//	@Test
//	void testUpdateBeerById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPatchBeerById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDeleteBeerById() {
//		fail("Not yet implemented");
//	}

}

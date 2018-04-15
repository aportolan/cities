package com.infinum.cities;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinum.cities.domain.City;
import com.infinum.cities.domain.QUser;
import com.infinum.cities.domain.User;
import com.infinum.cities.exception.CitiesException;
import com.infinum.cities.repository.UserRepository;
import com.infinum.cities.security.HeaderToken;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ControllerIntegrationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private String token;
	private User user;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
		this.user = userRepository.findAll(QUser.user.idUser.lt(10).and(QUser.user.listOfFavoritedCities.isNotEmpty()))
				.iterator().next();
		this.token = user.getToken();
	}

	@Test
	public void getCities() throws Exception {

		mockMvc.perform(get("/public/cities").accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void getCitiesOrderedByDate() throws Exception {

		mockMvc.perform(get("/public/cities/orderedByDate")
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void getByMostFavorited() throws Exception {

		mockMvc.perform(get("/public/cities/byMostFavorited")
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void token() throws Exception {

		mockMvc.perform(post("/token", 1).content(getUserCredentials()).accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk()).andExpect(header().exists(HeaderToken.X_AUTH_TOKEN.getValue()));

	}

	@Test
	public void getFavoriteCities() throws Exception {

		mockMvc.perform(get("/secured/cities/getFavorites").header(HeaderToken.X_AUTH_TOKEN.getValue(), token)
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void createCity() throws Exception {

		mockMvc.perform(post("/secured/cities/create").content(getCityContentAsJson())
				.header(HeaderToken.X_AUTH_TOKEN.getValue(), token).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void createFavoriteCity() throws Exception {

		mockMvc.perform(post("/secured/cities/createFavorite").content(getFavCityContentAsJson())
				.header(HeaderToken.X_AUTH_TOKEN.getValue(), token).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void deleteFavoriteCity() throws Exception {

		mockMvc.perform(
				delete("/secured/cities/deleteFavorite/{id}", 1).header(HeaderToken.X_AUTH_TOKEN.getValue(), token)
						.accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
				.andExpect(status().isOk());

	}

	private String getCityContentAsJson() {
		ObjectMapper om = new ObjectMapper();
		City city = new City();
		city.setName("Zagreb");
		city.setPopulation(700000l);
		city.setDescription("Croatian capital");
		try {
			return om.writeValueAsString(city);
		} catch (JsonProcessingException e) {
			throw new CitiesException(e);
		}
	}

	private String getFavCityContentAsJson() {
		ObjectMapper om = new ObjectMapper();
		City city = user.getListOfFavoritedCities().get(0);

		String json;
		try {
			json = om.writeValueAsString(city);
		} catch (JsonProcessingException e) {
			throw new CitiesException(e);
		}
		return json;
	}

	private String getUserCredentials() {
		ObjectMapper om = new ObjectMapper();
		User user = new User();
		user.setMail("abc@abc.com");
		user.setPassword("password");

		String json;
		try {
			json = om.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			throw new CitiesException(e);
		}
		return json;
	}
}

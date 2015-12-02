package com.trial.crossover.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trial.crossover.BaseTest;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.model.Product;
import com.trial.crossover.service.ProductService;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
public class CustomerControllerTest extends BaseTest {

	@Autowired
	private ProductService productService;

	private ProductDTO p1;
	private ProductDTO p2;

	@Override
	@Before
	public void init() {
		super.init();

		//adding some products
		p1 = new ProductDTO();
		p1.setDescription("description 1");
		p1.setPrice(10);
		p1.setQuantity(100);
		p1 = productService.create(p1);

		p2 = new ProductDTO();
		p2.setDescription("description 2");
		p2.setPrice(100.50f);
		p2.setQuantity(100);
		p2 = productService.create(p2);
	}

	@Override
	@After
	public void clean() {
		super.clean();
	}

	@org.junit.Test
	public void test_getAllProducts() throws Exception {
		JsonArray array = gson.fromJson(mockMvc.perform(get("/products/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class);

		JsonObject obj1 = (JsonObject) array.get(0);
		JsonObject obj2 = (JsonObject) array.get(1);

		MatcherAssert.assertThat(array.size(), equalTo(2));

		MatcherAssert.assertThat(obj1.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj1.get("description").getAsString(), equalTo("description 1"));
		MatcherAssert.assertThat(obj1.get("price").getAsString(), equalTo("10.0"));
		MatcherAssert.assertThat(obj1.get("quantity").getAsString(), equalTo("100"));

		MatcherAssert.assertThat(obj2.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj2.get("description").getAsString(), equalTo("description 2"));
		MatcherAssert.assertThat(obj2.get("price").getAsString(), equalTo("100.5"));
		MatcherAssert.assertThat(obj2.get("quantity").getAsString(), equalTo("100"));
	}

	@org.junit.Test
	public void test_createNewProducts() throws Exception {
		int previousProducts = gson.fromJson(mockMvc.perform(get("/products/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		Product prod = new Product();
		prod.setDescription("new Prod");
		prod.setPrice(66.66f);
		prod.setQuantity(99);
		String json = gson.toJson(prod);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/products/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		int currentProducts = gson.fromJson(mockMvc.perform(get("/products/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		MatcherAssert.assertThat(currentProducts, greaterThan(previousProducts));
		MatcherAssert.assertThat(obj.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj.get("description").getAsString(), equalTo("new Prod"));
		MatcherAssert.assertThat(obj.get("price").getAsString(), equalTo("66.66"));
		MatcherAssert.assertThat(obj.get("quantity").getAsString(), equalTo("99"));
	}

	@org.junit.Test
	public void test_getProduct() throws Exception {
		JsonObject obj = gson.fromJson(mockMvc.perform(get("/products/{id}", p1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);


		MatcherAssert.assertThat(obj.get("id").getAsString(), equalTo(p1.getId().toString()));
		MatcherAssert.assertThat(obj.get("description").getAsString(), equalTo(p1.getDescription()));
		MatcherAssert.assertThat(obj.get("price").getAsString(), equalTo(String.valueOf(p1.getPrice())));
		MatcherAssert.assertThat(obj.get("quantity").getAsString(), equalTo(String.valueOf(p1.getQuantity())));
	}

}

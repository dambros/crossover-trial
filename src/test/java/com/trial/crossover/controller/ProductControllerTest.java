package com.trial.crossover.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trial.crossover.BaseTest;
import com.trial.crossover.dto.ProductDTO;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
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
public class ProductControllerTest extends BaseTest {

	@Override
	@Before
	public void init() {
		super.init();
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

		MatcherAssert.assertThat(obj1.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(obj1.get("description").getAsString(), equalTo(p1.getDescription()));
		MatcherAssert.assertThat(obj1.get("price").getAsFloat(), equalTo(p1.getPrice()));
		MatcherAssert.assertThat(obj1.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity()));

		MatcherAssert.assertThat(obj2.get("id").getAsLong(), equalTo(p2.getId()));
		MatcherAssert.assertThat(obj2.get("description").getAsString(), equalTo(p2.getDescription()));
		MatcherAssert.assertThat(obj2.get("price").getAsFloat(), equalTo(p2.getPrice()));
		MatcherAssert.assertThat(obj2.get("availableQuantity").getAsInt(), equalTo(p2.getAvailableQuantity()));
	}

	@org.junit.Test
	public void test_createNewProduct() throws Exception {
		int previousProducts = gson.fromJson(mockMvc.perform(get("/products/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		ProductDTO prod = new ProductDTO();
		prod.setDescription("new Prod");
		prod.setPrice(66.66f);
		prod.setAvailableQuantity(99);
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
		MatcherAssert.assertThat(obj.get("description").getAsString(), equalTo(prod.getDescription()));
		MatcherAssert.assertThat(obj.get("price").getAsFloat(), equalTo(prod.getPrice()));
		MatcherAssert.assertThat(obj.get("availableQuantity").getAsInt(), equalTo(prod.getAvailableQuantity()));
	}

	@org.junit.Test
	public void test_createNewProductWithMissingFields() throws Exception {
		p1.setId(null);
		p1.setDescription(null);
		String json = gson.toJson(p1);

		mockMvc.perform(post("/products/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		p1.setDescription("new Prod");
		p1.setAvailableQuantity(null);
		String json2 = gson.toJson(p1);

		mockMvc.perform(post("/products/new")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		p1.setAvailableQuantity(100);
		p1.setPrice(null);
		String json3 = gson.toJson(p1);

		mockMvc.perform(post("/products/new")
				.contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_getProduct() throws Exception {
		JsonObject obj = gson.fromJson(mockMvc.perform(get("/products/{id}", p1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		MatcherAssert.assertThat(obj.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(obj.get("description").getAsString(), equalTo(p1.getDescription()));
		MatcherAssert.assertThat(obj.get("price").getAsFloat(), equalTo(p1.getPrice()));
		MatcherAssert.assertThat(obj.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity()));
	}

	@org.junit.Test
	public void test_getProductInvalidId() throws Exception {
		mockMvc.perform(get("/products/{id}", -1))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_updateProduct() throws Exception {
		p1.setDescription("new update");
		p1.setPrice(111f);
		p1.setAvailableQuantity(22);
		String json = gson.toJson(p1);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		MatcherAssert.assertThat(obj.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(obj.get("description").getAsString(), equalTo(p1.getDescription()));
		MatcherAssert.assertThat(obj.get("price").getAsFloat(), equalTo(p1.getPrice()));
		MatcherAssert.assertThat(obj.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity()));
	}

	@org.junit.Test
	public void test_updateProductWithMissingFields() throws Exception {
		p1.setDescription(null);
		String json = gson.toJson(p1);

		mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		p1.setDescription("new Prod");
		p1.setAvailableQuantity(null);
		String json2 = gson.toJson(p1);

		mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		p1.setAvailableQuantity(100);
		p1.setPrice(null);
		String json3 = gson.toJson(p1);

		mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		p1.setPrice(66f);
		p1.setId(null);
		String json4 = gson.toJson(p1);

		mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(json4))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_updateProductWithInvalidId() throws Exception {
		p1.setId(-1l);
		mockMvc.perform(post("/products/update")
				.contentType(MediaType.APPLICATION_JSON).content(gson.toJson(p1)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

}

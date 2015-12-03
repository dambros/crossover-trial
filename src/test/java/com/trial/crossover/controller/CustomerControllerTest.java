package com.trial.crossover.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trial.crossover.BaseTest;
import com.trial.crossover.dto.CustomerDTO;
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
public class CustomerControllerTest extends BaseTest {

	@Override
	@Before
	public void init() {
		super.init();
	}

	@org.junit.Test
	public void test_getAllCustomerss() throws Exception {
		JsonArray array = gson.fromJson(mockMvc.perform(get("/customers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class);

		JsonObject obj1 = (JsonObject) array.get(0);
		JsonObject obj2 = (JsonObject) array.get(1);

		MatcherAssert.assertThat(array.size(), equalTo(2));

		MatcherAssert.assertThat(obj1.get("id").getAsLong(), equalTo(c1.getId()));
		MatcherAssert.assertThat(obj1.get("name").getAsString(), equalTo(c1.getName()));
		MatcherAssert.assertThat(obj1.get("address").getAsString(), equalTo(c1.getAddress()));
		MatcherAssert.assertThat(obj1.get("phone1").getAsString(), equalTo(c1.getPhone1()));
		MatcherAssert.assertThat(obj1.get("phone2").getAsString(), equalTo(c1.getPhone2()));
		MatcherAssert.assertThat(obj1.get("creditLimit").getAsFloat(), equalTo(c1.getCreditLimit()));
		MatcherAssert.assertThat(obj1.has("currentCredit"), equalTo(false));

		MatcherAssert.assertThat(obj2.get("id").getAsLong(), equalTo(c2.getId()));
		MatcherAssert.assertThat(obj2.get("name").getAsString(), equalTo(c2.getName()));
		MatcherAssert.assertThat(obj2.get("address").getAsString(), equalTo(c2.getAddress()));
		MatcherAssert.assertThat(obj2.get("phone1").getAsString(), equalTo(c2.getPhone1()));
		MatcherAssert.assertThat(obj2.get("phone2").getAsString(), equalTo(c2.getPhone2()));
		MatcherAssert.assertThat(obj2.get("creditLimit").getAsFloat(), equalTo(c2.getCreditLimit()));
		MatcherAssert.assertThat(obj2.has("currentCredit"), equalTo(false));
	}

	@org.junit.Test
	public void test_createNewCustomer() throws Exception {
		int previousCustomers = gson.fromJson(mockMvc.perform(get("/customers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		CustomerDTO c = new CustomerDTO();
		c.setName("New Customer");
		c.setAddress("New Address");
		c.setPhone1("111111111");
		c.setPhone2("222222222");
		c.setCreditLimit(123f);
		String json = gson.toJson(c);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		int currentCustomers = gson.fromJson(mockMvc.perform(get("/customers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		MatcherAssert.assertThat(currentCustomers, greaterThan(previousCustomers));
		MatcherAssert.assertThat(obj.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj.get("name").getAsString(), equalTo(c.getName()));
		MatcherAssert.assertThat(obj.get("address").getAsString(), equalTo(c.getAddress()));
		MatcherAssert.assertThat(obj.get("phone1").getAsString(), equalTo(c.getPhone1()));
		MatcherAssert.assertThat(obj.get("phone2").getAsString(), equalTo(c.getPhone2()));
		MatcherAssert.assertThat(obj.get("creditLimit").getAsFloat(), equalTo(c.getCreditLimit()));
		MatcherAssert.assertThat(obj.has("currentCredit"), equalTo(false));
	}

	@org.junit.Test
	public void test_createNewCostumerWithMissingFields() throws Exception {
		c1.setId(null);
		c1.setName(null);
		String json = gson.toJson(c1);

		mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		c1.setName("New Customer");
		c1.setAddress(null);
		String json2 = gson.toJson(c1);

		mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		c1.setAddress("new Address");
		c1.setCreditLimit(null);
		String json3 = gson.toJson(c1);

		mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_getCustomer() throws Exception {
		JsonObject obj = gson.fromJson(mockMvc.perform(get("/customers/{id}", c1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		MatcherAssert.assertThat(obj.get("id").getAsLong(), equalTo(c1.getId()));
		MatcherAssert.assertThat(obj.get("name").getAsString(), equalTo(c1.getName()));
		MatcherAssert.assertThat(obj.get("address").getAsString(), equalTo(c1.getAddress()));
		MatcherAssert.assertThat(obj.get("phone1").getAsString(), equalTo(c1.getPhone1()));
		MatcherAssert.assertThat(obj.get("phone2").getAsString(), equalTo(c1.getPhone2()));
		MatcherAssert.assertThat(obj.get("creditLimit").getAsFloat(), equalTo(c1.getCreditLimit()));
	}

	@org.junit.Test
	public void test_getCustomerWithInvalidId() throws Exception {
		mockMvc.perform(get("/customers/{id}", -1))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_updateCustomer() throws Exception {
		c1.setName("New Customer xxx");
		c1.setAddress("New Address xxx");
		c1.setPhone1("111111111 xxx");
		c1.setPhone2("222222222 xxx");
		c1.setCreditLimit(12345f);
		String json = gson.toJson(c1);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		MatcherAssert.assertThat(obj.get("id").getAsLong(), equalTo(c1.getId()));
		MatcherAssert.assertThat(obj.get("name").getAsString(), equalTo("New Customer xxx"));
		MatcherAssert.assertThat(obj.get("address").getAsString(), equalTo("New Address xxx"));
		MatcherAssert.assertThat(obj.get("phone1").getAsString(), equalTo("111111111 xxx"));
		MatcherAssert.assertThat(obj.get("phone2").getAsString(), equalTo("222222222 xxx"));
		MatcherAssert.assertThat(obj.get("creditLimit").getAsFloat(), equalTo(12345f));
		MatcherAssert.assertThat(obj.has("currentCredit"), equalTo(false));
	}

	@org.junit.Test
	public void test_updateCustomerWithMissingFields() throws Exception {
		c1.setName(null);
		String json = gson.toJson(c1);

		mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		c1.setName("New Customer");
		c1.setAddress(null);
		String json2 = gson.toJson(c1);

		mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		c1.setAddress("new Address");
		c1.setCreditLimit(null);
		String json3 = gson.toJson(c1);

		mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		c1.setCreditLimit(123f);
		c1.setId(null);
		String json4 = gson.toJson(c1);

		mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(json4))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_updateCustomerWithInvalidId() throws Exception {
		c1.setId(-1l);
		mockMvc.perform(post("/customers/update")
				.contentType(MediaType.APPLICATION_JSON).content(gson.toJson(c1)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

}

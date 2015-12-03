package com.trial.crossover.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trial.crossover.BaseTest;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.service.CustomerService;
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
	private CustomerService customerService;

	private CustomerDTO c1;
	private CustomerDTO c2;

	@Override
	@Before
	public void init() {
		super.init();

		//adding some customers
		c1 = new CustomerDTO();
		c1.setName("Customer1");
		c1.setAddress("Address1");
		c1.setPhone1("123456789");
		c1.setPhone2("123456789");
		c1.setCreditLimit(1000L);
		c1 = customerService.create(c1);

		c2 = new CustomerDTO();
		c2.setName("Customer2");
		c2.setAddress("Address2");
		c2.setPhone1("123456789");
		c2.setPhone2("123456789");
		c2.setCreditLimit(5000L);
		c2 = customerService.create(c2);
	}

	@Override
	@After
	public void clean() {
		super.clean();
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

		MatcherAssert.assertThat(obj1.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj1.get("name").getAsString(), equalTo("Customer1"));
		MatcherAssert.assertThat(obj1.get("address").getAsString(), equalTo("Address1"));
		MatcherAssert.assertThat(obj1.get("phone1").getAsString(), equalTo("123456789"));
		MatcherAssert.assertThat(obj1.get("phone2").getAsString(), equalTo("123456789"));
		MatcherAssert.assertThat(obj1.get("creditLimit").getAsString(), equalTo("1000"));
		MatcherAssert.assertThat(obj1.has("currentCredit"), equalTo(false));

		MatcherAssert.assertThat(obj2.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj2.get("name").getAsString(), equalTo("Customer2"));
		MatcherAssert.assertThat(obj2.get("address").getAsString(), equalTo("Address2"));
		MatcherAssert.assertThat(obj2.get("phone1").getAsString(), equalTo("123456789"));
		MatcherAssert.assertThat(obj2.get("phone2").getAsString(), equalTo("123456789"));
		MatcherAssert.assertThat(obj2.get("creditLimit").getAsString(), equalTo("5000"));
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
		c.setCreditLimit(123L);
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
		MatcherAssert.assertThat(obj.get("name").getAsString(), equalTo("New Customer"));
		MatcherAssert.assertThat(obj.get("address").getAsString(), equalTo("New Address"));
		MatcherAssert.assertThat(obj.get("phone1").getAsString(), equalTo("111111111"));
		MatcherAssert.assertThat(obj.get("phone2").getAsString(), equalTo("222222222"));
		MatcherAssert.assertThat(obj.get("creditLimit").getAsString(), equalTo("123"));
		MatcherAssert.assertThat(obj.has("currentCredit"), equalTo(false));
	}

	@org.junit.Test
	public void test_createNewCostumerWithMissingFields() throws Exception {

		CustomerDTO c = new CustomerDTO();
		c.setAddress("New Address");
		c.setPhone1("111111111");
		c.setPhone2("222222222");
		c.setCreditLimit(123L);
		String json = gson.toJson(c);

		mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		CustomerDTO c2 = new CustomerDTO();
		c2.setName("New Customer");
		c2.setPhone1("111111111");
		c2.setPhone2("222222222");
		c2.setCreditLimit(123L);
		String json2 = gson.toJson(c2);

		mockMvc.perform(post("/customers/new")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		CustomerDTO c3 = new CustomerDTO();
		c3.setName("New Customer");
		c3.setAddress("New Address");
		c3.setPhone1("111111111");
		c3.setPhone2("222222222");
		String json3 = gson.toJson(c3);

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

		MatcherAssert.assertThat(obj.get("id").getAsString(), equalTo(c1.getId().toString()));
		MatcherAssert.assertThat(obj.get("name").getAsString(), equalTo(c1.getName()));
		MatcherAssert.assertThat(obj.get("address").getAsString(), equalTo(c1.getAddress()));
		MatcherAssert.assertThat(obj.get("phone1").getAsString(), equalTo(c1.getPhone1()));
		MatcherAssert.assertThat(obj.get("phone2").getAsString(), equalTo(c1.getPhone2()));
		MatcherAssert.assertThat(obj.get("creditLimit").getAsString(), equalTo(String.valueOf(c1.getCreditLimit())));
	}

}

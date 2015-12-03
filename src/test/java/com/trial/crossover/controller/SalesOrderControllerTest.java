package com.trial.crossover.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.trial.crossover.BaseTest;
import com.trial.crossover.dto.SalesOrderEditionDTO;
import com.trial.crossover.dto.SalesOrderProductEditionDTO;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class SalesOrderControllerTest extends BaseTest {

	@Override
	@Before
	public void init() {
		super.init();
	}

	@org.junit.Test
	public void test_getAllSalesOrder() throws Exception {
		JsonArray array = gson.fromJson(mockMvc.perform(get("/orders/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class);

		JsonObject obj1 = array.get(0).getAsJsonObject();
		JsonObject obj2 = array.get(1).getAsJsonObject();

		JsonArray prodArray1 = obj1.getAsJsonArray("orderProducts");
		JsonObject orderProd1 = prodArray1.get(0).getAsJsonObject();
		JsonObject prod1 = orderProd1.get("product").getAsJsonObject();

		JsonArray prodArray2 = obj2.getAsJsonArray("orderProducts");
		JsonObject orderProd2 = prodArray2.get(0).getAsJsonObject();
		JsonObject orderProd3 = prodArray2.get(1).getAsJsonObject();
		JsonObject prod2 = orderProd2.get("product").getAsJsonObject();
		JsonObject prod3 = orderProd3.get("product").getAsJsonObject();

		MatcherAssert.assertThat(array.size(), equalTo(2));

		MatcherAssert.assertThat(obj1.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj1.get("orderNumber").getAsLong(), equalTo(s1.getOrderNumber()));
		MatcherAssert.assertThat(obj1.get("totalPrice").getAsFloat(), equalTo(s1.getTotalPrice()));
		MatcherAssert.assertThat(orderProd1.get("id").getAsLong(), notNullValue());
		MatcherAssert.assertThat(orderProd1.get("productQuantity").getAsInt(), equalTo(prods1.get(0).getProductQuantity()));
		MatcherAssert.assertThat(prod1.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(prod1.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity()));
		MatcherAssert.assertThat(obj1.get("customer").getAsJsonObject().get("id").getAsLong(), equalTo(c1.getId()));


		MatcherAssert.assertThat(obj2.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj2.get("orderNumber").getAsLong(), equalTo(s2.getOrderNumber()));
		MatcherAssert.assertThat(obj2.get("totalPrice").getAsFloat(), equalTo(s2.getTotalPrice()));
		MatcherAssert.assertThat(orderProd2.get("id").getAsLong(), notNullValue());
		MatcherAssert.assertThat(orderProd2.get("productQuantity").getAsInt(), equalTo(prods2.get(0).getProductQuantity()));
		MatcherAssert.assertThat(orderProd3.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(orderProd3.get("productQuantity").getAsInt(), equalTo(prods2.get(1).getProductQuantity()));
		MatcherAssert.assertThat(prod2.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(prod2.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity()));
		MatcherAssert.assertThat(prod3.get("id").getAsLong(), equalTo(p2.getId()));
		MatcherAssert.assertThat(prod3.get("availableQuantity").getAsInt(), equalTo(p2.getAvailableQuantity()));
		MatcherAssert.assertThat(obj2.get("customer").getAsJsonObject().get("id").getAsLong(), equalTo(c2.getId()));
	}

	@org.junit.Test
	public void test_createNewSalesOrder() throws Exception {
		c1 = customerService.get(c1.getId());

		int previousOrders = gson.fromJson(mockMvc.perform(get("/orders/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		SalesOrderProductEditionDTO prodDto1 = new SalesOrderProductEditionDTO();
		prodDto1.setProductQuantity(3);
		prodDto1.setProduct(p1.getId());
		List<SalesOrderProductEditionDTO> prods = new ArrayList<>(Arrays.asList(prodDto1));

		SalesOrderEditionDTO d = new SalesOrderEditionDTO();
		d.setTotalPrice(p1.getPrice() * prodDto1.getProductQuantity());
		d.setCustomer(c1.getId());
		d.setOrderNumber(12345678l);
		d.setOrderProducts(prods);
		String json = gson.toJson(d);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		JsonArray prodArray1 = obj.getAsJsonArray("orderProducts");
		JsonObject orderProd1 = prodArray1.get(0).getAsJsonObject();
		JsonObject prod1 = orderProd1.get("product").getAsJsonObject();

		int currentOrders = gson.fromJson(mockMvc.perform(get("/orders/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonArray.class).size();

		MatcherAssert.assertThat(currentOrders, greaterThan(previousOrders));
		MatcherAssert.assertThat(obj.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj.get("orderNumber").getAsLong(), equalTo(d.getOrderNumber()));
		MatcherAssert.assertThat(obj.get("totalPrice").getAsFloat(), equalTo(d.getTotalPrice()));
		MatcherAssert.assertThat(orderProd1.get("id").getAsLong(), notNullValue());
		MatcherAssert.assertThat(orderProd1.get("productQuantity").getAsInt(), equalTo(prods.get(0).getProductQuantity()));
		MatcherAssert.assertThat(prod1.get("id").getAsLong(), equalTo(p1.getId()));
		MatcherAssert.assertThat(prod1.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity() - 3));

		JsonObject customer = obj.get("customer").getAsJsonObject();
		MatcherAssert.assertThat(customer.get("id").getAsLong(), equalTo(c1.getId()));

		//check customer credit
		MatcherAssert.assertThat(customer.get("currentCredit").getAsFloat(), equalTo(c1.getCurrentCredit() + d.getTotalPrice()));
		MatcherAssert.assertThat(customer.get("creditLimit").getAsFloat(), equalTo(c1.getCreditLimit() - d.getTotalPrice()));
	}

	@org.junit.Test
	public void test_createNewSalesOrderWithMissingFields() throws Exception {
		SalesOrderProductEditionDTO prodDto1 = new SalesOrderProductEditionDTO();
		prodDto1.setProductQuantity(3);
		prodDto1.setProduct(p1.getId());
		List<SalesOrderProductEditionDTO> prods = new ArrayList<>(Arrays.asList(prodDto1));

		SalesOrderEditionDTO d = new SalesOrderEditionDTO();
		d.setOrderNumber(null);
		d.setTotalPrice(p1.getPrice() * prodDto1.getProductQuantity());
		d.setCustomer(c1.getId());
		d.setOrderProducts(prods);
		String json = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		d.setOrderNumber(123l);
		d.setTotalPrice(null);
		String json2 = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		d.setTotalPrice(p1.getPrice() * prodDto1.getProductQuantity());
		d.setCustomer(null);
		String json3 = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));


		d.setCustomer(c1.getId());
		d.setOrderProducts(null);
		String json4 = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json4))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		prodDto1.setProduct(null);
		prods.set(0, prodDto1);
		d.setOrderProducts(prods);
		String json5 = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json5))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		prodDto1.setProduct(p1.getId());
		prodDto1.setProductQuantity(null);
		prods.set(0, prodDto1);
		d.setOrderProducts(prods);
		String json6 = gson.toJson(d);

		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json6))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_createNewSalesOrderWithWrongBussinessLogic() throws Exception {
		SalesOrderProductEditionDTO prodDto1 = new SalesOrderProductEditionDTO();
		prodDto1.setProductQuantity(3);
		prodDto1.setProduct(p2.getId());
		List<SalesOrderProductEditionDTO> prods = new ArrayList<>(Arrays.asList(prodDto1));

		SalesOrderEditionDTO d = new SalesOrderEditionDTO();
		d.setOrderNumber(111l);
		d.setTotalPrice(123f);
		d.setCustomer(c1.getId());
		d.setOrderProducts(prods);
		String json = gson.toJson(d);

		//wrong total amount
		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		prodDto1.setProductQuantity(11);
		d.setTotalPrice(p2.getPrice() * prodDto1.getProductQuantity());
		prods.set(0, prodDto1);
		d.setOrderProducts(prods);
		json = gson.toJson(d);

		//wrong available product amount
		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));

		prodDto1.setProductQuantity(4);
		d.setTotalPrice(p2.getPrice() * prodDto1.getProductQuantity());
		prods.set(0, prodDto1);
		d.setOrderProducts(prods);
		json = gson.toJson(d);

		//not enough credit
		mockMvc.perform(post("/orders/new")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_getSalesOrder() throws Exception {
		JsonObject obj = gson.fromJson(mockMvc.perform(get("/orders/{id}", s1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		MatcherAssert.assertThat(obj.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj.get("orderNumber").getAsLong(), equalTo(s1.getOrderNumber()));
		MatcherAssert.assertThat(obj.get("totalPrice").getAsFloat(), equalTo(s1.getTotalPrice()));
	}

	@org.junit.Test
	public void test_getSalesOrderWithInvalidId() throws Exception {
		mockMvc.perform(get("/orders/{id}", -1))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@org.junit.Test
	public void test_updateSalesOrder() throws Exception {
		SalesOrderProductEditionDTO prodDto1 = new SalesOrderProductEditionDTO();
		prodDto1.setProductQuantity(7); //6 items added
		prodDto1.setProduct(p1.getId());
		List<SalesOrderProductEditionDTO> prods = new ArrayList<>(Arrays.asList(prodDto1));

		SalesOrderEditionDTO d = new SalesOrderEditionDTO();
		d.setId(s1.getId());
		d.setTotalPrice(p1.getPrice() * prodDto1.getProductQuantity());
		d.setCustomer(c1.getId());
		d.setOrderNumber(12345678l);
		d.setOrderProducts(prods);
		String json = gson.toJson(d);

		JsonObject obj = gson.fromJson(mockMvc.perform(post("/orders/update")
				.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString(), JsonObject.class);

		JsonArray prodArray1 = obj.getAsJsonArray("orderProducts");
		JsonObject orderProd1 = prodArray1.get(0).getAsJsonObject();
		JsonObject prod1 = orderProd1.get("product").getAsJsonObject();

		MatcherAssert.assertThat(obj.get("id").getAsString(), notNullValue());
		MatcherAssert.assertThat(obj.get("orderNumber").getAsLong(), equalTo(d.getOrderNumber()));
		MatcherAssert.assertThat(obj.get("totalPrice").getAsFloat(), equalTo(d.getTotalPrice()));
		MatcherAssert.assertThat(orderProd1.get("id").getAsLong(), notNullValue());
		MatcherAssert.assertThat(orderProd1.get("productQuantity").getAsInt(), equalTo(prods.get(0).getProductQuantity()));
		MatcherAssert.assertThat(prod1.get("id").getAsLong(), equalTo(p1.getId()));

		//6 p1 items were added on this update
		MatcherAssert.assertThat(prod1.get("availableQuantity").getAsInt(), equalTo(p1.getAvailableQuantity() - 6));
	}
//
//	@org.junit.Test
//	public void test_updateProductWithMissingFields() throws Exception {
//		p1.setDescription(null);
//		String json = gson.toJson(p1);
//
//		mockMvc.perform(post("/products/update")
//				.contentType(MediaType.APPLICATION_JSON).content(json))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType("application/json;charset=UTF-8"));
//
//		p1.setDescription("new Prod");
//		p1.setAvailableQuantity(null);
//		String json2 = gson.toJson(p1);
//
//		mockMvc.perform(post("/products/update")
//				.contentType(MediaType.APPLICATION_JSON).content(json2))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType("application/json;charset=UTF-8"));
//
//		p1.setAvailableQuantity(100);
//		p1.setPrice(null);
//		String json3 = gson.toJson(p1);
//
//		mockMvc.perform(post("/products/update")
//				.contentType(MediaType.APPLICATION_JSON).content(json3))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType("application/json;charset=UTF-8"));
//
//		p1.setPrice(66f);
//		p1.setId(null);
//		String json4 = gson.toJson(p1);
//
//		mockMvc.perform(post("/products/update")
//				.contentType(MediaType.APPLICATION_JSON).content(json4))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType("application/json;charset=UTF-8"));
//	}
//
//	@org.junit.Test
//	public void test_updateProductWithInvalidId() throws Exception {
//		p1.setId(-1l);
//		mockMvc.perform(post("/products/update")
//				.contentType(MediaType.APPLICATION_JSON).content(gson.toJson(p1)))
//				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType("application/json;charset=UTF-8"));
//	}

}

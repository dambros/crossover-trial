package com.trial.crossover;

import com.google.gson.Gson;
import com.trial.crossover.config.WebConfig;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.dto.SalesOrderDTO;
import com.trial.crossover.dto.SalesOrderEditionDTO;
import com.trial.crossover.dto.SalesOrderProductEditionDTO;
import com.trial.crossover.service.CustomerService;
import com.trial.crossover.service.ProductService;
import com.trial.crossover.service.SalesOrderService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@Ignore
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseTest {

	@Autowired
	public WebApplicationContext wac;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected SalesOrderService salesOrderService;

	protected MockMvc mockMvc;
	protected Gson gson = new Gson();
	protected ProductDTO p1;
	protected ProductDTO p2;
	protected CustomerDTO c1;
	protected CustomerDTO c2;
	protected SalesOrderDTO s1;
	protected SalesOrderDTO s2;

	protected List<SalesOrderProductEditionDTO> prods1;
	protected List<SalesOrderProductEditionDTO> prods2;

	public void init() {
		Assert.assertNotNull(wac);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		//adding some products
		p1 = new ProductDTO();
		p1.setDescription("description 1");
		p1.setPrice(10f);
		p1.setAvailableQuantity(100);
		p1 = productService.create(p1);

		p2 = new ProductDTO();
		p2.setDescription("description 2");
		p2.setPrice(300.50f);
		p2.setAvailableQuantity(10);
		p2 = productService.create(p2);

		//adding some customers
		c1 = new CustomerDTO();
		c1.setName("Customer1");
		c1.setAddress("Address1");
		c1.setPhone1("123456789");
		c1.setPhone2("123456789");
		c1.setCreditLimit(1000.55f);
		c1 = customerService.create(c1);

		c2 = new CustomerDTO();
		c2.setName("Customer2");
		c2.setAddress("Address2");
		c2.setPhone1("123456789");
		c2.setPhone2("123456789");
		c2.setCreditLimit(5000f);
		c2 = customerService.create(c2);

		//adding some sales orders
		SalesOrderProductEditionDTO prodDto1 = new SalesOrderProductEditionDTO();
		prodDto1.setProductQuantity(1);
		prodDto1.setProduct(p1.getId());
		prods1 = new ArrayList<>(Arrays.asList(prodDto1));

		SalesOrderEditionDTO d = new SalesOrderEditionDTO();
		d.setTotalPrice(p1.getPrice() * prodDto1.getProductQuantity());
		d.setCustomer(c1.getId());
		d.setOrderNumber(123l);
		d.setOrderProducts(prods1);
		s1 = (SalesOrderDTO) salesOrderService.create(d);

		SalesOrderProductEditionDTO prodDto2 = new SalesOrderProductEditionDTO();
		prodDto2.setProductQuantity(1);
		prodDto2.setProduct(p1.getId());

		SalesOrderProductEditionDTO prodDto3 = new SalesOrderProductEditionDTO();
		prodDto3.setProductQuantity(5);
		prodDto3.setProduct(p2.getId());

		prods2 = new ArrayList<>(Arrays.asList(prodDto2, prodDto3));

		SalesOrderEditionDTO d2 = new SalesOrderEditionDTO();
		d2.setTotalPrice(p1.getPrice() * prodDto2.getProductQuantity() + p2.getPrice() * prodDto3.getProductQuantity());
		d2.setCustomer(c2.getId());
		d2.setOrderNumber(1234l);
		d2.setOrderProducts(prods2);
		s2 = (SalesOrderDTO) salesOrderService.create(d2);

		//Updating product due to sales order creation
		p1 = productService.get(p1.getId());
		p2 = productService.get(p2.getId());
	}
}

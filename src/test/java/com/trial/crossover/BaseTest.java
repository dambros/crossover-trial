package com.trial.crossover;

import com.google.gson.Gson;
import com.trial.crossover.config.WebConfig;
import com.trial.crossover.dto.CustomerDTO;
import com.trial.crossover.dto.ProductDTO;
import com.trial.crossover.service.CustomerService;
import com.trial.crossover.service.ProductService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by: dambros
 * Date: 12/2/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@Ignore
@ActiveProfiles("test")
public abstract class BaseTest {

	@Autowired
	public WebApplicationContext wac;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected CustomerService customerService;

	protected MockMvc mockMvc;
	protected Gson gson = new Gson();
	protected ProductDTO p1;
	protected ProductDTO p2;
	protected CustomerDTO c1;
	protected CustomerDTO c2;

	public void init() {
		Assert.assertNotNull(wac);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

		//adding some products
		p1 = new ProductDTO();
		p1.setDescription("description 1");
		p1.setPrice(10f);
		p1.setQuantity(100);
		p1 = productService.create(p1);

		p2 = new ProductDTO();
		p2.setDescription("description 2");
		p2.setPrice(100.50f);
		p2.setQuantity(100);
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
	}

	public void clean() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query deleteProducts = session.createQuery("delete from Product");
		deleteProducts.executeUpdate();

		Query deleteCustomers = session.createQuery("delete from Customer");
		deleteCustomers.executeUpdate();

		session.getTransaction().commit();
		session.close();
	}
}

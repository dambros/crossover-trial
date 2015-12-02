package com.trial.crossover;

import com.google.gson.Gson;
import com.trial.crossover.config.WebConfig;
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

	public MockMvc mockMvc;
	public Gson gson = new Gson();

	@Before
	public void init() {
		Assert.assertNotNull(wac);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	protected void save(Object... models) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Object model : models) {
			session.save(model);
		}
		session.getTransaction().commit();
		session.close();
	}
}

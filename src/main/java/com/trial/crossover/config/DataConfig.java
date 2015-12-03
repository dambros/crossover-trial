package com.trial.crossover.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by: dambros
 * Date: 12/1/2015
 */
@Configuration
@EnableTransactionManagement
public class DataConfig {

	@Resource
	private Environment env;

	//properties names
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES_EXTRACTOR = "hibernate.hbm2ddl.import_files_sql_extractor";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES = "hibernate.hbm2ddl.import_files";

	//application.properties values
	private static final String PROPERTY_NAME_SERVER_URL = "hibernate.server.url";
	private static final String PROPERTY_NAME_SERVER_USERNAME = "hibernate.server.username";
	private static final String PROPERTY_NAME_SERVER_PASSWORD = "hibernate.server.password";
	private static final String PROPERTY_NAME_SERVER_HBM2DDL_AUTO = "hibernate.server.hbm2ddl_auto";
	private static final String PROPERTY_NAME_SERVER_IMPORT_SCRIPT = "hibernate.server.import.script";


	@Profile("default")
	@Bean
	public DataSource mysqlDataSource() {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_SERVER_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_SERVER_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_SERVER_PASSWORD));
		dataSource.setInitialSize(2);

		return dataSource;
	}

	@Profile("test")
	@Bean
	public DataSource hsqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:testdb;shutdown=false");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	@Profile("default")
	@Bean(name = "hibernateProperties")
	public Properties mysqlHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_SERVER_HBM2DDL_AUTO));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES_EXTRACTOR, "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_IMPORT_FILES, env.getRequiredProperty(PROPERTY_NAME_SERVER_IMPORT_SCRIPT));
		return properties;
	}

	@Profile("test")
	@Bean(name = "hibernateProperties")
	public Properties hsqlHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.HSQLDialect");
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "create-drop");
		return properties;
	}

	@Bean
	public HibernateTransactionManager transactionManager(DataSource dataSource, Properties hibernateProperties) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory(dataSource, hibernateProperties).getObject());
		return transactionManager;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan("com.trial.crossover.model");
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		return sessionFactoryBean;
	}
}

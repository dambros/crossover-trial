package com.trial.crossover.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Created by: dambros
 * Date: 12/1/2015
 */
public class Initializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebConfig.class);
		servletContext.addListener(new ContextLoaderListener(ctx));
		servletContext.addFilter("httpPutFormFilter", HttpPutFormContentFilter.class);

		ctx.setServletContext(servletContext);

		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/api/*");
		servlet.setLoadOnStartup(1);
	}
}

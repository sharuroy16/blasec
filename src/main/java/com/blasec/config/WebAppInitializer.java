package com.blasec.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Java code to configure Spring MVC and Spring Data JPA. 
 * We use Java-based configuration as it's simpler than XML.
 * 
 * Configure Spring Dispatcher Servlet:
 * To use Spring MVC for our Java web application, we need to register the Spring Dispatcher Servlet upon application's startup 
 * by coding the following class.
 * 
 * The onStartup() method of this class will be automatically invoked by the servlet container when the application is being loaded. 
 * The Spring Dispatcher Servlet handles all the requests via the URL mapping "/" and it looks for configuration in the WebMvcConfig class.
 */
public class WebAppInitializer implements WebApplicationInitializer {
	
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebMvcConfig.class);
         
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
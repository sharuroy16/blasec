package com.blasec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * This class is annotated with the @Configuration annotation to tell Spring framework that this is a configuration class. 
 * The @ComponentScan annotation tells Spring to scan for configuration classes in the com.blasec package.
 * In this class, we simply create a view resolver bean that specifies the prefix and suffix for view files. 
 * So create the directory views under WebContent/WEB-INF directory to store JSP files.
 * You can add more Spring MVC configurations here.
 */

@Configuration
@EnableWebMvc 
@ComponentScan("com.blasec")
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setPrefix("/WEB-INF/views/");
	    viewResolver.setSuffix(".jsp");
	    return viewResolver;
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
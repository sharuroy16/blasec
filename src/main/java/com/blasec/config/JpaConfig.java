package com.blasec.config;

import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configure Spring Data JPA:
 * To enable Spring Data JPA, we need to create two beans: EntityManagerFactory and JpaTransactionManager.
 * 
 * Here, two important annotations are used:
 * @EnableJpaRepositories: this tells Spring Data JPA to look for repository classes in the specified package (com.blasec.repository) 
 * in order to inject relevant code at runtime.
 * @EnableTransactionManagement: this tells Spring Data JPA to generate code for transaction management at runtime.
 * 
 * In this class, the first method creates an instance of EntityManagerFactory to manage the persistence unit 'BlasecPersistance' 
 * (this name is specified in the persistence.xml file above).
 * And the last method creates an instance of JpaTransactionManagerfor the EntityManagerFactory created by the first method.
 * That's the minimum required configuration for using Spring Data JPA.
 */

@Configuration
@EnableJpaRepositories(basePackages = {"com.blasec.repository"})
@EnableTransactionManagement
public class JpaConfig {
	
    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("BlasecPersistance");
        return factoryBean;
    }
     
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }  
}
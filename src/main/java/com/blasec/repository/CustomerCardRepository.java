package com.blasec.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.blasec.entity.Customercarddetails;

/**
 * Code Repository Interface:
 * create the CustomerCardRepository interface that extends the JpaRepository interface defined by Spring Data JPA.
 * 
 * this is almost the code we need for the data access layer. 
 * As with Spring Data JPA, you don't have to write any DAO code. 
 * Just declare an interface that extends the JpaRepository interface, which defines CRUD methods like save(), findAll(), findById(), deleteById(), etc. 
 * At runtime, Spring Data JPA automatically generates the implementation code.
 * Note that we must specify the type of the model class and type of the primary key field when extending the JpaRepository interface: 
 * JpaRepository<Customercarddetails, Long>
 */

@Repository("customerCardRepository")
public interface CustomerCardRepository extends JpaRepository<Customercarddetails, Long> {
	
	@Query(value = "SELECT c.id, c.username, c.nameonthecard, c.cardnumber, c.cvv, c.expirydate, c.billingadrs FROM Customercarddetails c "
			+ "WHERE c.username = :globalUsername")
	public List<Object> fetchCardDetails(@Param("globalUsername") String globalUsername);
	
	@Query(value = "SELECT c FROM Customercarddetails c WHERE c.username LIKE '%' || :keyword || '%'")
	public List<Customercarddetails> search(@Param("keyword") String keyword);
}
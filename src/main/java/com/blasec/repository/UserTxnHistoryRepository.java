package com.blasec.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.blasec.entity.Usertxnhistory;

/**
 * Code Repository Interface:
 * create the UserTxnHistoryRepository interface that extends the JpaRepository interface defined by Spring Data JPA.
 * 
 * this is almost the code we need for the data access layer. 
 * As with Spring Data JPA, you don't have to write any DAO code. 
 * Just declare an interface that extends the JpaRepository interface, which defines CRUD methods like save(), findAll(), findById(), deleteById(), etc. 
 * At runtime, Spring Data JPA automatically generates the implementation code.
 * Note that we must specify the type of the model class and type of the primary key field when extending the JpaRepository interface: 
 * JpaRepository<Usertxnhistory, Long>
 */

@Repository("userTxnHistoryRepository")
public interface UserTxnHistoryRepository extends JpaRepository<Usertxnhistory, Long> {
	
	@Query(value = "SELECT c FROM Usertxnhistory c WHERE c.username = :username")
	public List<Usertxnhistory> retrieveUsersTxnHistory(@Param("username") String username);
	
	@Query(value = "SELECT c FROM Usertxnhistory c WHERE c.username LIKE '%' || :keyword || '%'")
	public List<Usertxnhistory> search(@Param("keyword") String keyword);
}
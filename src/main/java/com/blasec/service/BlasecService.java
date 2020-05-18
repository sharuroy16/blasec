package com.blasec.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;

@Service
public interface BlasecService {
	
	public Logindetails validateUser(String username, String userpassword);
	
	public List<Object> fetchCardDetails(String globalUsername);
	
	public boolean validatePaymentDetails(List<Object> result, Customercarddetails customercarddetails);
	
	public List<Usertxnhistory> retrieveUsersTxnHistory(String globalUsername);
	
	public String compareWithUserHistory(List<Usertxnhistory> usertxnhistoryList, Customercarddetails customercarddetails);
	
	public String getSecurityAnswer(String username);
	
	public void addUserTxnDetails(String username, long txnAmount, String remarks);
	
	public void addUserCardDetails(String username, Customercarddetails customercarddetails);
	
	public String validateIP();
	
	public String validateNegativeDatabase(Customercarddetails customercarddetails);
	
	public void updateOpenStatus(String username, char val);
	
	public void blackListAccountDetails(Customercarddetails customercarddetails);
}
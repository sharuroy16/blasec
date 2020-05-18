package com.blasec.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.blasec.entity.Blacklist;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;

@Service
public interface AdminService {
	
	public void save(Logindetails logindetails);
	public void saveCCardDetails(Customercarddetails customercarddetails);
	
	public List<Logindetails> listAllLoginDetails();
	public List<Customercarddetails> listAllCustomerCardDetails();
	public List<Usertxnhistory> listAllUserTxnHistory();
	
	public void delete(Long id);
	public void deleteCCardDetails(Long id);
	public void deleteUserTxnHistory(Long id);
	
	public Logindetails get(Long id);
	public Customercarddetails getCCardDetails(Long id);
	
	public List<Logindetails> search(String keyword);
	public List<Customercarddetails> searchCCardDetails(String keyword);
	public List<Usertxnhistory> searchUserTxnHistory(String keyword);
	
	public Blacklist getBlacklistDetails();
	public void saveBlacklistDetails(Blacklist blacklist);
	
	public Logindetails encryptLoginDetails(Logindetails logindetails);
	public List<Logindetails> decryptLoginDetails(List<Logindetails> logindetails);
}
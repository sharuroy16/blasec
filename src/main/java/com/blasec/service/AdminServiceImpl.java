package com.blasec.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.blasec.entity.Blacklist;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;
import com.blasec.interfaces.AES;
import com.blasec.interfaces.BlasecConstants;
import com.blasec.repository.CustomerCardRepository;
import com.blasec.repository.LoginRepository;
import com.blasec.repository.UserTxnHistoryRepository;

/**
 * Note that this class is annotated with the @Transactional annotation so all of its methods will be 
 * intercepted by Spring Data JPA for transaction management. 
 * And an instance of LoginRepository, CustomerCardRepository and UserTxnHistoryRepository interface will be injected into this class.
 * This is like magic, as we don't write any DAO code but Spring Data JPA will generate an implementation automatically at runtime.
 * And as you can see, all the methods in this class are for CRUD operations. 
 * It simply delegates all the call to a LoginRepository, CustomerCardRepository or UserTxnHistoryRepository object. 
 * This class seems to be redundant, but it is needed to decouple the business/service layer from the repository/DAO layer.
 */

@Component("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired 
	private LoginRepository loginRepository;
	
	@Autowired 
	private CustomerCardRepository customerCardRepository;
	
	@Autowired 
	private UserTxnHistoryRepository userTxnHistoryRepository;
	
	
	
	public void save(Logindetails logindetails) {
		loginRepository.save(logindetails);
	}
	public void saveCCardDetails(Customercarddetails customercarddetails) {
		customerCardRepository.save(customercarddetails);
	}
	
	
	
	public List<Logindetails> listAllLoginDetails() {
		return (List<Logindetails>) loginRepository.findAll();
	}
	public List<Customercarddetails> listAllCustomerCardDetails(){
		return (List<Customercarddetails>) customerCardRepository.findAll();
	}
	public List<Usertxnhistory> listAllUserTxnHistory(){
		return (List<Usertxnhistory>) userTxnHistoryRepository.findAll();
	}
	
	
	
	public void delete(Long id) {
		loginRepository.deleteById(id);
	}
	public void deleteCCardDetails(Long id) {
		customerCardRepository.deleteById(id);
	}
	public void deleteUserTxnHistory(Long id) {
		userTxnHistoryRepository.deleteById(id);
	}
	
	
	
	public Logindetails get(Long id) {
		return loginRepository.findById(id).get();
	}
	
	public Customercarddetails getCCardDetails(Long id) {
		return customerCardRepository.findById(id).get();
	}
	
	
	
	public List<Logindetails> search(String keyword) {
		return loginRepository.search(keyword);
	}
	public List<Customercarddetails> searchCCardDetails(String keyword){
		return customerCardRepository.search(keyword);
	}
	public List<Usertxnhistory> searchUserTxnHistory(String keyword){
		return userTxnHistoryRepository.search(keyword);
	}
	
	public Blacklist getBlacklistDetails() {
		Blacklist blacklist = new Blacklist();
		
		try {
			 String fileLoc = BlasecConstants.NEG_IP_LOC;
		     Path path = Paths.get(fileLoc);
		     List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		     if(!allLines.isEmpty()) {
		    	 StringBuilder str = new StringBuilder();
		    	 for(String val : allLines) {
			    	 str.append(val).append(",");
			     }
		    	 String ip = str.toString();
		    	 ip = ip.substring(0, ip.length() - 1);
		    	 blacklist.setIp(replaceChar(ip));
		     }else
		    	 blacklist.setIp(null);
		     
		     fileLoc = BlasecConstants.NEG_CADDR_LOC;
		     path = Paths.get(fileLoc);
		     allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		     if(!allLines.isEmpty()) {
		    	 StringBuilder str = new StringBuilder();
		    	 for(String val : allLines) {
			    	 str.append(val).append(",");
			     }
		    	 String addr = str.toString();
		    	 addr = addr.substring(0, addr.length() - 1);
		    	 blacklist.setCardholderaddress(replaceChar(addr));
		     }else
		    	 blacklist.setCardholderaddress(null);
		     
		     fileLoc = BlasecConstants.NEG_CNAME_LOC;
		     path = Paths.get(fileLoc);
		     allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		     if(!allLines.isEmpty()) {
		    	 StringBuilder str = new StringBuilder();
		    	 for(String val : allLines) {
			    	 str.append(val).append(",");
			     }
		    	 String name = str.toString();
		    	 name = name.substring(0, name.length() - 1);
		    	 blacklist.setCardholdername(replaceChar(name));
		     }else
		    	 blacklist.setCardholdername(null);
		     
		     fileLoc = BlasecConstants.NEG_CNUM_LOC;
		     path = Paths.get(fileLoc);
		     allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		     if(!allLines.isEmpty()) {
		    	 StringBuilder str = new StringBuilder();
		    	 for(String val : allLines) {
			    	 str.append(val).append(",");
			     }
		    	 String num = str.toString();
		    	 num = num.substring(0, num.length() - 1);
		    	 blacklist.setCardnumber(replaceChar(num));
		     }else
		    	 blacklist.setCardnumber(null);
		     
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}  
		return blacklist;
	}
	
	public void saveBlacklistDetails(Blacklist blacklist) {
		
		String[] ipList = null;
		String[] nameList = null;
		String[] addrList = null;
		String[] numList = null;
		
		String ip = replaceChar(blacklist.getIp());
		if(ip != null && !ip.isEmpty())
			ipList = ip.split(","); 
		
		String name = replaceChar(blacklist.getCardholdername());
		if(name != null && !name.isEmpty())
			nameList = name.split(","); 
		
		String addr = replaceChar(blacklist.getCardholderaddress());
		if(addr != null && !addr.isEmpty())
			addrList = addr.split(","); 
		
		String num = replaceChar(blacklist.getCardnumber());
		if(num != null && !num.isEmpty())
			numList = num.split(","); 
		
		addBlackList(ipList, nameList, addrList, numList);
	}
	
	private String replaceChar(String val) {
		String result = val.replaceAll("(\\r|\\n|\\t)", "");
		if(!result.isEmpty() && result.substring(0,1).equals("[") && result.substring(result.length() - 1).equals("]")) {
			result = result.substring(1, result.length() - 1);
		}
		return result;
	}
	
	private void addBlackList(String[] ipList, String[] nameList, String[] addrList, String[] numList) {
			
		String filePath = null;
		Path path = null;
		boolean fileExists = false;
		
		filePath = BlasecConstants.NEG_IP_LOC;
		path = Paths.get(filePath);
		fileExists = Files.exists(path);
				
		if(fileExists) {
		          try {
		        	  if(ipList != null) {
		        		  Files.write(path, ("").getBytes());
		        		  for(String ip : ipList) {
					            Files.write(path, (ip + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
					      }
		        	  }else {
		        		  Files.write(path, ("").getBytes());
		        	  }
		          } catch (IOException ioExceptionObj) {
		              System.out.println("Problem occured while writing to the NegativeDatabase_IP file: " + ioExceptionObj.getMessage());
		          }
		} else {
		          System.out.println("NegativeDatabase_IP.txt not present. Please check!");
		}
		
		filePath = BlasecConstants.NEG_CNUM_LOC;
		path = Paths.get(filePath);
		fileExists = Files.exists(path);
				
		if(fileExists) {
		          try {
		        	  if(numList != null) {
		        		  Files.write(path, ("").getBytes());
		        		  for(String num : numList) {
				            	Files.write(path, (num + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
				            }
		        	  }else {
		        		  Files.write(path, ("").getBytes());
		        	  }
		          } catch (IOException ioExceptionObj) {
		               System.out.println("Problem occured while writing to the NegativeDatabase_CardNumber file: " + ioExceptionObj.getMessage());
		          }
		} else {
		          System.out.println("NegativeDatabase_CardNumber.txt not present. Please check!");
		}
			
		filePath = BlasecConstants.NEG_CNAME_LOC;
		path = Paths.get(filePath);
		fileExists = Files.exists(path);
				
		if(fileExists) {
		          try {
		        	  if(nameList != null) {
		        		  Files.write(path, ("").getBytes());
		        		  for(String name : nameList) {
			        		  Files.write(path, (name + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
			        	  }
		        	  }else {
		        		  Files.write(path, ("").getBytes());
		        	  }
		          } catch (IOException ioExceptionObj) {
		              System.out.println("Problem occured while writing to the NegativeDatabase_CardHolderName file: " + ioExceptionObj.getMessage());
		          }
		} else {
		          System.out.println("NegativeDatabase_CardHolderName.txt not present. Please check!");
		}

		filePath = BlasecConstants.NEG_CADDR_LOC;
		path = Paths.get(filePath);
		fileExists = Files.exists(path);
				
		if(fileExists) {
		          try {
		        	  if(addrList != null) {
		        		  Files.write(path, ("").getBytes());
		        		  for(String addr : addrList) {
			        		  Files.write(path, (addr + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
			        	  }
		        	  }else {
		        		  Files.write(path, ("").getBytes());
		        	  }
		          } catch (IOException ioExceptionObj) {
		              System.out.println("Problem occured while writing to the NegativeDatabase_CardHolderAddress file: " + ioExceptionObj.getMessage());
		          }
		} else {
		          System.out.println("NegativeDatabase_CardHolderAddress.txt not present. Please check!");
		}
	}
	
	public Logindetails encryptLoginDetails(Logindetails logindetails) {
		
		logindetails.setUsername(AES.encrypt(logindetails.getUsername(), BlasecConstants.CIPHER_KEY));
		logindetails.setUserpassword(AES.encrypt(logindetails.getUserpassword(), BlasecConstants.CIPHER_KEY));
		logindetails.setSecurityquestion(AES.encrypt(logindetails.getSecurityquestion(), BlasecConstants.CIPHER_KEY));
		logindetails.setSecurityanswer(AES.encrypt(logindetails.getSecurityanswer(), BlasecConstants.CIPHER_KEY));
		
		return logindetails;
	}
	
	public List<Logindetails> decryptLoginDetails(List<Logindetails> logindetails){
		
		if(logindetails != null && logindetails.size() > 0) {
			for(Logindetails login : logindetails) {
				login.setUsername(AES.decrypt(login.getUsername(), BlasecConstants.CIPHER_KEY));
				login.setUserpassword(AES.decrypt(login.getUserpassword(), BlasecConstants.CIPHER_KEY));
				login.setSecurityquestion(AES.decrypt(login.getSecurityquestion(), BlasecConstants.CIPHER_KEY));
				login.setSecurityanswer(AES.decrypt(login.getSecurityanswer(), BlasecConstants.CIPHER_KEY));
			}
		}
		return logindetails;
	}
}
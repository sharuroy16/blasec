package com.blasec.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;
import com.blasec.interfaces.BlasecConstants;
import com.blasec.repository.CustomerCardRepository;
import com.blasec.repository.LoginRepository;
import com.blasec.repository.UserTxnHistoryRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

/**
 * Note that this class is annotated with the @Transactional annotation so all of its methods will be 
 * intercepted by Spring Data JPA for transaction management. 
 * And an instance of LoginRepository, CustomerCardRepository and UserTxnHistoryRepository interface will be injected into this class.
 * It simply delegates all the call to a LoginRepository, CustomerCardRepository or UserTxnHistoryRepository object. 
 * This class seems to be redundant, but it is needed to decouple the business/service layer from the repository/DAO layer.
 */

@Component("blasecService")
@Transactional
public class BlasecServiceImpl implements BlasecService {
	
	@Autowired 
	private LoginRepository loginRepository;
	
	@Autowired
	private CustomerCardRepository customerCardRepository;
	
	@Autowired
	private UserTxnHistoryRepository userTxnHistoryRepository;
	
	public Logindetails validateUser(String username, String userpassword) {
		return loginRepository.validateUser(username, userpassword);
	}
	
	public List<Object> fetchCardDetails(String globalUsername) {
		return customerCardRepository.fetchCardDetails(globalUsername);
	}
	/**
	 * AVS check is done here
	 */
	public boolean validatePaymentDetails(List<Object> result, Customercarddetails customercarddetails) {
		
		boolean isValidCardDetails = false;
		for(Object customercarddetail : result) {
			
			Object[] temp = ((Object[])customercarddetail);		        
		        
		    if(temp[2].toString().equals(customercarddetails.getNameonthecard())
					&& Long.parseLong(temp[3].toString()) == customercarddetails.getCardnumber()
					&& Integer.parseInt(temp[4].toString()) == customercarddetails.getCvv()
					&& temp[5].toString().equals(customercarddetails.getExpirydate())
					&& temp[6].toString().equals(customercarddetails.getBillingadrs())) { //AVS
						
					isValidCardDetails = true;
					break;
				}	
		}
		return isValidCardDetails;
	}
	
	public List<Usertxnhistory> retrieveUsersTxnHistory(String globalUsername) {
		return userTxnHistoryRepository.retrieveUsersTxnHistory(globalUsername);
	}
	
	public String compareWithUserHistory(List<Usertxnhistory> usertxnhistoryList, Customercarddetails customercarddetails) {
		
		String result = null;

        /**
    	 * 2: Analyzing credit card owner’s previous transaction rates.
    	 */
        if(validatePreviousTxnRates(usertxnhistoryList, customercarddetails)) {
        	/**
        	 * 3: Geographic location is obtained using the IP address.
        	 */
        	result = validateGeographicLocation(usertxnhistoryList);
            return result;
        }else {
        	result = BlasecConstants.UNUSUAL_PATTERN_TXN_RATE;
            return result;
        }
    }
	
	/**
	 * Negative Database Security
	 * 
	 * We have a list of blacklisted Card Number, Card Holder Name and Card Holder Address.
	 * Primarily, the user provided card details are cross-checked with this to decline the transaction.
	 * These files are updated by the ADMIN and they are in encrypted formats.
	 */
	public String validateNegativeDatabase(Customercarddetails customercarddetails){
		
		StringBuilder stringBuilder = new StringBuilder();
		String result = null;
		
		try {

	        /**
	         * User's Card Number is checked with the existing blacklisted Card Numbers
	         */
	        String fileLoc = BlasecConstants.NEG_CNUM_LOC;
	        Path path = Paths.get(fileLoc);
	        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);   
	        
	        if(allLines.contains(String.valueOf(customercarddetails.getCardnumber()))) 
	        	stringBuilder.append(BlasecConstants.CARD_NUMBER_BLACKLISTED).append(",");
       
	        /**
	         * User's Card Holder Name is checked with the existing blacklisted Card Holder Names
	         */
	        fileLoc = BlasecConstants.NEG_CNAME_LOC;
	        path = Paths.get(fileLoc);
	        allLines = Files.readAllLines(path, StandardCharsets.UTF_8); 
	        
	        if(allLines.contains(customercarddetails.getNameonthecard())) 
	        	stringBuilder.append(BlasecConstants.CARD_HOLDER_NAME_BLACKLISTED).append(",");
	        
	        /**
	         * User's Card Holder Address is checked with the existing blacklisted Card Holder Addresses
	         */
	        fileLoc = BlasecConstants.NEG_CADDR_LOC;
	        path = Paths.get(fileLoc);
	        allLines = Files.readAllLines(path, StandardCharsets.UTF_8); 
	        
	        if(allLines.contains(customercarddetails.getBillingadrs())) 
	        	stringBuilder.append(BlasecConstants.CARD_HOLDER_ADDRESS_BLACKLISTED).append(",");
	    	
	        if(stringBuilder.toString() != null && !stringBuilder.toString().isEmpty()) {
	        	stringBuilder.append(BlasecConstants.BLACKLIST);
	        	result = stringBuilder.toString();
	        }
	        else
				result = BlasecConstants.SUCCESS;
	        
		}catch (IOException ioException) {
        	System.out.println(ioException.getMessage());
		}
		return result;
	}
	
	/**
	 * Analyzing credit card owner’s previous transaction rates.
	 * There is always a trend in the user's transaction rates. We will check whether the new transaction amount lies
	 * between the rates. If not, there is an unusual pattern detected from the normal expense behavior.
	 */
	private boolean validatePreviousTxnRates(List<Usertxnhistory> usertxnhistoryList, Customercarddetails customercarddetails) {
		
		boolean isLegit = true;
		List<Long> txnAmounts = new ArrayList<Long>();
		
		for(Usertxnhistory usertxnhistory : usertxnhistoryList) {
			txnAmounts.add(usertxnhistory.getTxnamount());
		}
		
		Collections.sort(txnAmounts);
		long minTxnAmount = txnAmounts.get(0);
		long maxTxnAmount = txnAmounts.get(txnAmounts.size() - 1);
		
		if(!(customercarddetails.getTxnamount() <= maxTxnAmount && customercarddetails.getTxnamount() >= minTxnAmount))
			return false;
		
		return isLegit;
	}
	
	/**
	 * Geographic location is obtained using the IP address and verified with the previous history.
	 * Continent, Country, Latitude, Longitude, TimeZone are checked.
	 */
	private String validateGeographicLocation(List<Usertxnhistory> usertxnhistoryList) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String result = null;
		List<String> continents = new ArrayList<String>();
		List<String> countries = new ArrayList<String>();		
		List<Double> latitudes = new ArrayList<Double>();
		List<Double> longitudes = new ArrayList<Double>();
		List<String> timeZones = new ArrayList<String>();
		
		String dbLocation = BlasecConstants.GEO_LITE_LOC;
        
		try {
			File database = new File(dbLocation);
			DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
				         
			//Use ip
			String ip = InetAddress.getLocalHost().getHostAddress().trim();
			String mockIP = "202.71.129.225";
				
			InetAddress ipAddress = InetAddress.getByName(mockIP);
			CityResponse response = dbReader.city(ipAddress);
				
			String continent = response.getContinent().getNames().get("en");
			String country = response.getCountry().getNames().get("en");
			double latitude = response.getLocation().getLatitude();
			double longitude = response.getLocation().getLongitude();
			String timeZone = response.getLocation().getTimeZone();

			for(Usertxnhistory usertxnhistory: usertxnhistoryList) {
					
				if(!continents.contains(usertxnhistory.getTxncontinent()))
					continents.add(usertxnhistory.getTxncontinent());
					
				if(!countries.contains(usertxnhistory.getTxncountry()))
					countries.add(usertxnhistory.getTxncountry());
					
				if(!latitudes.contains(usertxnhistory.getTxnlatitude()))
					latitudes.add(usertxnhistory.getTxnlatitude());
					
				if(!longitudes.contains(usertxnhistory.getTxnlongitude()))
					longitudes.add(usertxnhistory.getTxnlongitude());
					
				if(!timeZones.contains(usertxnhistory.getTxntimezone()))
					timeZones.add(usertxnhistory.getTxntimezone());
			}
				
			if(!continents.contains(continent)) 
				stringBuilder.append(BlasecConstants.NEW_CONTINENT).append(",");
			if(!countries.contains(country))
				stringBuilder.append(BlasecConstants.NEW_COUNTRY).append(",");
			if(!latitudes.contains(latitude))
				stringBuilder.append(BlasecConstants.NEW_LATITUDE).append(",");
			if(!longitudes.contains(longitude))
				stringBuilder.append(BlasecConstants.NEW_LONGITUDE).append(",");
			if(!timeZones.contains(timeZone))
				stringBuilder.append(BlasecConstants.NEW_TIMEZONE).append(",");
			
			if(stringBuilder.toString() != null && !stringBuilder.toString().isEmpty()) {
	        	result = stringBuilder.toString();
	        	result = result.substring(0, result.length() - 1);
			}
			else
				result = BlasecConstants.SUCCESS;
			
		} catch (IOException ioException) {
        	System.out.println(ioException.getMessage());
		} catch (GeoIp2Exception geoIp2Exception) {
			System.out.println(geoIp2Exception.getMessage());
		}
		return result;
	}
	
	public String getSecurityAnswer(String username) {
		return loginRepository.getSecurityAnswer(username);
	}
	
	public void addUserTxnDetails(String username, long txnAmount, String remarks) {
		
		String dbLocation = BlasecConstants.GEO_LITE_LOC;
        
		try {
			String ip = InetAddress.getLocalHost().getHostAddress().trim();
			String mockIP = "202.71.129.225";
			
			File database = new File(dbLocation);
			DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
				
			InetAddress ipAddress = InetAddress.getByName(mockIP);
			CityResponse response = dbReader.city(ipAddress);
				
			String continent = response.getContinent().getNames().get("en");
			String country = response.getCountry().getNames().get("en");
			double latitude = response.getLocation().getLatitude();
			double longitude = response.getLocation().getLongitude();
			String timeZone = response.getLocation().getTimeZone();
			
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String txnDate = month + "/" + year;
			
			Usertxnhistory usertxnhistory = new Usertxnhistory();
			usertxnhistory.setUsername(username);
			usertxnhistory.setTxnamount(txnAmount);
			usertxnhistory.setTxncontinent(continent);
			usertxnhistory.setTxncountry(country);
			usertxnhistory.setTxnlatitude(latitude);
			usertxnhistory.setTxnlongitude(longitude);
			usertxnhistory.setTxntimezone(timeZone);
			usertxnhistory.setTxndate(txnDate);
			usertxnhistory.setRemarks(remarks);
			
			userTxnHistoryRepository.save(usertxnhistory);
			
		}  catch (GeoIp2Exception geoIp2Exception) {
			System.out.println(geoIp2Exception.getMessage());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
	}
	
	public void addUserCardDetails(String username, Customercarddetails customercarddetails) {
		customercarddetails.setUsername(username);
		customerCardRepository.save(customercarddetails);
	}
	
	/**
	 * 
	 */
	public String validateIP(){
		
		StringBuilder stringBuilder = new StringBuilder();
		String result = null;
		
		try {
			
			String ip = InetAddress.getLocalHost().getHostAddress().trim();
	        
			/**
			 * User IP is checked with the existing blacklisted IPs
			 */
	        String fileLoc = BlasecConstants.NEG_IP_LOC;
	        Path path = Paths.get(fileLoc);
	        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);       
	        
	        if(allLines.contains(ip)) 
	        	stringBuilder.append(BlasecConstants.IP_BLACKLISTED).append(",");       
	    	
	        if(stringBuilder.toString() != null && !stringBuilder.toString().isEmpty()) {
	        	stringBuilder.append(BlasecConstants.BLACKLIST);
	        	result = stringBuilder.toString();
	        }
	        else
				result = BlasecConstants.SUCCESS;
	        
		} catch (UnknownHostException unknownHostException) {
        	System.out.println(unknownHostException.getMessage());
        } catch (IOException ioException) {
        	System.out.println(ioException.getMessage());
		}
		return result;
	}
	
	public void updateOpenStatus(String username, char val) {
		loginRepository.updateOpenStatus(username, val);
	}
	
	public void blackListAccountDetails(Customercarddetails customercarddetails) {
		
		try {
			
			String ip = InetAddress.getLocalHost().getHostAddress().trim();
			
			String filePath = BlasecConstants.NEG_IP_LOC;
			Path path = Paths.get(filePath);
			boolean fileExists = Files.exists(path);
			
			if(fileExists) {
	            try {
	                Files.write(path, (ip + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
	            } catch (IOException ioExceptionObj) {
	                System.out.println("Problem Occured while writing to the NegativeDatabase_IP file: " + ioExceptionObj.getMessage());
	            }
	        } else {
	            System.out.println("NegativeDatabase_IP.txt not present. Please check!");
	        }
			
			filePath = null;
			path = null;
			
			filePath = BlasecConstants.NEG_CNUM_LOC;
			path = Paths.get(filePath);
			fileExists = Files.exists(path);
			
			if(fileExists) {
	            try {
	                Files.write(path, (String.valueOf(customercarddetails.getCardnumber()) + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
	            } catch (IOException ioExceptionObj) {
	                System.out.println("Problem Occured while writing to the NegativeDatabase_CardNumber file: " + ioExceptionObj.getMessage());
	            }
	        } else {
	            System.out.println("NegativeDatabase_CardNumber.txt not present. Please check!");
	        }
			
			filePath = null;
			path = null;
			
			filePath = BlasecConstants.NEG_CNAME_LOC;
			path = Paths.get(filePath);
			fileExists = Files.exists(path);
			
			if(fileExists) {
	            try {
	                Files.write(path, (customercarddetails.getNameonthecard() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
	            } catch (IOException ioExceptionObj) {
	                System.out.println("Problem Occured while writing to the NegativeDatabase_CardHolderName file: " + ioExceptionObj.getMessage());
	            }
	        } else {
	            System.out.println("NegativeDatabase_CardHolderName.txt not present. Please check!");
	        }
			
			filePath = null;
			path = null;
			
			filePath = BlasecConstants.NEG_CADDR_LOC;
			path = Paths.get(filePath);
			fileExists = Files.exists(path);
			
			if(fileExists) {
	            try {
	                Files.write(path, (customercarddetails.getBillingadrs() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
	            } catch (IOException ioExceptionObj) {
	                System.out.println("Problem Occured while writing to the NegativeDatabase_CardHolderAddress file: " + ioExceptionObj.getMessage());
	            }
	        } else {
	            System.out.println("NegativeDatabase_CardHolderAddress.txt not present. Please check!");
	        }
			
			filePath = null;
			path = null;
			
		} catch (IOException ioException) {
	      	System.out.println(ioException.getMessage());	
	    }
	}
}
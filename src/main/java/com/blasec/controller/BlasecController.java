package com.blasec.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.blasec.service.BlasecService;
import com.blasec.service.AdminService;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;
import com.blasec.interfaces.AES;
import com.blasec.interfaces.BlasecConstants;

/**
 * The BlasecController class is to handle all requests from the clients. 
 * This is a typical Spring MVC controller class, which is annotated with the @Controller annotation. 
 * An instance of BlasecService and AdminService is injected into this class using the @Autowired annotation.
 */

@Controller
public class BlasecController {

	private String globalUsername = null;

	private boolean isReLogin = false;
	
	private int count = 3;
	
	private boolean shouldAddTxnDetails = false;
	
	private boolean shouldAddCardDetails = false;
	
	private Customercarddetails globalCustomercarddetails = null;
	
	private String remarks = null;

	@Autowired
	private BlasecService blasecService;
	
	@Autowired
	private AdminService adminService;

	/**
	 * Initial load - Login page
	 */
	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}

	/**
	 * Validate login credentials and if found, redirects to payment page, else,
	 * login page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView validateUser(@RequestParam String username, @RequestParam String userpassword) {

		ModelAndView modelAndView = null;
		
		String encryptUsername = AES.encrypt(username, BlasecConstants.CIPHER_KEY);
		String encryptUserpwd = AES.encrypt(userpassword, BlasecConstants.CIPHER_KEY);

		Logindetails result = blasecService.validateUser(encryptUsername, encryptUserpwd);
		List<Logindetails> temp = new ArrayList<Logindetails>();
		temp.add(result);
		List<Logindetails> decryptedtemp = adminService.decryptLoginDetails(temp);

		if (result != null) {
			
			if(BlasecConstants.ADMIN.contentEquals(encryptUsername) && BlasecConstants.ADMIN_PWD.contentEquals(encryptUserpwd)) {
				List<Logindetails> loginDetails = adminService.listAllLoginDetails(); 
				List<Logindetails> decryptedLoginDetails = adminService.decryptLoginDetails(loginDetails);
				
				List<Customercarddetails> customercarddetails = adminService.listAllCustomerCardDetails();
				List<Usertxnhistory> usertxnhistory = adminService.listAllUserTxnHistory();
				ModelAndView mav = new ModelAndView("admin_index");
				mav.addObject("loginDetails", decryptedLoginDetails); 
				mav.addObject("customercarddetails", customercarddetails); 
				mav.addObject("usertxnhistory", usertxnhistory); 
				return mav; 
			
			}
			
			if(BlasecConstants.OPEN_STATUS_N.charAt(0) == result.getOpenstatus()) {
				String message = "<h3 style=\"color: #cc0000;\">*The account is temperorily blocked for security violations. Please contact the Admin team for furthur proceedings.</h3>";
				modelAndView = new ModelAndView("blacklisted_message");
				modelAndView.addObject("message", message);
				return modelAndView;
			}
			
			String tempRemark = blasecService.validateIP();
			if (BlasecConstants.SUCCESS.equals(tempRemark)) {
				if(isReLogin) { 
					isReLogin = false;
					modelAndView = new ModelAndView("security_question");
					modelAndView.addObject("securityquestion", decryptedtemp.get(0).getSecurityquestion());
					modelAndView.addObject("count", count);
					return modelAndView;
				}
				 
				globalUsername = username;
				modelAndView = new ModelAndView("payment");
				Customercarddetails customercarddetails = new Customercarddetails();
				modelAndView.addObject("customercarddetails", customercarddetails);
			}else {
				String message = "<h3 style=\"color: #cc0000;\">*The information provided and obtained are marked under blacklisted category. Please contact the Admin team for furthur proceedings.</h3>";
				modelAndView = new ModelAndView("blacklisted_message");
				modelAndView.addObject("message", message);
				return modelAndView;
			}
			
		} else {
			String message = "<small style=\"color: #cc0000;\">*Invalid user credentials.</small>";
			modelAndView = new ModelAndView("index");
			modelAndView.addObject("message", message);
		}
		return modelAndView;
	}

	/**
	 * Validate payment details
	 */
	@RequestMapping(value = "/validatePayment", method = RequestMethod.POST)
	public ModelAndView validatePayment(@ModelAttribute("customercarddetails") Customercarddetails customercarddetails) {

		ModelAndView modelAndView = null;
		globalCustomercarddetails = customercarddetails;
		
		remarks = blasecService.validateNegativeDatabase(customercarddetails);
		
		if(BlasecConstants.SUCCESS.equals(remarks)) {
			
			customercarddetails.setExpirydate(customercarddetails.getMonth() + "/" + customercarddetails.getYear());

			List<Object> result = blasecService.fetchCardDetails(globalUsername);

			if (result != null && result.size() > 0) {
				boolean isValidCardDetails = blasecService.validatePaymentDetails(result, customercarddetails);

				if (isValidCardDetails) {

					List<Usertxnhistory> usertxnhistoryList = blasecService.retrieveUsersTxnHistory(globalUsername);

					if (usertxnhistoryList != null && !usertxnhistoryList.isEmpty()) {

						remarks = blasecService.compareWithUserHistory(usertxnhistoryList, customercarddetails);

						if (BlasecConstants.SUCCESS.equals(remarks)) {
							/**
							 * add transaction history
							 */
							blasecService.addUserTxnDetails(globalUsername, customercarddetails.getTxnamount(), remarks);
							modelAndView = new ModelAndView("payment_successful");
							return modelAndView;
						} else {
							/**
							 * Previous transaction history present, but now prompted with new values.
							 * re-login, security question and add transaction details
							 */
							isReLogin = true;
							shouldAddCardDetails = false;
							shouldAddTxnDetails = true;
						}
					} else {
						/**
						 * first time user, no transaction history present.
						 * re-login, security question and add transaction details
						 */
						remarks = BlasecConstants.FIRST_TRANSACTION;
						isReLogin = true;
						shouldAddCardDetails = false;
						shouldAddTxnDetails = true;
					}
				} else {
					/**
					 * New Card details but previous card details are present.
					 * re-login, security question, add card details and add transaction details
					 */
					remarks = BlasecConstants.NEW_CARD_AND_FIRST_TXN;
					isReLogin = true;
					shouldAddCardDetails = true;
					shouldAddTxnDetails = true;
				}
			} else {
				/**
				 * New Card details, no previous card details present.
				 * re-login, security question, add card details and add transaction details
				 */
				remarks = BlasecConstants.NEW_CARD_AND_FIRST_TXN;
				isReLogin = true;
				shouldAddCardDetails = true;
				shouldAddTxnDetails = true;
			}
			
			if((isReLogin && shouldAddCardDetails && shouldAddTxnDetails) || (isReLogin && shouldAddTxnDetails)) {
				String message = "<small style=\"color: #cc0000;\">*Re-login again for security reasons.</small>";
				modelAndView = new ModelAndView("index");
				modelAndView.addObject("message", message);
			}
			
		} else {
			int index = remarks.lastIndexOf(",");
			if(index >= 0) {
				String value = remarks.substring(index + 1);
				if(BlasecConstants.BLACKLIST.equals(value)) {
					String message = "<h3 style=\"color: #cc0000;\">*The information provided and obtained are marked under blacklisted category. Please contact the Admin team for furthur proceedings.</h3>";
					modelAndView = new ModelAndView("blacklisted_message");
					modelAndView.addObject("message", message);
					return modelAndView;
				}
			}
		}

		return modelAndView;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/securityvalidation", method = RequestMethod.POST)
	public ModelAndView validateSecurityAnswer(@RequestParam String securityquestion, @RequestParam String securityanswer) {

		ModelAndView modelAndView = null;
		
		String secAnswer = blasecService.getSecurityAnswer(AES.encrypt(globalUsername, BlasecConstants.CIPHER_KEY));
		String decryptedSecAnswer = AES.decrypt(secAnswer, BlasecConstants.CIPHER_KEY);
		
		if(decryptedSecAnswer.equals(securityanswer)) {
			/**
			 * 1: Perform payment operation
			 */
			/**
			 * 2: add card details
			 */
			if(shouldAddCardDetails) {
				blasecService.addUserCardDetails(globalUsername, globalCustomercarddetails);
				shouldAddCardDetails = false;
			}
			/**
			 * 2: add transaction details
			 */
			if(shouldAddTxnDetails) {
				blasecService.addUserTxnDetails(globalUsername, globalCustomercarddetails.getTxnamount(), remarks);
				shouldAddTxnDetails = false;
			}
			
			modelAndView = new ModelAndView("payment_successful");
			return modelAndView;
		}else {
			if(count > 1) {
				--count;
				modelAndView = new ModelAndView("security_question");
				modelAndView.addObject("securityquestion", securityquestion);
				modelAndView.addObject("count", count);
				return modelAndView;
			}else {
				/**
				 * blacklist file
				 */
				blasecService.updateOpenStatus(AES.encrypt(globalUsername, BlasecConstants.CIPHER_KEY), 'N');
				blasecService.blackListAccountDetails(globalCustomercarddetails);
				String message = "<h3 style=\"color: #cc0000;\">*The account is temperorily blocked for security violations. Please contact the Admin team for furthur proceedings.</h3>";
				modelAndView = new ModelAndView("blacklisted_message");
				modelAndView.addObject("message", message);
				return modelAndView;
			}
		}
	}
}
package com.blasec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.blasec.service.AdminService;
import com.blasec.entity.Blacklist;
import com.blasec.entity.Customercarddetails;
import com.blasec.entity.Logindetails;
import com.blasec.entity.Usertxnhistory;
import com.blasec.interfaces.AES;
import com.blasec.interfaces.BlasecConstants;

/**
 * The AdminController class is to handle all admin actions. 
 * This is a typical Spring MVC controller class, which is annotated with the @Controller annotation. 
 * An instance of AdminService is injected into this class using the @Autowired annotation.
 */

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin_index") 
	public ModelAndView home() {
		List<Logindetails> loginDetails = adminService.listAllLoginDetails(); 
		List<Logindetails> decryptedLoginDetails = adminService.decryptLoginDetails(loginDetails);
		
		List<Customercarddetails> customercarddetails = adminService.listAllCustomerCardDetails();
		List<Usertxnhistory> usertxnhistory = adminService.listAllUserTxnHistory();
		ModelAndView modelAndView = new ModelAndView("admin_index");
		modelAndView.addObject("loginDetails", decryptedLoginDetails); 
		modelAndView.addObject("customercarddetails", customercarddetails); 
		modelAndView.addObject("usertxnhistory", usertxnhistory); 
		return modelAndView; 
	}
	
	/**
	 * New Login Details
	 */
	@RequestMapping("/new")
	public String newLoginDetails(Map<String, Object> model) {
		Logindetails logindetails = new Logindetails();
		model.put("logindetails", logindetails);
		return "admin_login_new";
	}
	
	/**
	 * Save Login Details
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveLoginDetails(@ModelAttribute("logindetails") Logindetails logindetails) {
		Logindetails encryptedLogindetails = adminService.encryptLoginDetails(logindetails);
		adminService.save(encryptedLogindetails);
		return "redirect:/admin_index";
	}
	
	/**
	 * Save Customer Card Details
	 */
	@RequestMapping(value = "/saveCCard", method = RequestMethod.POST)
	public String saveCCardDetails(@ModelAttribute("customercarddetails") Customercarddetails customercarddetails) {
		adminService.saveCCardDetails(customercarddetails);
		return "redirect:/admin_index";
	}
	
	/**
	 * Edit Login Details
	 */
	@RequestMapping("/edit")
	public ModelAndView editLoginDetails(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("admin_login_edit");
		Logindetails logindetails = adminService.get(id);
		List<Logindetails> temp = new ArrayList<Logindetails>();
		temp.add(logindetails);
		List<Logindetails> decryptedLoginDetails = adminService.decryptLoginDetails(temp);
		modelAndView.addObject("logindetails", decryptedLoginDetails.get(0));
		return modelAndView;
	}
	
	/**
	 * Edit Customer Card Details
	 */
	@RequestMapping("/editCCard")
	public ModelAndView editCCardDetails(@RequestParam long id) {
		ModelAndView modelAndView = new ModelAndView("admin_ccard_edit");
		Customercarddetails customercarddetails = adminService.getCCardDetails(id);
		modelAndView.addObject("customercarddetails", customercarddetails);
		return modelAndView;
	}
	
	/**
	 * Delete Login Details
	 */
	@RequestMapping("/delete")
	public String deleteLoginDetails(@RequestParam long id) {
		adminService.delete(id);
		return "redirect:/admin_index";		
	}
	
	/**
	 * Delete Customer Card Details
	 */
	@RequestMapping("/deleteCCard")
	public String deleteCCardDetails(@RequestParam long id) {
		adminService.deleteCCardDetails(id);
		return "redirect:/admin_index";		
	}
	
	/**
	 * Delete Transaction Details
	 */
	@RequestMapping("/deleteTxn")
	public String deleteUserTxnHistory(@RequestParam long id) {
		adminService.deleteUserTxnHistory(id);
		return "redirect:/admin_index";		
	}
	
	/**
	 * Search Result page
	 */
	@RequestMapping("/search") 
	public ModelAndView search(@RequestParam String keyword) { 

		List<Logindetails> result = adminService.search(AES.encrypt(keyword, BlasecConstants.CIPHER_KEY));
		List<Logindetails> decryptedLoginDetails = adminService.decryptLoginDetails(result);
		List<Customercarddetails> resultCCardDetails = adminService.searchCCardDetails(keyword);
		List<Usertxnhistory> resultUserTxnHistory = adminService.searchUserTxnHistory(keyword);
		ModelAndView modelAndView = new ModelAndView("admin_search"); 
		modelAndView.addObject("result", decryptedLoginDetails); 
		modelAndView.addObject("resultCCardDetails", resultCCardDetails); 
		modelAndView.addObject("resultUserTxnHistory", resultUserTxnHistory); 
		return modelAndView; 
	}
	
	/**
	 * BlackList View - IP, Card Holder's Name, Card Holder's Address and Card Number
	 */
	@RequestMapping("/blacklist")
	public ModelAndView getBlacklistDetails() {
		ModelAndView modelAndView = new ModelAndView("blacklist_view"); 
		Blacklist blacklist = adminService.getBlacklistDetails();
		modelAndView.addObject("blacklist", blacklist);
		return modelAndView;
	}
	
	/**
	 * BlackList View - IP, Card Holder's Name, Card Holder's Address and Card Number
	 */
	@RequestMapping(value = "/blacklistsave", method = RequestMethod.POST)
	public String saveBlacklistDetails(@ModelAttribute("blacklist") Blacklist blacklist) {
		adminService.saveBlacklistDetails(blacklist);
		return "redirect:/admin_index";	
	}
}
package com.springboot.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.Entity.Category;
import com.springboot.Entity.User;
import com.springboot.Service.CategroyServiceImp;
import com.springboot.Service.UserServiceImp;

@Controller
@RequestMapping("/User")
public class UserController {

	@Autowired
	private UserServiceImp userServiceImp;
	
	@Autowired
	private CategroyServiceImp categroyServiceImp;
	
	@GetMapping("/")
	public String home() {
		return "User/home";
	}
	
	@ModelAttribute
	public void getUserDetails(Principal principal , Model model) {
		if(principal != null) {
			String email = principal.getName();
			User user = userServiceImp.getUserByEmail(email);
			model.addAttribute("user", user);
		}		
	    List<Category> category = categroyServiceImp.getAllActiveCategory();
		model.addAttribute("categories", category);
	}
}

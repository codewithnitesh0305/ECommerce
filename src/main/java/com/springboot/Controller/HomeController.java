package com.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.Service.CategroyServiceImp;
import com.springboot.Service.ProductServiceImp;



@Controller
public class HomeController {

	@Autowired
	private CategroyServiceImp categroyServiceImp;
	
	@Autowired
	private ProductServiceImp productServiceImp;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("category", categroyServiceImp.getAllActiveCategory());
		model.addAttribute("product",productServiceImp.getAllProduct());
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/product")
	public String product(Model model) {
		model.addAttribute("category", categroyServiceImp.getAllActiveCategory());
		model.addAttribute("product",productServiceImp.getAllProduct());
		return "product";
	}
	
	@GetMapping("/viewDetails")
	public String viewDetails() {
		return "viewDetails";
	}
	
}

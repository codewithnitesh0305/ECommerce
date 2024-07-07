package com.springboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
		model.addAttribute("product",productServiceImp.getAllActiveProduct());
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
		model.addAttribute("product",productServiceImp.getAllActiveProduct());
		return "product";
	}
	
	@GetMapping("/viewDetails/{id}")
	public String viewDetails(@PathVariable("id") int id, Model model) {
		model.addAttribute("productDetails", productServiceImp.getProductById(id));
		return "viewDetails";
	}
	
}

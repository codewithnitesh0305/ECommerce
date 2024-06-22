package com.springboot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdminControllor {

	@GetMapping("/")
	public String index() {
		return "Admin/index";
	}
	
	@GetMapping("/addProduct")
	public String addProduct() {
		return "Admin/addProduct";
	}
	
	@GetMapping("/addCategory")
	public String addCategroy() {
		return "Admin/addCategory";
	}
	
	@GetMapping("/viewProduct")
	public String viewProduct() {
		return "Admin/viewProduct";
	}
}

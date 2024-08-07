package com.springboot.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.Entity.Category;
import com.springboot.Entity.User;
import com.springboot.Service.CategroyServiceImp;
import com.springboot.Service.ProductServiceImp;
import com.springboot.Service.UserServiceImp;
import com.springboot.Util.CommonUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

	@Autowired
	private CategroyServiceImp categroyServiceImp;
	
	@Autowired
	private ProductServiceImp productServiceImp;
	
	@Autowired
	private UserServiceImp userServiceImp;
	@GetMapping("/")
	public String index(@RequestParam(value="category", defaultValue = "") String category, Model model) {
		model.addAttribute("category", categroyServiceImp.getAllActiveCategory());
		model.addAttribute("product",productServiceImp.getAllProduct());
		model.addAttribute("product",productServiceImp.getAllActiveProduct(category));
		model.addAttribute("paramValue", category);
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
	
	@GetMapping("/product")
	public String product(@RequestParam(value ="category", defaultValue = "") String category, Model model) {
		model.addAttribute("category", categroyServiceImp.getAllActiveCategory());
		model.addAttribute("product",productServiceImp.getAllActiveProduct(category));
		model.addAttribute("paramValue", category);
		return "product";
	}
	
	@GetMapping("/viewDetails/{id}")
	public String viewDetails(@PathVariable("id") int id, Model model) {
		model.addAttribute("productDetails", productServiceImp.getProductById(id));
		return "viewDetails";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user,@RequestParam("file")MultipartFile file,  HttpSession session) throws IOException {
		String fileName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
		user.setProfileImage(fileName);
		User saveUser = userServiceImp.saveUser(user);
		if(ObjectUtils.isEmpty(saveUser)) {
			session.setAttribute("errorMsg", "Some field are empty");
		}else {
			File saveFile = new ClassPathResource("static/Images").getFile();
			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +"Product"+File.separator + file.getOriginalFilename()); 
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			session.setAttribute("successMsg", "Register successfully...");
		}
		return "redirect:/register";
	}
	
	@GetMapping("/forgetPassword")
	public String forgetPassword() {
		return "forgetPassword";
	}
	
	@GetMapping("/resetPassword")
	public String resetPassword() {
		return "resetPassword";
	}
	
	@PostMapping("/forgetPassword")
	public String processForgetPassword(@RequestParam String email, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		User user = userServiceImp.getUserByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			session.setAttribute("errorMsg", "Invalid email...");
		}else {
			String resetToken = UUID.randomUUID().toString();
			userServiceImp.updateUserRestToken(email,resetToken);
			
			CommonUtil commonUtil = new CommonUtil();
			String url = commonUtil.generateUrl(request)+"/resetPassword?token="+resetToken;
			boolean sendEmail = commonUtil.sendEmail(email,url);
			//Generate URL - http://localhost:8080/resetPassword?token=flkjsdfklsdjfsdlkfj
			
			if(sendEmail) {
				session.setAttribute("successMsg", "Reset password link send in your email");
			}else {
				session.setAttribute("errorMsg", "Something went wrong...");
			}
		}
		return "redirect:/forgetPassword";
	}
}

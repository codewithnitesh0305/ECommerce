package com.springboot.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.Entity.Category;
import com.springboot.Service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class AdminControllor {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public String index() {
		return "Admin/index";
	}
	
	@GetMapping("/addProduct")
	public String addProduct() {
		return "Admin/addProduct";
	}
	
	@GetMapping("/addCategory")
	public String addCategroy(Model model) {
		model.addAttribute("categroy", categoryService.getAllCategory());
		return "Admin/addCategory";
	}
	
	@GetMapping("/viewProduct")
	public String viewProduct() {
		return "Admin/viewProduct";
	}
	
	@GetMapping("/editCategory/{id}")
	public String editCategroy(@PathVariable int id , Model model) {
		model.addAttribute("category", categoryService.getCategroyById(id));
		return "Admin/editCategroy";
	}
	
	//Save Product  Category in Database 
	@PostMapping("/saveCategory")
	public String saveCategroy(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, HttpSession session, Model model) throws IOException {
		
		if(categoryService.existCategory(category.getName())) {
			session.setAttribute("errorMsg", "Categroy is already exist...");
		}else {
			String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
			category.setImageName(imageName);
			Category saveCategory = categoryService.saveCategory(category);
			if(ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Some thing went wrong...");
			}else {
				//Save Images in the Images Folder
				File saveFile = new ClassPathResource("static/Images").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Category"+ File.separator + file.getOriginalFilename());
				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				session.setAttribute("successMsg", "Category add successfully...");
			}
		}		
		return "redirect:/Admin/addCategory";
	}
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id,HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategroy(id);
		System.out.println(deleteCategory);
		if(deleteCategory) {
			session.setAttribute("successMsg", "Categroy delete successfully...");
		}else {
			session.setAttribute("errorMsag", "Something went wrong...");
		}
		return "redirect:/Admin/addCategory";
	}
	
	@PostMapping("/updateCategroy")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
		Category oldCategroy = categoryService.getCategroyById(category.getId());
		//If user not update(file is null) the file than old file will take
		String fileName = file.isEmpty() ? oldCategroy.getImageName():file.getOriginalFilename();
		if(!ObjectUtils.isEmpty(oldCategroy)) {
			oldCategroy.setName(category.getName());
			oldCategroy.setImageName(fileName);
			oldCategroy.setIsactive(category.isIsactive());			
		}
		 Category saveCategory = categoryService.saveCategory(oldCategroy);	 
		 if(!ObjectUtils.isEmpty(saveCategory)) {
			 if(!file.isEmpty()) {
					//Save Images in the Images Folder
						File saveFile = new ClassPathResource("static/Images").getFile();
						Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Category"+ File.separator + file.getOriginalFilename());
						System.out.println(path);
						Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				 }
			 session.setAttribute("successMsg", "Update Successfully...");
		 }else {
			 session.setAttribute("errorMsg", "Something went wrong...");
		 }
		return "redirect:/Admin/editCategory/"+category.getId();
	}
}

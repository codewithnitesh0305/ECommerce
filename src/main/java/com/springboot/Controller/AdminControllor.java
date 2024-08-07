package com.springboot.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
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
import com.springboot.Entity.Product;
import com.springboot.Entity.User;
import com.springboot.Service.CategoryService;
import com.springboot.Service.ProductService;
import com.springboot.Service.UserServiceImp;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class AdminControllor {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;	
	@Autowired
	private UserServiceImp userServiceImp;
	
	@GetMapping("/")
	public String index() {
		return "Admin/index";
	}
	
	@ModelAttribute
	public void getUserDetails(Principal principal , Model model) {
		if(principal != null) {
			String email = principal.getName();
			User user = userServiceImp.getUserByEmail(email);
			model.addAttribute("user", user);
		}		
	    List<Category> category = categoryService.getAllActiveCategory();
		model.addAttribute("categories", category);
	}
	
	@GetMapping("/addProduct")
	public String addProduct(Model model) {
		List<Category> listCategory = categoryService.getAllCategory();
		model.addAttribute("category", listCategory);
		return "Admin/addProduct";
	}
	
	@GetMapping("/addCategory")
	public String addCategroy(Model model) {
		model.addAttribute("categroy", categoryService.getAllCategory());
		return "Admin/addCategory";
	}
	
	@GetMapping("/viewProduct")
	public String viewProduct(Model model) {
		model.addAttribute("product", productService.getAllProduct());
		return "Admin/viewProduct";
	}
	
	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		model.addAttribute("categories", categoryService.getAllCategory());
		return "Admin/editProduct";
	}
	
	@GetMapping("/editCategory/{id}")
	public String editCategroy(@PathVariable int id , Model model) {
		model.addAttribute("category", categoryService.getCategroyById(id));
		return "Admin/editCategroy";
	}
	
	@GetMapping("/viewUsers")
	public String viewUsers(Model model) {
		model.addAttribute("users", userServiceImp.getAllUsersByRole("ROLE_USER"));
		return "Admin/viewUsers";
	}
	//Save Product  Category in Database 
	@PostMapping("/saveCategory")
	public String saveCategroy(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, HttpSession session, Model model) throws IOException {
		
		if(categoryService.existCategory(category.getName())) {
			session.setAttribute("errorMsg", "Categroy is already exist...");
		}else {
			//If the User not add the file than the default.jpg(file name) is store in database
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
	//Delete Product Category in Database
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
	
	//Update Product Category in Database 
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
	
	//Add Product to the Database
	@PostMapping("/saveProduct")
	public String addProdduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {		
		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		product.setImageName(imageName);
		product.setDiscount(0);
		System.out.println(product.isIsactive());
		product.setDiscountPrice(product.getPrice());
		  Product saveProduct = productService.saveProduct(product);
		  if(ObjectUtils.isEmpty(saveProduct)) { 
			  session.setAttribute("errorMsg","Some field in empty..."); 
		  }else { 
			  //Save Images in the Images Folder File
		  File saveFile = new ClassPathResource("static/Images").getFile();
		  Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Product"+File.separator + file.getOriginalFilename()); 
		  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		  session.setAttribute("successMsg", "Product add successfully..."); 
		  }
		 
		return "redirect:/Admin/addProduct";
	}
	
	//Delete Product by id from database
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id, HttpSession session) {
		boolean deleteProduct = productService.deleteProductById(id);
		if(deleteProduct) {
			session.setAttribute("successMsg", "Product Delete Successfully...");
		}else {
			session.setAttribute("errorMsg", "Something went wrong...");
		}
		return "redirect:/Admin/viewProduct";
	}
	
	//Update Product from database
	@PostMapping("/updateProdcut")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
		if(product.getDiscount()  <0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "Invalid Discount");
		}else {
			Product oldProduct = productService.getProductById(product.getId());
			String imageName = file.isEmpty() ? oldProduct.getImageName() : file.getOriginalFilename();
			oldProduct.setTitle(product.getTitle());
			oldProduct.setDescription(product.getDescription());
			oldProduct.setCategory(product.getCategory());
			oldProduct.setPrice(product.getPrice());
			oldProduct.setStock(product.getStock());
			oldProduct.setImageName(imageName);
			oldProduct.setIsactive(product.isIsactive());
			oldProduct.setDiscount(product.getDiscount());
			Double discount = product.getPrice()*(product.getDiscount()/100.0);
			Double discountPrice = product.getPrice() - discount;
			oldProduct.setDiscountPrice(discountPrice);
			Product updateProduct = productService.updateProduct(oldProduct);
			if(!ObjectUtils.isEmpty(updateProduct)) {			
				if(!file.isEmpty()) {
					File saveFile = new ClassPathResource("static/Images").getFile();
					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "Product" + File.separator + file.getOriginalFilename());
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);				
				}
				session.setAttribute("successMsg", "Product update successfully...");
			}else {
				session.setAttribute("errorMsg", "Something went wrong");
			}
		}		
		return "redirect:/Admin/editProduct/"+product.getId();
	}
	
	@GetMapping("/updateStatus")
	public String updateUserStatus(@RequestParam("status") Boolean status, @RequestParam("id") Integer id, HttpSession session) {
		boolean update = userServiceImp.updateUserStatus(id, status);
		if(update) {
			session.setAttribute("successMsg", "User status update successfully...");
		}else {
			session.setAttribute("errorMsg", "Something went worg...");
		}
		return "redirect:/Admin/viewUsers";
	}
	
}

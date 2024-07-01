package com.springboot.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.Entity.Product;
import com.springboot.Repository.ProductRepositoy;

@Service
public class ProductServiceImp implements ProductService{

	@Autowired
	private ProductRepositoy productRepositoy;
	
	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepositoy.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepositoy.findAll();
	}

	@Override
	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return productRepositoy.findById(id);
	}

	@Override
	public boolean deleteProductById(int id) {
		// TODO Auto-generated method stub
		Product product = productRepositoy.findById(id);
		if(!product.equals(null)) {
			productRepositoy.delete(product);
			return true;		
		}
		return false;
	}

	@Override
	public Product updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepositoy.save(product);
	}

}

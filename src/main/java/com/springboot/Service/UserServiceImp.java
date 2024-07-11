package com.springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.Entity.User;
import com.springboot.Repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setRole("ROLE_ADMIN");
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}

package com.springboot.Service;

import java.util.List;
import java.util.Optional;

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
		user.setEnable(true);
		user.setRole("ROLE_USER");
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public List<User> getAllUsersByRole(String role) {
		// TODO Auto-generated method stub
		List<User> user = userRepository.findByRole(role);
		return user;
	}

	@Override
	public boolean updateUserStatus(Integer id, Boolean status) {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepository.findById(id);
		if(findById.isPresent()) {
			User user = findById.get();
			user.setEnable(status);
			userRepository.save(user);
			return true;
		}
		return false;
	}
}

package com.springboot.Sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springboot.Entity.User;
import com.springboot.Repository.UserRepository;

@Component
public class CustomeUserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with"+username+ " not found");
		}
		return new CustomeUser(user);
	}

}

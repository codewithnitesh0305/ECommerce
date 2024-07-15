package com.springboot.Service;

import java.util.List;

import com.springboot.Entity.User;

public interface UserService {

	public User saveUser(User user);
	public User getUserByEmail(String email);
	public List<User> getAllUsersByRole(String role);
	public boolean updateUserStatus(Integer id, Boolean status);
	public boolean existEmail(String email);
	public void updateUserRestToken(String email, String resetToken);
}

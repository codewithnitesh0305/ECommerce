package com.springboot.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	public List<User> findByRole(String role);
	public Optional<User> findById(Integer id);
	public User existsByEmail(String email);
}

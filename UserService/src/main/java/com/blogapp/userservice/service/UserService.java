package com.blogapp.userservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.userservice.entity.User;
import com.blogapp.userservice.exception.ResourceNotFoundException;
import com.blogapp.userservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User createUser(User user) {
		User savedUser = this.userRepo.save(user);
		return savedUser;
	}
	
	public User updateUser(User userPayload,Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
		user.setName(userPayload.getName());
		user.setEmail(userPayload.getEmail());
		user.setPhoneNo(userPayload.getPhoneNo());
		User updatedUser = this.userRepo.save(user);
		return updatedUser;
	}
	
	public void deleteUser(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
		this.userRepo.delete(user);
	}
	
	public User getUserById(Long userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," id ",userId));
		return user;
	}
	
	public List<User> getAllUsers(){
		List<User> users = this.userRepo.findAll();
		return users;
	}
	
}

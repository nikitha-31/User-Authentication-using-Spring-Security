package com.user.service;

import java.util.List;

import com.user.model.User;

public interface UserProfileService {

	public User getUserProfile(String jwt);
	
	public List<User> getAllUsers();
}

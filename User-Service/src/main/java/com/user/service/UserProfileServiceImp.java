package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.config.JwtProvider;
import com.user.model.User;
import com.user.repository.UserRepository;


@Service
public class UserProfileServiceImp implements UserProfileService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUserProfile(String jwt) {
		String email=JwtProvider.getEmailFromJwtToken(jwt);
		return userRepository.findByEmail(email);
		
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}

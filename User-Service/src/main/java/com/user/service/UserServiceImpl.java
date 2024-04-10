package com.user.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("the email"+email+"is invalid");
		}
		List<GrantedAuthority> authorities=new ArrayList<>();
		// TODO Auto-generated method stub
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}


}

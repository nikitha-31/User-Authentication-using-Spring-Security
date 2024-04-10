package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.user.config.JwtProvider;
import com.user.model.User;
import com.user.repository.UserRepository;
import com.user.request.LoginRequest;
import com.user.response.AuthResponse;
import com.user.service.UserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws Exception{
		String email=user.getEmail();
		String password=user.getPassword();
		String fullname=user.getFullname();
		String role=user.getRole();
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null) {
			throw new Exception("Email already used");
		}
		
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setFullname(fullname);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setRole(role);
		
		User savedUser= userRepository.save(createdUser);
		
		userRepository.save(savedUser);
		
		Authentication authentication= new UsernamePasswordAuthenticationToken(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=JwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Registered sucessfully");
		authResponse.setStatus(true);
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		System.out.println(username+"-----"+password);
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=JwtProvider.generateToken(authentication);
		AuthResponse authResponse=new AuthResponse();
		authResponse.setMessage("Login Sucessfull");
		authResponse.setJwt(token);
		authResponse.setStatus(true);
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails=userServiceImpl.loadUserByUsername(email);
		System.out.println("user details"+userDetails);
		
		if(userDetails==null) {
			System.out.println("user details null"+userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			System.out.println("sign in user details - wrong password"+userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
	
	

}

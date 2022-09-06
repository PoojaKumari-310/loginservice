package com.cg.service;

import java.util.ArrayList;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.entity.User;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	//UserDetailsService interface has a method to load User by username and returns a UserDetails object that Spring Security can use for authentication and validation.
// returns the authorities
	//UserDetails contains necessary information (such as: username, password, authorities) to build an Authentication object.
	//throws UsernameNotFoundException if the user could not be found or the user has no grantedAuthority.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = restTemplate.getForObject("http://localhost:8083/user/find/"+username, User.class);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}

}

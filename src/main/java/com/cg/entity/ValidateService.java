// This is for validate the token.valid is true or false it is say that that's it.

package com.cg.entity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cg.service.CustomUserDetailsService;
import com.cg.util.JwtUtil;

@Service
public class ValidateService {
	@Autowired
	private JwtUtil jwtutils;

	@Autowired
	private CustomUserDetailsService service;
	


	/**
	 * @param token compare the token returns the authenticationResponse
	 */
	
	
	public AuthenticationResponse validate(String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		String jwt = token.substring(7); // Bearer space

		authenticationResponse.setJwtToken(jwt);
		authenticationResponse.setValid(jwtutils.validateJwtToken(jwt));
		return authenticationResponse;
	}

}

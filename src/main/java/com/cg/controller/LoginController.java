package com.cg.controller;

// here we break binary stream into groups of 6 bits. like  000100-4

// Base-64 encoding is a way of taking binary data and turning it into text so that it's more easily transmitted in things like e-mail and HTML form data.
import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.entity.AuthRequest;
import com.cg.entity.AuthenticationResponse;
import com.cg.entity.LoginResponse;
import com.cg.entity.User;
import com.cg.entity.ValidateService;
import com.cg.util.JwtUtil;

@RestController // to response get , post method.
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;
	

	@Autowired
	ValidateService validateService;
  
	// it can be put directly in the class 
	@Autowired
	private RestTemplate restTemplate;


	@PostMapping("/generate")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {

		try {
			// validate the username and password
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			
			String jwt = jwtUtil.generateToken(authRequest.getUsername());


			User user = restTemplate.getForObject("http://localhost:8083/user/find/"+ authRequest.getUsername(),
					User.class);

			return ResponseEntity
					.ok(new LoginResponse(user.getUserId(), user.getName(), user.getEmail(), user.getMobile(), jwt));

		}

		catch (Exception e) // e is exception
		{
			throw new Exception("Invalid Username or password");
		}

	
	}
	
	@GetMapping("/validate")
	//Headers carry information for: Request and Response Body. Request Authorization.
    public AuthenticationResponse getValidity(@RequestHeader(name="Authorization")  String token) {
//       byte[] byt = Base64.getUrlDecoder().decode(token);
       
       return validateService.validate(token);
    }

}

	
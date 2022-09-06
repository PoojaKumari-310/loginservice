package com.cg;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class LoginserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginserviceApplication.class, args);
	}
	// RestTemplate return
	//RestTemplate Methods

	//request and returns nothing. that contains both the HTTP status code and the resource as an object.
	// default implementation RestTemplate
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}

}

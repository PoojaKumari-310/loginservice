//JwtUtils provides methods for generating, parsing, validating JWT

package com.cg.util;

import java.util.Date;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;

@Service
public class JwtUtil {

	// JWT is created with a secret key and that secret key is private to you which means you will never reveal that to the public or inject inside the JWT token. When you receive a JWT from the client, you can verify that JWT with this that secret key stored on the server.
	
	private String secret = "poojakumaripkkpoojakumaripkkpoojakumaripkkpoojakumaripkkpoojakumaripkk";
	//logger is nothing but a kind  like in phone we have call logs the list of call we have in recent ,in  browsing we have history kind of things they store all those things 
	// when user registered itself means it will stored after login of it is matching then it will generate token
	// logging the basic way to add logging is to use this class called logger factory and it has a method called get logger it is a static method it takes in our class so that it knows which class is doing the
	// actual logging and then you get a logger object out of this.(get logger is a standard way for to log in java using this framework called SLf4j)
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    
	//JSON web tokens (JWTs) claims are pieces of information asserted about a subject. For example, an ID token (which is always a JWT) can contain a claim called name that asserts that the name of the user authenticating is "John Doe".
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())) //subject is a Username 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // issued token means current date
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }


    // if logger is matching  it is true then ok in try but if it is false it's showing error in catch in different-2 error msgs we are handling the errors a/c to exception
    public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}


}

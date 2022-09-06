// In entity class we will create one  response class so it will return the object.

package com.cg.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationResponse {
	private String jwtToken;
    private boolean valid;
    // here there is a method which is valid so this is validate the token 
    public AuthenticationResponse(String jwtToken, Boolean valid) {
        super();
        this.jwtToken = jwtToken;
        this.valid = valid;
    }

    public AuthenticationResponse() {
        super();
    }
 
    public String getJwtToken() {
        return jwtToken;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
    public boolean getValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
}



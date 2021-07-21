package com.example.jwtAuth.demo.Payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	
	private String username;
	private String email;
	private String password;
	
    private Set<String> role;

    
    public Set<String> getRole() {
        return this.role;
      }
      
      public void setRole(Set<String> role) {
        this.role = role;
      }
}

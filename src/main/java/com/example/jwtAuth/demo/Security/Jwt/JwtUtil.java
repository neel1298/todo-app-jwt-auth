package com.example.jwtAuth.demo.Security.Jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.jwtAuth.demo.Security.Services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


	@Value("${neelp.app.jwtSecret}")
	private String jwtSecret;

	@Value("${neelp.app.jwtExpirationMs}")
	private int jwtExpire;

	final Date createdDate = new Date();
	
	final Date expirationDate = new Date( createdDate.getTime() + jwtExpire);

	public String generateToken(Authentication auth) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			
		return Jwts.builder()
			   .setSubject(userDetails.getUsername())
			   .setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpire))
			   .signWith(SignatureAlgorithm.HS512, jwtSecret)
			   .compact();
		
		
	}
	
	public String getUsernameFromJwt(String authToken) {
		
		String username=Jwts.parser()
						.setSigningKey(jwtSecret)
						.parseClaimsJws(authToken)
						.getBody().getSubject();
		
		return username;
	}

	
	public boolean ValidateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
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


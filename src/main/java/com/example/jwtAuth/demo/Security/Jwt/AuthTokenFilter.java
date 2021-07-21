package com.example.jwtAuth.demo.Security.Jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwtAuth.demo.Security.Services.UserDetailsServiceImpl;


public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtils;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		
			String jwtToken = parseJwt(request);
			if(jwtToken!=null && jwtUtils.ValidateJwtToken(jwtToken)) {
			
				String username = jwtUtils.getUsernameFromJwt(jwtToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails , "" , userDetails.getAuthorities()	
						);
						
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch (Exception e) {
			
				System.out.println("Cannot set user Authentication");
		
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
	public String parseJwt(HttpServletRequest request) {
		
		String jwtToken = request.getHeader("Authorization");
		if(StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer ")) {
			return jwtToken.substring(7,jwtToken.length());
		}
		
		return null;
	}

}

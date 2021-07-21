package com.example.jwtAuth.demo.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtAuth.demo.Payload.JwtResponse;
import com.example.jwtAuth.demo.Payload.LoginRequest;
import com.example.jwtAuth.demo.Payload.SignUpRequest;
import com.example.jwtAuth.demo.Repository.RoleRepository;
import com.example.jwtAuth.demo.Repository.UserRepository;
import com.example.jwtAuth.demo.Security.Jwt.JwtUtil;
import com.example.jwtAuth.demo.Security.Services.UserDetailsImpl;
import com.example.jwtAuth.demo.models.ERole;
import com.example.jwtAuth.demo.models.Role;
import com.example.jwtAuth.demo.models.User;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	JwtUtil jwtUtils;
	
	@Autowired
	PasswordEncoder passEncode;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/test")
	public String testapi() {
		return "hello";
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String newJwt = jwtUtils.generateToken(authentication);
		
		UserDetailsImpl userDetailImp = (UserDetailsImpl) authentication.getPrincipal();
		List<String> userRoles = userDetailImp.getAuthorities().stream()
								.map(item->item.getAuthority())
								.collect(Collectors.toList());
		
		
		return ResponseEntity.ok(newJwt);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signNewUser(@Valid @RequestBody SignUpRequest signUpReq){
		
	
		if(userRepository.existsByEmail(signUpReq.getEmail()) || userRepository.existsByUsername(signUpReq.getUsername())) {
			
			return ResponseEntity
					.badRequest()
					.body("Error: Username or Email already taken");
		}
		
		
		User user = new User(signUpReq.getUsername(),signUpReq.getEmail(),passEncode.encode(signUpReq.getPassword()));
		
		
		Set<String> strRoles = signUpReq.getRole();
		
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MOD)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		
		return  ResponseEntity.ok("User registered successfully!");
	}
	
	
	
}

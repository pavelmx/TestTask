package com.pavel.TestTask.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavel.TestTask.entity.auth.KindOfRole;
import com.pavel.TestTask.entity.auth.Role;
import com.pavel.TestTask.entity.auth.User;
import com.pavel.TestTask.repository.RoleRepository;
import com.pavel.TestTask.repository.UserRepository;
import com.pavel.TestTask.security.JwtProvider;
import com.pavel.TestTask.security.JwtResponse;
import com.pavel.TestTask.security.Login;
import com.pavel.TestTask.security.Register;
import com.pavel.TestTask.security.ResponseMessage;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody Login login) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody Register reg) {
		if (userRepository.existsByUsername(reg.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(reg.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = new User(reg.getName(), reg.getUsername(), reg.getEmail(),
				encoder.encode(reg.getPassword()));

		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName(KindOfRole.ROLE_USER));
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}

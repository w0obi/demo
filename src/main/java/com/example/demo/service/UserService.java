package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserEntity create(final UserEntity userentity) {
		if(userentity == null || userentity.getUsername() == null) {
			throw new RuntimeException("Invalid arguments");
			
		}
		final String username = userentity.getUsername();
		if(userRepository.existsByUsername(username)) {
			log.warn("Username already exists {}", username);
			throw new RuntimeException("Username already exists");
		}
		return userRepository.save(userentity);
	}
	
	public UserEntity getByCredentials(final String username, String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByUsername(username);
		
		if(originalUser != null &&
			encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}
}

package com.seyithandilek.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.seyithandilek.springboot.dto.UserRegistrationDTO;
import com.seyithandilek.springboot.model.User;

public interface UserService extends UserDetailsService {
	User save(UserRegistrationDTO registrationDto);

}

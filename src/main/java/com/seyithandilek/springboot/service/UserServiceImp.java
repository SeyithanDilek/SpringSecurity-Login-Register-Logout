package com.seyithandilek.springboot.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.seyithandilek.springboot.dto.UserRegistrationDTO;
import com.seyithandilek.springboot.model.Role;
import com.seyithandilek.springboot.model.User;
import com.seyithandilek.springboot.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	private UserRepository repository;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImp(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User save(UserRegistrationDTO registrationDto) {
		User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));

		return repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalida username or password");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), null);
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}

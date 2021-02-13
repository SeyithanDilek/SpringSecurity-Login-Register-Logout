package com.seyithandilek.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seyithandilek.springboot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}

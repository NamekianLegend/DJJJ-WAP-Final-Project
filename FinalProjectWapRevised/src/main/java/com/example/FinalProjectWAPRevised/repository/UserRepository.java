package com.example.FinalProjectWAPRevised.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinalProjectWAPRevised.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}

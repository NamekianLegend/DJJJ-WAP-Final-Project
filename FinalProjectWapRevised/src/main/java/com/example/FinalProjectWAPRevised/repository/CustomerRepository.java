package com.example.FinalProjectWAPRevised.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinalProjectWAPRevised.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}

package com.example.FinalProjectWAPRevised.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FinalProjectWAPRevised.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByTitle(String title);
}

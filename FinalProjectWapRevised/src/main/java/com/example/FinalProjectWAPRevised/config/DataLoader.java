
package com.example.FinalProjectWAPRevised.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadBooks(ItemRepository itemRepository) {
        return args -> {

            if (itemRepository.count() == 0) {

                itemRepository.save(new Item("Harry Potter", "J.K. Rowling", 20.00));
                itemRepository.save(new Item("The Hobbit", "J.R.R. Tolkien", 31.00));
                itemRepository.save(new Item("1984", "George Orwell", 55.40));
                itemRepository.save(new Item("The Great Gatsby", "F. Scott Fitzgerald", 100.99));

                System.out.println("Sample books loaded into database.");
            }
        };
    }
}

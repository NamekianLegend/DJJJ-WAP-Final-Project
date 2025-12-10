package com.example.FinalProjectWAPRevised.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String email;
    private String creditCard;
    private String password;

    public Customer(){};

    public Customer(String name, String email, String password) {
            this.username = name;
            this.email = email;
            this.creditCard = generateCreditCard();
            this.password = password;
    }
    // list for what the user has bought
    @ManyToMany
    @JoinTable(
        name = "customer_basket",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> basket = new ArrayList<>();

    // getters and settters

    
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getBasket() {
        return basket;
    }

    public void setBasket(List<Item> basket) {
        this.basket = basket;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getPassword() {
        return password;
    }

    static String generateCreditCard() {
        String cardNumber = "";
        for (int i = 0; i < 16; i++) {
            cardNumber += Integer.toString((int) (Math.random() * 10));
        }

        return cardNumber;
    }
    
    public void displayDetails() {
        System.out.println("Name: " + username + " | Email: " + email + " | Credit Card: " + creditCard);
    }
}

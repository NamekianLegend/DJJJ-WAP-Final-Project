package com.example.FinalProjectWAPRevised.model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String email;
    private String creditCard;
    private String password;

    public Customer(){};

    public Customer(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.creditCard = generateCreditCard();
            this.password = password;
    }
    // list for what the user has bought
    private ArrayList<Item> itemBought = new ArrayList<>();

    // getters and settters
    public ArrayList<Item> getItemBought() {
        return itemBought;
    }

    public void setItemBought(ArrayList<Item> itemBought) {
        this.itemBought = itemBought;
    }

    public String getName() {
        return name;
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
        System.out.println("Name: " + name + " | Email: " + email + " | Credit Card: " + creditCard);
    }
}

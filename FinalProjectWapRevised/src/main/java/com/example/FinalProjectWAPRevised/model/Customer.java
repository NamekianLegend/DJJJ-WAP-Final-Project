package com.example.FinalProjectWAPRevised.model;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Name cannot be blank")
    private String name;


    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    private String creditCard;
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{6,120}$",
        message = "Password must contain upper, lower, number, and special character"
    )
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

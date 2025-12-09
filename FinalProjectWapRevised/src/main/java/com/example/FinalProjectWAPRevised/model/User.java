package com.example.FinalProjectWAPRevised.model;

import java.util.ArrayList;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {


    @NotBlank(message = "No Blanks ")
    @NotNull
    @Size(min = 5, message = "Name should have at least 5 characters")
    private String name;


    @NotBlank(message = "I need an email from you please and thank you! ")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Stop trying to use a bad email! >:(")
        @Size(max = 100, message = "Email must be less than 100 characters")
    @Email(message = "Im expecting a VALID email please and thank you!")
    private String email;

    @NotBlank(message = "No Blanks ")
    private String creditCard;
     @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{6,120}$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;

   public User(String name, String password) {
        this.name = name;
        this.email = name + "@gmail.com";
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
}

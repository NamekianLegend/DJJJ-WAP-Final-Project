package com.example.FinalProjectWAP.model;


public class User {
    private String name;
    private String email;
    private String creditCard;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.email = name + "@gmail.com";
        this.creditCard = generateCreditCard();
        this.password = password;
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
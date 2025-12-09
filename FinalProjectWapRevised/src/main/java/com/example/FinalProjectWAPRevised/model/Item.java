package com.example.FinalProjectWAPRevised.model;


import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Double price;
    private boolean bought = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // owner of basket


    // default constructor
    public Item (){}
    
    public Item(String title, String author, Double price, Customer customer) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.customer = customer;
    }


    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isBought(){
        return bought;
    }
    public void setBought(boolean bought){
        this.bought =  bought;
    }


    public String toString() {
        return "Item \n\t[Id: " + id + 
                ", Title: " + title + 
                ", Author: " + author + 
                ", Price: " + price + "]";
    }   
}

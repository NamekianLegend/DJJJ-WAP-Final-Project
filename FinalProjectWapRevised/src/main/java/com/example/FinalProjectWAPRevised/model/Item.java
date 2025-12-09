package com.example.FinalProjectWAPRevised.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Double price;

    
    public Item(Long id, String title, String author, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
    // default constructor
    public Item (){}

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

    // to string, do we need this? I think we do, delete if not needed i presumed we needed a to string
    @Override
    public String toString() {
        return "Item [id=" + id + 
        ", title=" + title + 
        ", author=" + author + 
        ", price=" + price + "]";
    }
    public boolean isBought() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBought'");
    }


    
}

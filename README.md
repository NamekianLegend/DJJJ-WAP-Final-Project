# Final Project: Bookstore

Brief Description: 

This project is the culumination of everything that we have learned in INET2005 Web Application Programming

this project includes the usage of the following areas of topics as discussed in class:

• Register for an account

• Log in and access their profile

• View available products or books

• Add items to a basket

• Review items in the basket

• Complete a checkout process



## Why this Project?

We chose to create this project, based on familiarity with similar system that was deemed intuitive enough to start with a challengeing outcome towards the end. a perfect blend
## How does this work?

The program strives to mimic a typical online system one would use to purchase reading material for availability, unless it is unavailable.

- If the book in question is available for purchase, the client can add the book out to their basket; they may proceed to the checkout or continue browsing.

- If the book is not available, the book cannot be purchased.

- Once the client has provided their mailing address and valid credit card, the user will be notified the purchase was successful!

## Features

The web app has the following features:

- User Registration and Login

• Register new users
• Validate email and password
• Store passwords using hashing
• Log in with sessions or cookies
• Redirect to a homepage or dashboard after login

- Product or Book Catalog

• Display a list of available items
• Store data in a database (H2 or MySQL)
• Use JPA or JDBC to retrieve items
• Include product details such as name, price, description.

- Basket Functionality

• Add items to the basket
• Remove items from the basket
• View basket contents at any time
• Store basket data using sessions or cookies

- Checkout

• Show total price
• Confirm purchase
• Save order in the database

- Structure

We are using the Model View Controller Pattern, learned from class

-Persistent Data

• JPA ORM with Entities and Repositories


- Security Requirements

• Authentication
• Authorization for protected pages
• Password hashing
• SQL injection prevention

- Testing
• One service test
• One controller test using MockMVC

- Code Organization

• controller
• model
• repository
• service
• configuration
• templates (Thymeleaf)
• static (CSS, JS)



- ## How to log in

- The client will first need to register into the system by providing the relevant information to sign into the homepage

- If the prerequistes to register have been succesful, the user need only provide the approved login credentials and begin browsing the selections available

- When the user decides on a book they wish to purchase, they need only click on the purchse button beside the desired book, the book will be added to the users basket.

- When ready, the user may check their basket and head to the checkout to make their purchase.

- From thier, the user must provide their mailing address and a valid credit card. If accepted the user will be shown that thier purchase was a success.

- At anytime the user may wish to logout with their choices saved to the basket, when logged back in their selections will still be there.

- The user may at anytime if they so chose, update their profile.

## How to run it 

-To run, download the repo to your machine, run the servlet from your IDE of choice.
- in your web browser of choice, type http://localhost:8080/

- from there, please set up a login for yourself, your password should be a capital, a special character and a number and exceed the minimum character requirement

- Once you have successfully logged in, you are free to browse what books are available for you to purchase!

- when you have selected a book you wish to purchase, click on the purchase button to add the book to your basket

- you may click on the basket button to confirm your choices, you will proceed to the checkout page.

- From there, enter a valid mailing address, plus a valid credit card number; if successful you will be greated with a success page that the transaction was a success.

- Thank you for exploring our Book Store System!

## Authors

Dawson Brown, Judah Csyani, Joshua Leslie , Jeremy Paruch

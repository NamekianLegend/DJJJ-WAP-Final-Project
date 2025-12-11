// this class is used to handle all of the db operations like .save, and .findall
// so we don't have to call them in the controller itself
// rather we can call the functions from the service class in the controller class
package com.example.FinalProjectWAPRevised.service;

import com.example.FinalProjectWAPRevised.model.Customer;
import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.model.userForm;
import com.example.FinalProjectWAPRevised.repository.CustomerRepository;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;

import jakarta.servlet.http.HttpSession;

import com.example.FinalProjectWAPRevised.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Handler;


@Service
public class CustomerService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public CustomerService(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }


    //-----------------------------LOGIN-----------------------------

    // authenticate login
    public Customer authenticate(String username, String password){
        List<Customer> customers = customerRepository.findAll();
        for(Customer customer: customers){
            if(customer.getUsername().equals(username) && passwordEncoder.matches(password, customer.getPassword())){
                return customer;
            }
        }

        return null;
    }

    // register new user
    @Transactional
    public void registerCustomer(userForm form) {
        String hashedPassword = passwordEncoder.encode(form.getPassword());
        Customer newCustomer = new Customer(form.getName(), form.getEmail(), hashedPassword);
        customerRepository.save(newCustomer);
    }

    @Transactional
    public HttpSession updateCustomer(userForm form, Long id, HttpSession session){
        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Customer not found"));

        Customer c1 = (Customer)session.getAttribute("loggedInCustomer");
        System.out.println("(Before) Session Password hashed: "+c1.getPassword());
        System.out.println("(Before) Repo Password hashed: "+updatedCustomer.getPassword());
        System.out.println("(Before) Password: "+(String)session.getAttribute("password"));


        String hashedPassword = passwordEncoder.encode(form.getPassword());
        updatedCustomer.setPassword(hashedPassword);
        updatedCustomer.setEmail(form.getEmail());
        updatedCustomer.setUsername(form.getName());




        
        customerRepository.save(updatedCustomer);
        session.setAttribute("password", form.getPassword());
        session.setAttribute("loggedInCustomer", updatedCustomer);

        c1 = (Customer)session.getAttribute("loggedInCustomer");

        System.out.println("(After) Session Password hashed: "+c1.getPassword());
        System.out.println("(After) Repo Password hashed: "+updatedCustomer.getPassword());
        System.out.println("(After) Password: "+(String)session.getAttribute("password"));

        return session;
    }

    // get by id
    public Customer getById(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found"));
    }



    //-----------------------------BASKET-----------------------------

    // add to basket
    @Transactional
    public Customer addItemToBasket(Long customerId, Long itemId){
        // initialize the customer by id, and the item by id
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new RuntimeException("Customer not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new RuntimeException("Item not found"));

        // if customer's basket doesnt contain the item, add the item to the customer's basket and save it to the db
        if (!customer.getBasket().contains(item)) {
            customer.getBasket().add(item);
            return customerRepository.save(customer);
        }
        return customer;
    }

    // remove from basket
    @Transactional
    public Customer removeItemFromBasket(Long customerId, Long itemId) {
        // fetch the customer from db
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // remove item
        customer.getBasket().removeIf(item -> item.getId().equals(itemId));

        //save changes
        return customerRepository.save(customer);
    }

    // show basket
    @Transactional(readOnly = true)
    public Customer showBasket(String username){
        //customer.getBasket().size(); im not sure why this was here
        return customerRepository.findByUsername(username);
    }

}

// this class is used to handle all of the db operations like .save, and .findall
// so we don't have to call them in the controller itself
// rather we can call the functions from the service class in the controller class
package com.example.FinalProjectWAPRevised.service;

import com.example.FinalProjectWAPRevised.model.Customer;
import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.model.userForm;
import com.example.FinalProjectWAPRevised.repository.CustomerRepository;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public CustomerService(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }


    //-----------------------------LOGIN-----------------------------

    // authenticate login
    public Customer authenticate(String username, String password){
        // Find user by name and password from repository
        return customerRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null); //if user doesn't exist return null
    }

    // register new user
    @Transactional
    public void registerCustomer(userForm form) {
        Customer newCustomer = new Customer(form.getName(), form.getEmail(), form.getPassword());
        customerRepository.save(newCustomer);
    }



    //-----------------------------BASKET-----------------------------

    // add to basket
    @Transactional
    public Customer addItemToBasket(Long customerId, Long itemId){
        // initialize the customer by id, and the item by id
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

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

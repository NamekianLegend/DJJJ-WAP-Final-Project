package com.example.FinalProjectWAPRevised.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.model.Customer;
import com.example.FinalProjectWAPRevised.model.userForm;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;
import com.example.FinalProjectWAPRevised.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LibraryController {

    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;

    public LibraryController(ItemRepository itemRepository, CustomerRepository customerRepository){
        this.itemRepository=itemRepository;
        this.customerRepository = customerRepository;
    }

    //Buy Item
    @PostMapping("bought")
    public String boughtItem(@RequestParam Long itemId, Model model) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setBought(true); // error here

            //update item with new isBought value
            itemRepository.save(item);
        }


        //makes sure to return to the the home page istead of having them sign in again
        model.addAttribute("username", "User");

        //reloads the items to the home page
        loadItems(model);

        return "home";
    }


    @PostMapping("/return")
    public String returnItem(@RequestParam Long itemId, Model model){
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item != null){
            item.setBought(false); // Error Here
            itemRepository.save(item);
        }

        model.addAttribute("username", "User");
        loadItems(model);


        return "home";
    }

    //loads items into the available and unavialable items slot in home
    private void loadItems(Model model) {
        List<Item> allItems = itemRepository.findAll();
        model.addAttribute("availableItems",
                allItems.stream().filter(item -> !item.isBought()).toList());
        model.addAttribute("boughtItems",
                allItems.stream().filter(Item::isBought).toList());
    }


    //-----------------------------LOGIN-----------------------------
    // login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    // logging in
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model) {
    
        // Find user by name and password from repository
        Customer customer = customerRepository.findAll().stream()
                .filter(u -> u.getName().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null); //if user doesn't exist return null


        if (customer!=null){
            model.addAttribute("username",customer.getName());

            // return a list of items if isBought is false to the model
            model.addAttribute("availableitems",
                    itemRepository.findAll().stream().filter(item-> !item.isBought()).toList());

            // return a list of items if isBought is true to the model
            model.addAttribute("boughtItems",
                    itemRepository.findAll().stream().filter(Item::isBought).toList());
            return "store";
        }

        return "login";
    }


    //-----------------------------REGISTER-----------------------------
    // register page
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("form", new userForm());
        return "register";
    }
    
    // register form submission
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("form") userForm form,
                                  BindingResult bindingResult,
                                  Model model) {

        if(bindingResult.hasErrors()){
            return "register";
        }

        if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("passwordError", "Passwords do not match.");
            return "register";
        }

        Customer user = new Customer(form.getName(), form.getEmail(), form.getPassword());
        customerRepository.save(user);
        
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // This clears all session attributes
        return "redirect:/login"; // Redirect to login page
    }

}  


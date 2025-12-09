package com.example.FinalProjectWAPRevised.controller;

import java.security.Principal;
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

    //-----------------------------STORE-----------------------------
    @GetMapping("/store")
    public String showStore(HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if(customer == null){
            return "login";
        }

        model.addAttribute("customer", customer);
        loadItems(model);

        return "store";
    }



    //-----------------------------CHECKOUT-----------------------------
    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if(customer == null){
            return "login";
        }

        model.addAttribute("customer", customer);
        return "checkout";
    }

    //-----------------------------PROFILE-----------------------------
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");

        if(customer == null){
            return "login";
        }

        model.addAttribute("customer", customer);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // This clears all session attributes
        return "login"; // Redirect to login page
    }


    //-----------------------------BASKET-----------------------------
    @GetMapping("/basket")
    public String showBasket(HttpSession session, Model model){
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        

        if(customer == null){
            return "login";
        }


        customer = customerRepository.findByUsername(customer.getUsername());
        System.out.println("HELLO"+customer.getBasket().size()); //forces basket to load


        model.addAttribute("customer", customer);
        model.addAttribute("basket", customer.getBasket());

        return "basket";
    }

    //--Add to basket--
    @PostMapping("/basket/add")
    public String addToBasket(@RequestParam Long itemId, HttpSession session) {
        Customer sessionCustomer = (Customer) session.getAttribute("loggedInCustomer");
        if (sessionCustomer == null) return "login";

        Customer customer = customerRepository.findById(sessionCustomer.getId()).orElseThrow();

        Item item = itemRepository.findById(itemId).orElseThrow();

        if (!customer.getBasket().contains(item)) {
            customer.getBasket().add(item);
            customerRepository.save(customer);
        }

        session.setAttribute("loggedInCustomer", customer);
        return "redirect:/store";
    }



    //--Remove from basket--
@PostMapping("/basket/remove")
public String removeFromBasket(@RequestParam Long itemId, HttpSession session){
    Customer sessionCustomer = (Customer) session.getAttribute("loggedInCustomer");

    if(sessionCustomer == null){
        return "login";
    }

    // Fetch the customer from DB with basket initialized
    Customer customer = customerRepository.findById(sessionCustomer.getId())
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    // Remove item
    customer.getBasket().removeIf(item -> item.getId().equals(itemId));

    // Save changes
    customerRepository.save(customer);

    // Update session
    session.setAttribute("loggedInCustomer", customer);

    return "redirect:/basket";
}

    //-----------------------------LOGIN-----------------------------
    // login page
    @GetMapping("/")
    public String showLoginForm(){
        return "login";
    }
    
    // logging in
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
    
        // Find user by name and password from repository
        Customer customer = customerRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null); //if user doesn't exist return null

        if (customer!=null){
            session.setAttribute("loggedInCustomer", customer);

            model.addAttribute("username",customer.getUsername());

            // return a list of items if isBought is false to the model
            model.addAttribute("availableItems",
                    itemRepository.findAll().stream().filter(item-> !item.isBought()).toList());

            return "store";
        }else{
            String errorMessage = "Invalid Login";
            model.addAttribute("loginError", errorMessage);
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

        if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("passwordError", "Passwords do not match.");
            bindingResult.rejectValue("confirm", "userForm Error -> Confirm Password", "Passwords do not match");
            return "register";
        }

        if(bindingResult.hasErrors()){
            return "register";
        }

        Customer user = new Customer(form.getName(), form.getEmail(), form.getPassword());
        customerRepository.save(user);
        
        return "login";
    }





    //////
    ///     //Buy Item
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

        return "store";
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

        return "store";
    }

    //loads items into the available and unavialable items slot in home
    private void loadItems(Model model) {
        List<Item> allItems = itemRepository.findAll();
        model.addAttribute("availableItems",
                allItems.stream().filter(item -> !item.isBought()).toList());
    }
}  


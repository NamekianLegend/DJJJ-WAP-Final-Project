package com.example.FinalProjectWAPRevised.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FinalProjectWAPRevised.model.Customer;
import com.example.FinalProjectWAPRevised.model.userForm;
import com.example.FinalProjectWAPRevised.service.CustomerService;
import com.example.FinalProjectWAPRevised.service.ItemService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class StoreController {

    private final ItemService itemService;
    private final CustomerService customerService;

    public StoreController(ItemService itemService, CustomerService customerService){
        this.itemService = itemService;
        this.customerService = customerService;
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

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String address,
                              @RequestParam String card,
                              HttpSession session,
                              Model model) {
    System.out.println("✅ Checkout POST triggered");

    Customer sessionCustomer = (Customer) session.getAttribute("loggedInCustomer");
    if (sessionCustomer == null) {
        return "login";
    }

    // Optional: finalize order
   // customerService.checkout(sessionCustomer.getId(), address, card);

    // Update session
    Customer updatedCustomer = customerService.showBasket(sessionCustomer.getUsername());
    session.setAttribute("loggedInCustomer", updatedCustomer);

    return "redirect:/orderSuccess";
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
        // this changes to sessionCustomer, we use it just to verify the session
        Customer sessionCustomer = (Customer) session.getAttribute("loggedInCustomer");
        

        if(sessionCustomer == null){
            return "login";
        }

        // we get an actual customer object from the database using the service
        // feed the sessionCustomer username into the method to use it in querying the db
        Customer customer = customerService.showBasket(sessionCustomer.getUsername());


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

        Customer customer = customerService.addItemToBasket(sessionCustomer.getId(), itemId);

        session.setAttribute("loggedInCustomer", customer);
        return "redirect:/basket";
    }



    //--Remove from basket--
@PostMapping("/basket/remove")
public String removeFromBasket(@RequestParam Long itemId, HttpSession session){
    Customer sessionCustomer = (Customer) session.getAttribute("loggedInCustomer");

    if(sessionCustomer == null){
        return "login";
    }

    // function that fetches the customer, removes the item, then saves changes in the db
    Customer customer = customerService.removeItemFromBasket(sessionCustomer.getId(), itemId);

    // Update session
    session.setAttribute("loggedInCustomer", customer);

    return "redirect:/basket";
}

@GetMapping("/orderSuccess")
public String showOrderSuccess(HttpSession session, Model model){
    Customer customer = (Customer) session.getAttribute("loggedInCustomer");

    if(customer == null){
        return "login";
    }

    model.addAttribute("customer", customer);
    return "orderSuccess";
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

        Customer customer = customerService.authenticate(username, password);

        if (customer!=null){
            session.setAttribute("loggedInCustomer", customer);
            model.addAttribute("username", customer.getUsername());

            // return a list of items if isBought is false to the model
            model.addAttribute("availableItems", itemService.findAvailableItems());

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

        customerService.registerCustomer(form);
        
        return "login";
    }





    //-----------------------------ITEMS-----------------------------
    @PostMapping("bought")
    public String boughtItem(@RequestParam Long itemId, Model model) {
        itemService.buyItem(itemId);

        //makes sure to return to the home page istead of having them sign in again
        model.addAttribute("username", "User");

        //reloads the items to the home page
        loadItems(model);

        return "redirect:/basket";
    }


    @PostMapping("/return")
    public String returnItem(@RequestParam Long itemId, Model model){
        itemService.returnItem(itemId);

        model.addAttribute("username", "User");
        loadItems(model);

        return "store";
    }

    //loads items into the available and unavialable items slot in home
    private void loadItems(Model model) {
        model.addAttribute("availableItems", itemService.findAvailableItems());
    }
}  


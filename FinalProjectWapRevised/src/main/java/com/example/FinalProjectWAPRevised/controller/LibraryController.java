package com.example.FinalProjectWAPRevised.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.model.User;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;
import com.example.FinalProjectWAPRevised.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LibraryController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public LibraryController(ItemRepository itemRepository, UserRepository userRepository){
        this.itemRepository=itemRepository;
        this.userRepository = userRepository;
    }

// logging in
@PostMapping("/login")

public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
// finally figured out what I was doing wrong and its 12:43 AM, why am i so slow to figure this stuff out!
List<User> users = userRepository.findAll();
for(User user: users){
    if (user.getEmail().equals(email)&&user.getPassword().equals(password)){
        model.addAttribute("username",user.getName());

        // return a list of items if isBought is false to the model
        model.addAttribute("availableitems",
        itemRepository.findAll().stream().filter(item-> !item.isBought()).toList());

        // return a list of items if isBought is true to the model
        model.addAttribute("boughtItems",
        itemRepository.findAll().stream()
        .filter(Item::isBought) // not sure what happend here
        .toList());
        return "home";
        

        }
    }
    return "login";
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

    // login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // register page
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // register form submission
    @PostMapping("/register")
    public String processRegister(@Valid @RequestParam String name,
            @Valid @RequestParam String email,
            @Valid @RequestParam String password, @Valid @RequestParam String confirm,
            Model model) {
        if(password.equals(confirm)){
            User tempUser = new User(name,password);// Error Here
            
            userRepository.save(tempUser);
            System.out.println("\nUSER REGISTRATION");
            tempUser.displayDetails();
            return "login";
        }
        
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // This clears all session attributes
        return "redirect:/login"; // Redirect to login page
    }

}  


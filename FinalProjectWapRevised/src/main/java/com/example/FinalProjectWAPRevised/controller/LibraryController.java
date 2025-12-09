package com.example.FinalProjectWAPRevised.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.model.User;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;
import com.example.FinalProjectWAPRevised.repository.UserRepository;

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


}  


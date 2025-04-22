package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller

public class RegisterAccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/RegisterAccounts")//A simple form page similar to make accounts but for other users (Will add to databse)
    public String showRegisterAccountsPage(Model model) {
        model.addAttribute("user", new users());
        return "registerAdmin";
    }

    @PostMapping("/RegisterUser")
    public String AddUser(@ModelAttribute users user,
                          @RequestParam String username, Model model) {
        if (userService.register(username)) {
            userRepository.save(user);
            return "Dash";
        } else {
            model.addAttribute("error", "User already exists");
            model.addAttribute("user", user);//Need this brcasue if the account already exists the form keeps the crap that was fileld in
            return "registerAdmin";

        }
    }
}

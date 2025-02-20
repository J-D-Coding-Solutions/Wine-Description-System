package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    // Handle GET request for login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This should resolve to the login.html template
    }

    // Handle POST request for form submission (login)
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password, Model model) {
        // Authenticate using UserService
        if (userService.authenticate(username, password)) {
            // If authentication is successful, redirect to welcome page
            return "redirect:/welcome?username=" + username; // Redirect with username
        } else {
            // If authentication fails, return to the login page with an error message
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Re-render the login page with the error
        }
    }

    @GetMapping("/RegisterPage")
    public String DisplayRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/AddUser")
    public String AddUser(@ModelAttribute User user,
                            @RequestParam String username, Model model) {
        if (userService.register(username)) {
            userRepository.save(user);
            return "login";
        } else {
            model.addAttribute("error", "User already exists");
            return "Register";

        }
    }
    @GetMapping("/welcome")
    public String Welcome(@RequestParam String username, Model model) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            model.addAttribute("Username", user.getUsername());
        } else {
            model.addAttribute("error", "User not found");
        }
    return "/welcome";
    }


















}
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
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    //Also need to make designated error page to redirect
    //Need to make dash its own controller & be able to pull data from favorite wines
    // Handle GET request for login page
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // This should resolve to the login.html template
    }

    // Handle POST request for form submission (login)
    @PostMapping("/")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        String role = userService.authenticate(username, password);

        if (role == null) {
            return "redirect:/"; // Re,direct back to log in if authentication fails
        }

        // Store role in session
        session.setAttribute("userRole", role);

        return "redirect:/" + "Dash"; // Redirect to appropriate dashboard
    }

    @GetMapping("/RegisterPage")//Need to make individual controller
    public String DisplayRegisterPage(Model model) {
        model.addAttribute("user", new users());
        return "Register";
    }

    @PostMapping("/AddUser")//Need to make individual controller
    public String AddUser(@ModelAttribute users user,
                            @RequestParam String username, Model model) {
        if (userService.register(username)) {
            user.setRole("USER");
            userRepository.save(user);
            return "login";
        } else {
            model.addAttribute("error", "User already exists");
            return "Register";

        }
    }
    @GetMapping("/Dash")
    public String welcome(@RequestParam(name = "username", required = false) String username, Model model) {
        if (username != null) {
            users user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                model.addAttribute("username", user.getUsername());
            } else {
                model.addAttribute("error", "User not found");
            }
        } else {
            model.addAttribute("error", "Username not provided");
        }

        return "Dash"; // Thymeleaf template name
    }

}
package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import jakarta.persistence.Id;
import org.apache.catalina.User;
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
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println(userRepository.findByUsername(password));
        String role = userService.authenticate(username, password);

        if (role == null) {
            return "redirect:/"; // Re,direct back to log in if authentication fails
        }


        // Store role in session
        session.setAttribute("username", username);
        session.setAttribute("userRole", role);
        //session.setAttribute("user_id", user_id);

        //print out username in cosnole
        System.out.println("Logged in user: " + username);
        System.out.println("Logged in user role: " + role);

        return "redirect:/" + "Dash"; // Redirect to appropriate dashboard
    }


}
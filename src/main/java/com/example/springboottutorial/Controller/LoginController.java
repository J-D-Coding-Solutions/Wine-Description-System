package Controller;

import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Handle GET request for login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login"; // This should resolve to the login.html template
    }

    // Handle POST request for form submission (login)
   /* @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password, Model model) {
        // Authenticate using UserService
        if (userService.authenticate(username, password)) {
            // If authentication is successful, redirect to welcome page
            return "welcome"; // This should resolve to the welcome.html template
        } else {
            // If authentication fails, return to the login page with an error message
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Re-render the login page with the error
        }
    }*/
}

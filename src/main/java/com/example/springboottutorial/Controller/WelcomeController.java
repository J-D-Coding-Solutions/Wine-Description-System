package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/friendPage")
    public String friendPage(@RequestParam(name = "username", required = false) String username, Model model) {
        if (username != null) {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                model.addAttribute("username", user.getUsername());
            } else {
                model.addAttribute("error", "User not found");
            }
        } else {
            model.addAttribute("error", "Username not provided");
        }

        return "Friends";
    }

    @GetMapping("/AdminDash")
    public String showAdminPage() {
        return "welcome-ADMIN"; // This should resolve to the login.html template
    }

    @GetMapping("/SocialPage")
    public String showSocialPage() {
        return "Friends"; // This should resolve to the login.html template
    }

    @GetMapping("/GuestDash")
    public String showGuestPage() {
        return "welcome-GUEST"; // This should resolve to the login.html template
    }

    @GetMapping("/ModeratorDash")
    public String showModeratorPage() {
        return "welcome-MODERATOR"; // This should resolve to the login.html template
    }

    @GetMapping("/AddFriends")
    public String showFriendsPage() {
        return "registerFriend"; // This should resolve to the login.html template
    }

    @GetMapping("/SommelierDash")
    public String showSomlierPage() {
        return "welcome-SOMMELIER"; // This should resolve to the login.html template
    }

    @GetMapping("/StakeholderDash")
    public String showStakeholderPage() {
        return "welcome-STAKEHOLDER"; // This should resolve to the login.html template
    }

    @GetMapping("/WineRequest")
    public String showRequestPage() {
        return "Wine-request"; // This should resolve to the login.html template
    }


}

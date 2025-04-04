package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
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
    //this does not use any of the fuctionality below because there is no error model made in the html page
    public String friendPage(@RequestParam(name = "username", required = false) String username, Model model) {
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

        return "Friends";
    }


    @GetMapping("/WineRequest")
    public String showRequestPage() {
        return "Wine-request"; // This should resolve to the login.html template
    }

    @GetMapping("/FriendRequest")
    public String showFriendRequestPage() {
        return "registerFriend";
    }

    @GetMapping("/ReportBuggies")
    public String showReportBuggiesPage() {
        return "reportBug";
    }

    @GetMapping("/RegisterAccounts")
    public String showRegisterAccountsPage() {
        return "registerAdmin";
    }

    @GetMapping("/WineSearch")
    public String showWineSearchPage() {
        return "WineSearch";
    }

    @GetMapping("/AdjustProfiles")
    public String showAdjustProfilesPage() {
        return "adjustProfile";
    }

    @GetMapping("/ViewBugs")
    public String showBugsPage() {
        return "viewBugReport";
    }

    @GetMapping("/WineRequests")
    public String showWineRequestsPage() {
        return "RequestedWines";
    }

}

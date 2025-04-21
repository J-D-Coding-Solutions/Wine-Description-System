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

    @GetMapping("/friendPage")//DONE DO NOT DO
    public String showFriendPage() {return "Friends";
    }





    @GetMapping("/FriendRequest")// DONE DO NOT DO
    public String showFriendRequestPage() {
        return "registerFriend";
    }

    @GetMapping("/WineSearch")//This just gets the wine search page and cannot move from here and wont work in the wine search controller because it is a rest controller
    public String showWineSearchPage() {
        return "wineSearch";
    }

    @GetMapping("/AdjustProfiles")//Will show all current Users and let the mod delete account or adjust info (Will display info and edit info in databse)
    public String showAdjustProfilesPage() {
        return "adjustProfile";
    }

    @GetMapping("/ViewBugs")//Will show all current reported bugs from users and mod can accept them to work or decline (Will display info and edit info in databse)
    public String showBugsPage() {
        return "viewBugReport";
    }

    @GetMapping("/WineRequests")//Will be same as friends page but show current wines in winelist (Load by 10-20) and show requests from users (Will display and edit info in database)
    public String showWineRequestsPage() {
        return "RequestedWines";
    }

}

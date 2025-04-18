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
public class FriendPageController {

    @GetMapping("/friendPage")//Will show currecnt friends, have other infromation pulled each time a user is clicked and show current requests (Will display info from database & Edit info in database)
    public String showFriendPage() {return "Friends";
    }


}

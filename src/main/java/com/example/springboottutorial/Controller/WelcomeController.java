package com.example.springboottutorial.Controller;

/**
 * WelcomeController.java
 * This Class is responsible fro displaying the Wine Search page it is not included in the winesreach crontroller because that is a rest controller
 */

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


    @GetMapping("/WineSearch")//This just gets the wine search page and cannot move from here and wont work in the wine search controller because it is a rest controller
    public String showWineSearchPage() {

        return "wineSearch";
    }


}



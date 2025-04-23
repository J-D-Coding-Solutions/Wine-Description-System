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
public class AdjustProfilesController {

    @GetMapping("/AdjustProfiles")//Will show all current Users and let the mod delete account or adjust info (Will display info and edit info in databse)
    public String showAdjustProfilesPage() {
        return "adjustProfile";
    }

}

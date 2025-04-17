package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

@Controller
public class WineRequestController {

    @Autowired
    private WineRequestRepository wineRequestRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/WineRequest")//Where users ask the somolier to look at the wine (Will add to databse)
    public String showRequestPage(Model model) {
        model.addAttribute("Wine_Request", new WineRequests()); //makes a new object of the wine request
        return "Wine-request";
    }


    @PostMapping("/Request")
    public String RequestWine(@ModelAttribute("Wine_Request") WineRequests request, HttpSession session) {

        //this sets username as a strign and grabs from the session
        String sessionusername = (String) session.getAttribute("username");
        //so currenltt its a string and needs to be an object to store

        users username = userRepository.findByUsername(sessionusername).orElse(null);
        //So this not only makes sure its in the database but also makes sure its not null and sets it as a user object


        //This then sets the user object to the request and saves it to the database
        request.setUser(username);
        wineRequestRepository.save(request);
        return "dash"; // Redirect to a success page or dashboard after saving
    }
}

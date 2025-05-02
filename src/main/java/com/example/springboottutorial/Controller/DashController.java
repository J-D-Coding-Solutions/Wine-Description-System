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

import java.util.ArrayList;
import java.util.List;


@Controller
public class DashController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteWineRepository favoriteWineRepository;
    @Autowired
    private WineRepository wineRepository;

    @GetMapping("/Dash")
    //This is doing nothing lol
    public String welcome(@RequestParam(name = "username", required = false) String username, Model model, Model wines, HttpSession session) {
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

        String sessionusername = (String) session.getAttribute("username");
        users currentUser = userRepository.findByUsername(sessionusername).orElse(null);

        List<favoriteWines> favWines = favoriteWineRepository.findAllByUser(currentUser);
        List<wines> wineList = new ArrayList<>();

        for (favoriteWines fav : favWines) {
            wineList.add(fav.getWine());
        }
        wines.addAttribute("wineList", wineList);

        return "Dash"; // Thymeleaf template name
    }

}

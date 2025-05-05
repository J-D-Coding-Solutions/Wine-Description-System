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
    public String welcome(@RequestParam(name = "username", required = false) String username, Model model, HttpSession session) {
        users user = null;

        if (username != null) {
            user = userRepository.findByUsername(username).orElse(null);
        } else {
            String sessionUsername = (String) session.getAttribute("username");
            if (sessionUsername != null) {
                user = userRepository.findByUsername(sessionUsername).orElse(null);
            }
        }

        if (user == null) {
            model.addAttribute("error", "User not found or not logged in.");
            return "Dash";
        }

        model.addAttribute("user", user);

        List<favoriteWines> favWines = favoriteWineRepository.findAllByUser(user);
        List<wines> wineList = new ArrayList<>();
        for (favoriteWines fav : favWines) {
            wineList.add(fav.getWine());
        }
        model.addAttribute("wineList", wineList);

        return "Dash";
    }
}

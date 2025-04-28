package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdjustProfilesController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/AdjustProfiles")//Will show all current Users and let the mod delete account or adjust info (Will display info and edit info in databse)
    public String showAdjustProfilesPage(Model model, HttpSession session) {
        List<users> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "adjustProfile";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/AdjustProfiles";
    }

}

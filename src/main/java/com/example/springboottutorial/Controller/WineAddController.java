package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WineAddController {

    @Autowired
    private WineRepository wineRepository;

    @GetMapping("/RegisterWines")
    public String showAddWinePage(Model model) {
        model.addAttribute("wine", new wines());
        return "registerWine";
    }

    @PostMapping("/Addwine")
    public String Addwine(@ModelAttribute wines wine) {
        wineRepository.save(wine);
        return "dash";
    }
}

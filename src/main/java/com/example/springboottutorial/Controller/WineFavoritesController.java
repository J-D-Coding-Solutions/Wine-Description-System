package com.example.springboottutorial.Controller;


import com.example.springboottutorial.Model.favoriteWines;
import com.example.springboottutorial.Model.users;
import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.FavoriteWineRepository;
import com.example.springboottutorial.Repository.FriendRequestRepository;
import com.example.springboottutorial.Repository.UserRepository;
import com.example.springboottutorial.Repository.WineRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class WineFavoritesController {

    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private FavoriteWineRepository favoriteWineRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addFavorite")
    public String favoriteWine(@RequestParam String winename, HttpSession session){
        Optional<wines> wine = wineRepository.findByWineName(winename);
        String sessionusername = (String) session.getAttribute("username");
        users currentUser = userRepository.findByUsername(sessionusername).orElse(null);
        if(wine.isPresent()){
            favoriteWines favWine = new favoriteWines(currentUser,wine.get());
            if(favoriteWineRepository.existsByUserAndWine(currentUser, wine.get())){
                System.out.println("ALREADY FAVORITE");
            }
            else {
                favoriteWineRepository.save(favWine);
            }
        }
        else{
            System.out.println("WINE NOT FOUND");
        }
        return "dash";
    }

    @PostMapping("/removeFavorite/{wineId}")
    @Transactional
    public String removeFavorite(@PathVariable long wineId, HttpSession session) {
        wines wine = wineRepository.getById(wineId);
        String sessionusername = (String) session.getAttribute("username");
        users currentUser = userRepository.findByUsername(sessionusername).orElse(null);

        favoriteWineRepository.deleteByWineAndUser(wine, currentUser);
        return "redirect:/Dash";
    }
}


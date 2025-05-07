package com.example.springboottutorial.Controller;

/**
 * FriendRequestController.java
 * This class handles the friend request functionality in the application.
 * It allows users to send friend requests to other users.
 * It uses the FriendRequestRepository to manage friend requests
 */

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.sun.istack.NotNull;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



@Controller
public class FriendRequestController {

    @Autowired
    private FriendRequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFriendRepository userFriendRepository;

    @GetMapping("/FriendRequest")
    public String showFriendRequestPage(Model model) {

        model.addAttribute("friendRequest", new users());
        return "registerFriend";
    }

    @PostMapping("/requestFriend")
    public String requestFriend(@ModelAttribute users friendRequest, HttpSession session) {
        System.out.println("TESTING FRIEND REQUEST");
        String sessionusername = (String) session.getAttribute("username");
        //So this not only makes sure its in the database but also makes sure its not null and sets it as a user object
        users currentUser = userRepository.findByUsername(sessionusername).orElse(null);
        users friendUser = userRepository.findByUsername(friendRequest.getUsername()).orElse(null);

        if((currentUser != null && friendUser != null) & currentUser != friendUser){
        boolean friendAlready = userFriendRepository.existsByUserAndFriend(currentUser, friendUser);
        boolean friendAlready1 = userFriendRepository.existsByUserAndFriend(friendUser, currentUser);

        if(!friendAlready && !friendAlready1){
        boolean exists = requestRepository.existsBySenderAndFriend(currentUser, friendUser);
        boolean exists1 = requestRepository.existsBySenderAndFriend(friendUser, currentUser);

        System.out.println(exists);
        System.out.println(exists1);

        if (!exists & !exists1) {
            System.out.println("SENDING FRIEND REQUEST");
            friendRequest request = new friendRequest(currentUser, friendUser);

            requestRepository.save(request);
        }
        }}
        return "redirect:/FriendRequest";
    }

}

package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class FriendPageController {

    @Autowired
    private UserFriendRepository userFriendRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRequestRepository requestRepository;

    @GetMapping("/friendPage")//Will show currecnt friends, have other infromation pulled each time a user is clicked and show current requests (Will display info from database & Edit info in database)
    public String showFriendPage(Model model, HttpSession session) {
        //Gets All Friends in the Friend List Database, This is Temporary. i need to find all instances of the user in the Database

        //Gets the Current User's username
        String sessionusername = (String) session.getAttribute("username");
        //So this not only makes sure its in the database but also makes sure its not null and sets it as a user object
        users currentUser = userRepository.findByUsername(sessionusername).orElse(null);
        assert currentUser != null;
        Long curentUserId = currentUser.getId();

        List<userFriend> allFriends = userFriendRepository.findAllByUserIdOrFriendId(curentUserId);

        //Makes sure the Current user is the user in the friend request and the friend is the friend
        for(userFriend friend : allFriends){
            if(!friend.getUser().getId().equals(curentUserId)){
                users temp = friend.getFriend();
                friend.setFriend(friend.getUser());
                friend.setUser(temp);
            }
        }

        List<friendRequest> requests = requestRepository.findAllByfriend(currentUser);

        model.addAttribute("requests", requests);
        model.addAttribute("userFriend", allFriends);//this needs to be filled out once the model is done
        return "Friends";
    }

    @PostMapping("/accept/{id}")
    public String accept(@PathVariable Long id) {
        userFriend friend = new userFriend();
        friendRequest request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("request not found"));

        System.out.println(request.getId());
        System.out.println(friend.getId());

        friend.setUser(request.getSender());
        friend.setFriend(request.getFriend());

        System.out.println(friend.getUser().getId());
        System.out.println(friend.getFriend().getId());

        if (request.getSender().getId() >= request.getFriend().getId()) {
            friend.setUser(request.getFriend());
            friend.setFriend(request.getSender());
        }

        System.out.println(friend.getUser());
        System.out.println(friend.getFriend());

        userFriendRepository.save(friend);
        requestRepository.delete(request);
        return "redirect:/friendPage";
    }

    @PostMapping("/decline/{id}")
    public String decline(@PathVariable Long id) {

        requestRepository.deleteById(id);

        return "redirect:/friendPage";
    }
}

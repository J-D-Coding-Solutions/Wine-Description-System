package com.example.springboottutorial.Service;

import com.example.springboottutorial.Model.User;
import com.example.springboottutorial.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }
    public boolean register(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isEmpty(); // Returns true if the user does not exist, false if it does
    }

}

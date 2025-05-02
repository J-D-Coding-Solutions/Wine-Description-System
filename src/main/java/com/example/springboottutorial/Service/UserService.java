package com.example.springboottutorial.Service;

import com.example.springboottutorial.Encryption.PassEncryption;
import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String username, String password) {
        Optional<users> user = userRepository.findByUsername(username);

//        if (user.isPresent() && user.get().getPassword().equals(password)) {
//            return user.get().getRole(); // Return role if authentication is successful
//        }
//        return null; // Authentication failed
            if (user.isPresent() && PassEncryption.CheckPass(password, user.get().getPassword())) {
                System.out.println("Logged in user: " + password);
                return user.get().getRole();
        }
            //System.out.println(user.get().getPassword());
        return null;
    }
    public boolean register(String username) {
        Optional<users> user = userRepository.findByUsername(username);
        return user.isEmpty(); // Returns true if the user does not exist, false if it does
    }

}

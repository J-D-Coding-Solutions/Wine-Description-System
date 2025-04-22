package com.example.springboottutorial.Service;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private UserFriendRepository userFriendRepository;


}

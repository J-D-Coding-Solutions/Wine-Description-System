package com.example.springboottutorial.Service;

import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WineService {

    @Autowired
    private WineRepository wineRepository;
}

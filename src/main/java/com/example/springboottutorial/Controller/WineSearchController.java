package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springboottutorial.Controller.DTO.WineSearchRequest;

@RestController
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest Search) {
        String UserSearch = Search.getDescription();
        return ResponseEntity.ok("It Works!!! " + UserSearch);
    }
}

package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
@Controller
public class BugReportController {

    @Autowired
    private BugReportRepository bugReportRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ReportBuggies")//A simple form page that takes users bug reports (Will add to databse)
    public String showReportBuggiesPage(Model model) {
        model.addAttribute("Bug_Report", new BugReport());//this needs to be filled out once the model is done
        return "reportBug";
    }

    @PostMapping("/ReportBug")
    public String addBugReport(@ModelAttribute("Bug_Report") BugReport bugReport, HttpSession session) {

        //this sets username as a strign and grabs from the session
        String sessionusername = (String) session.getAttribute("username");
        //so currenltt its a string and needs to be an object to store

        users username = userRepository.findByUsername(sessionusername).orElse(null);
        //So this not only makes sure its in the database but also makes sure its not null and sets it as a user object

        bugReport.setUser(username);
        bugReportRepository.save(bugReport);
        return "dash"; // Redirect to a success page or dashboard after saving
    }



}

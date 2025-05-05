package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ViewBugsController {

    @Autowired
    private BugReportRepository bugReportRepository;

    @GetMapping("/ViewBugs")//Will show all current reported bugs from users and mod can accept them to work or decline (Will display info and edit info in databse)
    public String showBugsPage(Model model) {
        List<BugReport> BugReport = bugReportRepository.findAll();
        model.addAttribute("BugReport", BugReport);
        return "viewBugReport";
    }



}

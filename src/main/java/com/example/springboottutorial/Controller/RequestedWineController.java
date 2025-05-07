package com.example.springboottutorial.Controller;

/**
 * RequestedWineController.java
 * This Class is responsible for handling the requests related to wine requests.
 * It allows the admin and sommeliers to view all wine requests and decline them.
 *
 */

import com.example.springboottutorial.Model.WineRequests;
import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.WineRepository;
import com.example.springboottutorial.Repository.WineRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RequestedWineController {

    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private WineRequestRepository wineRequestRepository;

    @GetMapping("/WineRequests")
    public String showAddWinePage(Model model) {

        List<wines> allWine = wineRepository.findAll();
        List<WineRequests> allRequest = wineRequestRepository.findAll();

        model.addAttribute("Wines", allWine);
        model.addAttribute("Requests", allRequest);
        return "RequestedWines";
    }

    //Do we want to give the admin the ability to accept the pure users description? Or for the somilier to add their own details
//    @PostMapping("/accept/{id}")
//    public String accept(@PathVariable Long id) {
//        wineRepository.save()
//
//        return "redirect:/WineRequests";
//    }

//    <td>  <form th:action="@{'/accept/' + ${request.getId()} }" method="post">
//            <button class="accept">Accept</button>
//          </form>
//      </td>

    @PostMapping("/declineWine/{id}")
    public String accept(@PathVariable Long id) {

        wineRequestRepository.deleteById(id);
        return "redirect:/WineRequests";
    }
}


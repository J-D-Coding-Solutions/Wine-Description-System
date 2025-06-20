package com.example.springboottutorial.Controller;

/**
 * WineAddController.java
 * The Class is resposnible for handling the requests related to adding wines.
 * It contains methods to show the add wine page and to handle the form submission for adding a new wine.
 */

import com.example.springboottutorial.Model.arff;
import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.WineRepository;
import com.example.springboottutorial.Repository.arffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WineAddController {
    private final NLPController nlpService;

    // Constructor injection

    public WineAddController(NLPController nlpService) {
        this.nlpService = nlpService;
    }
    @Autowired
    private WineRepository wineRepository;
    @Autowired
    private arffRepository arffRepository;


    @GetMapping("/RegisterWines")
    public String showAddWinePage(Model model) {
        model.addAttribute("wine", new wines());
        return "registerWine";
    }

    @PostMapping("/Addwine")
    public String Addwine(@ModelAttribute wines wine) {
        wineRepository.save(wine);



       // NLPController NLPController = new NLPController();
        ModelController modelController = new ModelController(arffRepository);

        List<String> userKeyWords =  nlpService.keyWordList(nlpService.NLP(wine.getWineDesc() + " " + wine.getProvince()+ " " + wine.getCountry() + " " + wine.getRegion() + " " + wine.getVariety() + " " + wine.getWinery()));
        List<String> predictedKeyWord = modelController.predictKeywordType(userKeyWords);
        List<Double> predictedWeights = modelController.predictWeights(userKeyWords);

        modelController.addArff(userKeyWords, predictedKeyWord, predictedWeights);
        modelController.createArff();
        modelController.trainModel();

        return "redirect:/RegisterWines";
    }
}

package com.example.springboottutorial.Controller;

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

        NLPController NLPController = new NLPController();
        ModelController modelController = new ModelController(arffRepository);

        List<String> userKeyWords =  NLPController.keyWordList(NLPController.NLP(wine.getWineDesc() + " " + wine.getProvince()+ " " + wine.getCountry() + " " + wine.getRegion() + " " + wine.getVariety() + " " + wine.getWinery()));
        List<String> predictedKeyWord = modelController.predictKeywordType(userKeyWords);
        List<Double> predictedWeights = modelController.predictWeights(userKeyWords);

        for(int i = 0; i < userKeyWords.size(); i++){
            String[] parts = userKeyWords.get(i).split(",");

// Extract each variable from the parts array
            String extractedLemma = parts[0].replace("\"", "");  // Remove the quotes around the lemma
            String extractedPos = parts[1];
            int extractedWordLength = Integer.parseInt(parts[2]);
            String extractedContainsDigit = parts[3];
            String extractedPrevWordPOS = parts[4];
            String extractedNextPos = parts[5];
            String extractedIsContextuallyDescriptive = parts[6];
            String extractedSuffix = parts[7];

            arff arff = new arff(extractedLemma, extractedPos, extractedWordLength, extractedContainsDigit, extractedPrevWordPOS, extractedNextPos, extractedIsContextuallyDescriptive, extractedSuffix, predictedKeyWord.get(i), predictedWeights.get(i));
            arffRepository.save(arff);
        }

        modelController.createArff();
        modelController.trainModel();

        return "dash";
    }
}

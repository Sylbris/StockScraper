package io.tracker.webscraper.controllers;

import io.tracker.webscraper.services.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    StockDataService stockDataService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("stockStats", stockDataService.getAllStats());
        return "home";
    }
}

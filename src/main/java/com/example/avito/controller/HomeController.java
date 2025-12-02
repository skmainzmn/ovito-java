package com.example.avito.controller;

import com.example.avito.service.AdService;
import com.example.avito.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AdService adService;
    private final CategoryService categoryService;

    public HomeController(AdService adService, CategoryService categoryService) {
        this.adService = adService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ads", adService.getAllAds());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}

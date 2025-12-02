package com.example.avito.controller;

import com.example.avito.service.AdService;
import com.example.avito.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final AdService adService;
    private final CategoryService categoryService;

    public HomeController(AdService adService, CategoryService categoryService) {
        this.adService = adService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) Long categoryId,
                        @RequestParam(required = false) String city,
                        Model model) {

        // если есть хоть один фильтр – ищем, иначе показываем все
        if ((q != null && !q.isBlank())
                || categoryId != null
                || (city != null && !city.isBlank())) {
            model.addAttribute("ads", adService.searchAds(q, categoryId, city));
        } else {
            model.addAttribute("ads", adService.getAllAds());
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("q", q);
        model.addAttribute("cityFilter", city);
        model.addAttribute("categoryId", categoryId);

        return "index"; // шаблон index.html
    }
}
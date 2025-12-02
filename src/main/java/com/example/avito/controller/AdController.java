package com.example.avito.controller;

import com.example.avito.entity.Ad;
import com.example.avito.service.AdService;
import com.example.avito.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;

    public AdController(AdService adService,
                        CategoryService categoryService) {
        this.adService = adService;
        this.categoryService = categoryService;
    }

    // список всех объявлений
    @GetMapping
    public String listAds(Model model) {
        model.addAttribute("ads", adService.getAllAds());
        model.addAttribute("categories", categoryService.findAll());
        return "ads/list"; // ads/list.html
    }

    // просмотр одного объявления
    @GetMapping("/{id}")
    public String viewAd(@PathVariable Long id, Model model) {
        Ad ad = adService.getById(id);
        model.addAttribute("ad", ad);
        return "ads/view"; // ads/view.html
    }

    // форма создания объявления
    @GetMapping("/new")
    public String newAdForm(Model model) {
        model.addAttribute("ad", new Ad());
        model.addAttribute("categories", categoryService.findAll());
        return "ads/new"; // ads/new.html
    }

    // 🔥 обработка отправки формы
    @PostMapping
    public String createAd(@ModelAttribute Ad ad,
                           @RequestParam(required = false) Long categoryId) {
        adService.createAd(ad, categoryId);
        return "redirect:/ads";
    }
}
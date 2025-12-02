package com.example.avito.controller;

import com.example.avito.entity.Ad;
import com.example.avito.service.AdService;
import com.example.avito.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final CategoryService categoryService;

    public AdController(AdService adService, CategoryService categoryService) {
        this.adService = adService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listAds(@RequestParam(required = false) String q,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) BigDecimal minPrice,
                          @RequestParam(required = false) BigDecimal maxPrice,
                          Model model) {

        List<Ad> ads = adService.search(q, categoryId, city, minPrice, maxPrice);

        model.addAttribute("ads", ads);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("q", q);
        model.addAttribute("city", city);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "ads/list";
    }

    @GetMapping("/{id}")
    public String viewAd(@PathVariable Long id, Model model) {
        Ad ad = adService.getById(id);
        model.addAttribute("ad", ad);
        return "ads/view";
    }

    @GetMapping("/new")
    public String newAdForm(Model model) {
        model.addAttribute("ad", new Ad());
        model.addAttribute("categories", categoryService.findAll());
        return "ads/new";
    }

    @PostMapping
    public String createAd(@ModelAttribute("ad") Ad ad,
                           @RequestParam(required = false) Long categoryId) {

        Ad saved = adService.create(ad, categoryId);
        return "redirect:/ads/" + saved.getId();
    }

    @GetMapping("/{id}/edit")
    public String editAdForm(@PathVariable Long id, Model model) {
        Ad ad = adService.getById(id);
        model.addAttribute("ad", ad);
        model.addAttribute("categories", categoryService.findAll());
        return "ads/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAd(@PathVariable Long id,
                           @ModelAttribute("ad") Ad ad,
                           @RequestParam(required = false) Long categoryId) {

        adService.update(id, ad, categoryId);
        return "redirect:/ads/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteAd(@PathVariable Long id) {
        adService.delete(id);
        return "redirect:/ads";
    }
}
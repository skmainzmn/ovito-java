package com.example.avito.service;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Category;
import com.example.avito.repository.AdRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final CategoryService categoryService;

    public AdService(AdRepository adRepository, CategoryService categoryService) {
        this.adRepository = adRepository;
        this.categoryService = categoryService;
    }

    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    public Ad getById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
    }

    public List<Ad> search(String q, Long categoryId, String city,
                           BigDecimal minPrice, BigDecimal maxPrice) {

        if (q != null && !q.isBlank()) {
            return adRepository.findByTitleContainingIgnoreCase(q);
        }

        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            return adRepository.findByCategory(category);
        }

        if (city != null && !city.isBlank()) {
            return adRepository.findByCityIgnoreCase(city);
        }

        if (minPrice != null && maxPrice != null) {
            return adRepository.findByPriceBetween(minPrice, maxPrice);
        }

        return adRepository.findAll();
    }

    public Ad create(Ad ad, Long categoryId) {
        ad.setCreatedAt(LocalDateTime.now());
        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            ad.setCategory(category);
        }
        return adRepository.save(ad);
    }

    public Ad update(Long id, Ad updated, Long categoryId) {
        Ad ad = getById(id);

        ad.setTitle(updated.getTitle());
        ad.setDescription(updated.getDescription());
        ad.setPrice(updated.getPrice());
        ad.setCity(updated.getCity());

        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            ad.setCategory(category);
        }

        return adRepository.save(ad);
    }

    public void delete(Long id) {
        adRepository.deleteById(id);
    }
}

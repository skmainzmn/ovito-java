package com.example.avito.service;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Category;
import com.example.avito.entity.User;
import com.example.avito.repository.AdRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public AdService(AdRepository adRepository,
                     CategoryService categoryService,
                     UserService userService) {
        this.adRepository = adRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // 🔹 СПИСОК объявлений (для главной / списка)
    public List<Ad> getAllAds() {
        return adRepository.findAllByOrderByCreatedAtDesc();
    }

    // 🔹 Поиск по фильтрам (если ты уже делала)
    public List<Ad> searchAds(String q, Long categoryId, String city) {
        boolean hasText = q != null && !q.isBlank();
        boolean hasCat = categoryId != null;
        boolean hasCity = city != null && !city.isBlank();

        if (hasText && hasCat && hasCity) {
            return adRepository
                    .findByTitleContainingIgnoreCaseAndCategoryIdAndCityIgnoreCaseOrderByCreatedAtDesc(
                            q, categoryId, city);
        } else if (hasText && hasCat) {
            return adRepository
                    .findByTitleContainingIgnoreCaseAndCategoryIdOrderByCreatedAtDesc(q, categoryId);
        } else if (hasText && hasCity) {
            return adRepository
                    .findByTitleContainingIgnoreCaseAndCityIgnoreCaseOrderByCreatedAtDesc(q, city);
        } else if (hasCat && hasCity) {
            return adRepository
                    .findByCategoryIdAndCityIgnoreCaseOrderByCreatedAtDesc(categoryId, city);
        } else if (hasText) {
            return adRepository.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(q);
        } else if (hasCat) {
            return adRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId);
        } else if (hasCity) {
            return adRepository.findByCityIgnoreCaseOrderByCreatedAtDesc(city);
        } else {
            return getAllAds();
        }
    }

    // 🔹 Получить по id
    public Ad getById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Объявление не найдено: " + id));
    }

    // 🔥 ВАЖНО: именно ЭТОТ метод вызывается из AdController
    public Ad createAd(Ad ad, Long categoryId) {
        ad.setCreatedAt(LocalDateTime.now());

        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            ad.setCategory(category);
        }

        // текущий залогиненный пользователь
        User owner = userService.getCurrentUser();
        ad.setOwner(owner);

        return adRepository.save(ad);
    }

    // нужен для PaymentService / promote
    public Ad save(Ad ad) {
        return adRepository.save(ad);
    }
}
package com.example.avito.repository;

import com.example.avito.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    // все объявления, самые новые сначала
    List<Ad> findAllByOrderByCreatedAtDesc();

    // только по тексту
    List<Ad> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String q);

    // только по категории
    List<Ad> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    // только по городу
    List<Ad> findByCityIgnoreCaseOrderByCreatedAtDesc(String city);

    // текст + категория
    List<Ad> findByTitleContainingIgnoreCaseAndCategoryIdOrderByCreatedAtDesc(
            String q,
            Long categoryId
    );

    // текст + город
    List<Ad> findByTitleContainingIgnoreCaseAndCityIgnoreCaseOrderByCreatedAtDesc(
            String q,
            String city
    );

    // категория + город
    List<Ad> findByCategoryIdAndCityIgnoreCaseOrderByCreatedAtDesc(
            Long categoryId,
            String city
    );

    // текст + категория + город
    List<Ad> findByTitleContainingIgnoreCaseAndCategoryIdAndCityIgnoreCaseOrderByCreatedAtDesc(
            String q,
            Long categoryId,
            String city
    );
}
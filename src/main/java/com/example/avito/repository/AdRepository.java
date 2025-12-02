package com.example.avito.repository;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findByTitleContainingIgnoreCase(String title);

    List<Ad> findByCategory(Category category);

    List<Ad> findByCityIgnoreCase(String city);

    List<Ad> findByPriceBetween(BigDecimal min, BigDecimal max);
}
package com.example.avito.service;

import com.example.avito.entity.Category;
import com.example.avito.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
    }

    @PostConstruct
    public void initDefaultCategories() {
        if (categoryRepository.count() == 0) {
            Category c1 = new Category();
            c1.setName("Недвижимость");
            categoryRepository.save(c1);

            Category c2 = new Category();
            c2.setName("Автомобили");
            categoryRepository.save(c2);

            Category c3 = new Category();
            c3.setName("Работа");
            categoryRepository.save(c3);
        }
    }
}

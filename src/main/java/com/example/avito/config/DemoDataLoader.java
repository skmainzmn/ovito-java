package com.example.avito.config;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Category;
import com.example.avito.entity.User;
import com.example.avito.repository.AdRepository;
import com.example.avito.repository.CategoryRepository;
import com.example.avito.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DemoDataLoader {

    @Bean
    public CommandLineRunner loadDemoData(CategoryRepository categoryRepository,
                                          UserRepository userRepository,
                                          AdRepository adRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {
            // если уже есть категории — ничего не делаем, чтобы не дублировать
            if (categoryRepository.count() > 0) {
                return;
            }

            // --- категории ---
            Category catRealty = new Category();
            catRealty.setName("Недвижимость");
            categoryRepository.save(catRealty);

            Category catElectronics = new Category();
            catElectronics.setName("Электроника");
            categoryRepository.save(catElectronics);

            Category catPets = new Category();
            catPets.setName("Животные");
            categoryRepository.save(catPets);

            // --- пользователь ---
            User demoUser = new User();
            demoUser.setUsername("demo");
            demoUser.setEmail("demo@example.com");
            demoUser.setPhone("+7 999 123-45-67");
            demoUser.setEnabled(true);
            demoUser.setPassword(passwordEncoder.encode("demo123")); // пароль: demo123
            userRepository.save(demoUser);

            // --- объявления ---
            Ad flat = new Ad();
            flat.setTitle("Сдаётся светлая квартира в центре");
            flat.setDescription("2-комнатная квартира, рядом метро, свежий ремонт, можно с животными.");
            flat.setCity("Москва");
            flat.setPrice(new BigDecimal("65000"));
            flat.setCategory(catRealty);
            flat.setOwner(demoUser);
            flat.setCreatedAt(LocalDateTime.now().minusDays(1));
            flat.setPromoted(true);
            adRepository.save(flat);

            Ad iphone = new Ad();
            iphone.setTitle("iPhone 13 Pro, как новый");
            iphone.setDescription("Оригинал, полный комплект, без царапин, всегда был в чехле.");
            iphone.setCity("Санкт-Петербург");
            iphone.setPrice(new BigDecimal("75000"));
            iphone.setCategory(catElectronics);
            iphone.setOwner(demoUser);
            iphone.setCreatedAt(LocalDateTime.now().minusHours(5));
            iphone.setPromoted(false);
            adRepository.save(iphone);

            Ad cat = new Ad();
            cat.setTitle("Милый котёнок в добрые руки");
            cat.setDescription("Котёнок 3 месяца, приучен к лотку, очень ласковый.");
            cat.setCity("Казань");
            cat.setPrice(new BigDecimal("0"));
            cat.setCategory(catPets);
            cat.setOwner(demoUser);
            cat.setCreatedAt(LocalDateTime.now().minusDays(2));
            cat.setPromoted(false);
            adRepository.save(cat);

            System.out.println(">>> Demo data loaded: categories + user demo/demo123 + 3 ads");
        };
    }
}
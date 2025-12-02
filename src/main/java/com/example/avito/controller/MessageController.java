package com.example.avito.controller;

import com.example.avito.entity.Ad;
import com.example.avito.entity.User;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AdService;
import com.example.avito.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final AdService adService;
    private final UserRepository userRepository;

    public MessageController(MessageService messageService,
                             AdService adService,
                             UserRepository userRepository) {
        this.messageService = messageService;
        this.adService = adService;
        this.userRepository = userRepository;
    }

    @PostMapping("/ad/{adId}")
    public String sendMessage(@PathVariable Long adId,
                              @RequestParam String text) {

        Ad ad = adService.getById(adId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + username));

        User recipient = ad.getOwner();

        messageService.sendMessage(ad, sender, recipient, text);

        return "redirect:/ads/" + adId;
    }
}
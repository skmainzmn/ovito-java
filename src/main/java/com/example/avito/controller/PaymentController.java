package com.example.avito.controller;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Payment;
import com.example.avito.service.AdService;
import com.example.avito.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final AdService adService;

    public PaymentController(PaymentService paymentService,
                             AdService adService) {
        this.paymentService = paymentService;
        this.adService = adService;
    }

    @PostMapping("/ad/{id}/promote")
    public String promoteAd(@PathVariable Long id) {
        Ad ad = adService.getById(id);

        // создаём фейковый платёж
        Payment payment = paymentService.createPaymentForAd(ad, new BigDecimal("199.00"));
        paymentService.markAsSuccess(payment);

        // помечаем объявление как "поднятое"
        ad.setPromoted(true);
        adService.save(ad);

        return "redirect:/ads/" + id;
    }
}
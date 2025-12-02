package com.example.avito.service;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Payment;
import com.example.avito.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RobokassaService robokassaService;

    public PaymentService(PaymentRepository paymentRepository, RobokassaService robokassaService) {
        this.paymentRepository = paymentRepository;
        this.robokassaService = robokassaService;
    }

    // создаём "фейковый" платёж для объявления
    public Payment createPaymentForAd(Ad ad, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setAd(ad);
        payment.setAmount(amount);
        payment.setStatus("NEW");
        payment.setCreatedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    // помечаем платёж как успешный
    public Payment markAsSuccess(Payment payment) {
        payment.setStatus("SUCCESS");
        return paymentRepository.save(payment);
    }
}
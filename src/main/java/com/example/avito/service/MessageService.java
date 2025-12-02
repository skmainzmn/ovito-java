package com.example.avito.service;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Message;
import com.example.avito.entity.User;
import com.example.avito.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Отправка сообщения автору объявления
    public Message sendMessage(Ad ad, User sender, User recipient, String text) {
        Message m = new Message();
        m.setAd(ad);
        m.setSender(sender);
        m.setRecipient(recipient);
        m.setText(text);
        m.setCreatedAt(LocalDateTime.now());  // вот эта строка — норм

        return messageRepository.save(m);
    }

    // Список сообщений по объявлению (чат)
    public List<Message> getMessagesForAd(Ad ad) {
        return messageRepository.findByAdOrderByCreatedAtAsc(ad);
    }
}
package com.example.avito.repository;

import com.example.avito.entity.Ad;
import com.example.avito.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByAdOrderByCreatedAtAsc(Ad ad);
}
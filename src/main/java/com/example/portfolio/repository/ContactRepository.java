package com.example.portfolio.repository;


import com.example.portfolio.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface ContactRepository extends JpaRepository<ContactMessage, UUID> {}
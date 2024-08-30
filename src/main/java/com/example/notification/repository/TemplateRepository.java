package com.example.notification.repository;

import com.example.notification.model.TemplateMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TemplateRepository extends JpaRepository<TemplateMessage, UUID> {
    List<TemplateMessage> findTemplatesByOwner_Id(UUID id, Pageable pageable);
}

package com.example.notification.repository;

import com.example.notification.model.Template;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

    public interface TemplateRepository extends JpaRepository<Template, UUID> {
        List<Template> findTemplatesByOwner_Id(UUID id, Pageable pageable);
    }

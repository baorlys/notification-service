package com.example.notification.model;

import com.example.notification.enums.TargetOutput;
import com.example.notification.enums.TemplateType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Template {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    User owner;

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    TemplateType templateType;

    @Column(nullable = false)
    String body;
}

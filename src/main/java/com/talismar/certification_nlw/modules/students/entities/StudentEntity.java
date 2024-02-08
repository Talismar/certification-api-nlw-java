package com.talismar.certification_nlw.modules.students.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // Para criar um construtor com todos os argumentos
@NoArgsConstructor // Para criar um empty constructor
@Entity(name="students")
public class StudentEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "studentEntity")
    @JsonBackReference
    private List<CertificationStudentEntity> certificationsStudentEntity;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}

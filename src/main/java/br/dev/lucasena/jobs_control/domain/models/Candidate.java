package br.dev.lucasena.jobs_control.domain.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @Email(message = "Email must to be valid.")
    @NotBlank
    private String email;

    @Length(min = 6, message = "Pass must has at least 6 characters.")
    private String password;
    
    @NotBlank
    private String description;
    @NotBlank
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

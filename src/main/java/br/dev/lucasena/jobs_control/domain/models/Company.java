package br.dev.lucasena.jobs_control.domain.models;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  private String cnpj;

  @NotBlank
  @Email(message = "Email must to be valid.")
  private String email;

  @NotBlank
  @Length(min = 6, message = "Pass must has at least 6 characters.")
  private String password;

  @NotBlank
  private String name;

  @NotBlank
  private String website;

  @CreationTimestamp
  private LocalDateTime createdAt;
}

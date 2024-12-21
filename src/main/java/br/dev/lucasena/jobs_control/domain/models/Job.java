package br.dev.lucasena.jobs_control.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.dev.lucasena.jobs_control.utils.enums.Level;
import br.dev.lucasena.jobs_control.utils.enums.Type;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @Enumerated(EnumType.STRING)
  private Level level;

  @Enumerated(EnumType.STRING)
  private Type type;

  @NotBlank
  private String benefits;

  @NotNull
  private Float salary;

  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private Company company;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @CreationTimestamp
  private LocalDateTime createdAt;
}

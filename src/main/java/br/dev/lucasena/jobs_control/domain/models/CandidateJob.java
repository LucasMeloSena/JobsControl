package br.dev.lucasena.jobs_control.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "candidate_job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateJob {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private Candidate candidate;
  @ManyToOne
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private Job job;

  @Column(name = "candidate_id", nullable = false)
  private UUID candidateId;
  @Column(name = "job_id", nullable = false)
  private UUID jobId;

  @CreationTimestamp
  private LocalDateTime createdAt;
}

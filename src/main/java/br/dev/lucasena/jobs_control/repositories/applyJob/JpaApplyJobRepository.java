package br.dev.lucasena.jobs_control.repositories.applyJob;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.lucasena.jobs_control.domain.models.CandidateJob;

public interface JpaApplyJobRepository extends JpaRepository<CandidateJob, UUID> {
  
}

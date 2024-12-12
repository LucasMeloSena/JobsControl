package br.dev.lucasena.jobs_control.repositories.job;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.lucasena.jobs_control.domain.models.Job;

public interface JpaJobRepository extends JpaRepository<Job, UUID> {
  
}

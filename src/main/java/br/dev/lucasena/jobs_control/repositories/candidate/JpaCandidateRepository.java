package br.dev.lucasena.jobs_control.repositories.candidate;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.lucasena.jobs_control.domain.models.Candidate;

public interface JpaCandidateRepository extends JpaRepository<Candidate, UUID> {
  Optional<Candidate> findByEmail(String email);
}

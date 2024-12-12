package br.dev.lucasena.jobs_control.domain.useCases.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.exceptions.UserAlreadyExistsException;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
    JpaCandidateRepository candidateRepository;

  public Candidate execute(Candidate candidate) {
    this.candidateRepository
            .findByEmail(candidate.getEmail())
            .ifPresent((foundUser) -> {
                throw new UserAlreadyExistsException();
            });
        
         return this.candidateRepository.save(candidate);
  }
}

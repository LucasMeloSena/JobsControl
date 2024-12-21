package br.dev.lucasena.jobs_control.domain.useCases.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.exceptions.UserAlreadyExistsException;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private JpaCandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder encodePassword;

  public Candidate execute(Candidate candidate) {
    this.candidateRepository
            .findByEmail(candidate.getEmail())
            .ifPresent((foundUser) -> {
                throw new UserAlreadyExistsException();
        });
            
    String encodedPass = this.encodePassword.encode(candidate.getPassword());
    candidate.setPassword(encodedPass);

        
         return this.candidateRepository.save(candidate);
  }
}

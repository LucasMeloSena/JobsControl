package br.dev.lucasena.jobs_control.domain.useCases.candidate;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.dtos.candidate.ProfileResponseDto;
import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;

@Service
public class GetProfileUseCase {
  @Autowired
  private JpaCandidateRepository candidateRepository;

  public ProfileResponseDto execute(UUID id) {
    Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> {
      throw new UsernameNotFoundException("Candidate not found with provided id");
    });

   return ProfileResponseDto.builder()
    .id(candidate.getId())
    .name(candidate.getName())
    .description(candidate.getDescription())
    .email(candidate.getEmail())
    .curriculum(candidate.getCurriculum())
        .build();     
  }
}

package br.dev.lucasena.jobs_control.domain.useCases.candidate;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.CandidateJob;
import br.dev.lucasena.jobs_control.repositories.applyJob.JpaApplyJobRepository;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;
import br.dev.lucasena.jobs_control.repositories.job.JpaJobRepository;

@Service
public class ApplyJobUseCase {
  @Autowired
  private JpaCandidateRepository candidateRepository;
  @Autowired
  private JpaJobRepository jobRepository;
  @Autowired
  private JpaApplyJobRepository applyJobRepository;

  public CandidateJob execute(UUID candidateId, UUID jobId) {
    this.candidateRepository.findById(candidateId).orElseThrow(() -> {
      throw new UsernameNotFoundException("Candidate not found.");
    });
    this.jobRepository.findById(jobId).orElseThrow(() -> {
      throw new UsernameNotFoundException("Job not found");
    });

    CandidateJob applyJob = CandidateJob.builder()
      .candidateId(candidateId)
      .jobId(jobId)
      .build();
    return applyJobRepository.save(applyJob);
  }
}

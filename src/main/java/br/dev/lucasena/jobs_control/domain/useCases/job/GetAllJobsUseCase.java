package br.dev.lucasena.jobs_control.domain.useCases.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Job;
import br.dev.lucasena.jobs_control.repositories.job.JpaJobRepository;

@Service
public class GetAllJobsUseCase {
  @Autowired
  private JpaJobRepository jobRepository;

  public List<Job> execute(String filter) {
    return this.jobRepository.findByNameContainingIgnoreCase(filter);
  }
}

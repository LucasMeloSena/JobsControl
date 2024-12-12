package br.dev.lucasena.jobs_control.domain.useCases.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Job;
import br.dev.lucasena.jobs_control.repositories.job.JpaJobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  JpaJobRepository jobRepository;

  public Job execute(Job job) {
    return this.jobRepository.save(job);
  }
}

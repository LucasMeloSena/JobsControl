package br.dev.lucasena.jobs_control.domain.useCases.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Job;
import br.dev.lucasena.jobs_control.repositories.company.JpaCompanyRepository;
import br.dev.lucasena.jobs_control.repositories.job.JpaJobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  JpaJobRepository jobRepository;

  @Autowired
  JpaCompanyRepository companyRepository;

  public Job execute(Job job) {
    companyRepository.findById(job.getCompanyId()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Company not found");
    });
    return this.jobRepository.save(job);
  }
}

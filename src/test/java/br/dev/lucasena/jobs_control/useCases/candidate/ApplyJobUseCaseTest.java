package br.dev.lucasena.jobs_control.useCases.candidate;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.assertj.core.api.Assertions.assertThat;

import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.domain.models.CandidateJob;
import br.dev.lucasena.jobs_control.domain.models.Job;
import br.dev.lucasena.jobs_control.domain.useCases.candidate.ApplyJobUseCase;
import br.dev.lucasena.jobs_control.repositories.applyJob.JpaApplyJobRepository;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;
import br.dev.lucasena.jobs_control.repositories.job.JpaJobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobUseCaseTest {
  @InjectMocks
  private ApplyJobUseCase applyJobUseCase;
  @Mock
  private JpaCandidateRepository candidateRepository;
  @Mock
  private JpaJobRepository jobRepository;
  @Mock
  private JpaApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Should be able to apply job")
  public void should_be_able_to_apply_job() {
      UUID candidateId = UUID.randomUUID();
      UUID jobId = UUID.randomUUID();

      var applyJob = CandidateJob.builder().candidateId(candidateId).jobId(jobId).build();
      var applyJobCreated = CandidateJob.builder().id(UUID.randomUUID()).build();

      when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new Candidate()));
      when(jobRepository.findById(jobId)).thenReturn(Optional.of(new Job()));
      when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

      var result = applyJobUseCase.execute(candidateId, jobId);
      assertThat(result).hasFieldOrProperty("id");
      assertNotNull(result.getId());
  }

  @Test
  @DisplayName("Should not be able to apply job if candidate not found")
  public void should_not_be_able_to_apply_job_if_candidate_not_found() {
    try {
      applyJobUseCase.execute(null, null);
    } catch (Exception ex) {
      assertInstanceOf(UsernameNotFoundException.class, ex);
    }
  }

  @Test
  @DisplayName("Should not be able to apply job if job not found")
  public void should_not_be_able_to_apply_job_if_job_not_found() {
    try {
      UUID candidateId = UUID.randomUUID();
      Candidate candidate = new Candidate();
      candidate.setId(candidateId);
      when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
      applyJobUseCase.execute(candidateId, null);
    } catch (Exception ex) {
      assertInstanceOf(UsernameNotFoundException.class, ex);
    }
  }
}

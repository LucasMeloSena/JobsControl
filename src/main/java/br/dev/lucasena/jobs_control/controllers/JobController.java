package br.dev.lucasena.jobs_control.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.lucasena.jobs_control.domain.dtos.job.CreateJobDto;
import br.dev.lucasena.jobs_control.domain.models.Job;
import br.dev.lucasena.jobs_control.domain.useCases.job.CreateJobUseCase;
import br.dev.lucasena.jobs_control.domain.useCases.job.GetAllJobsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Job", description = "Job operations")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @Autowired
  private GetAllJobsUseCase getAllJobsUseCase;

    @PreAuthorize("hasRole('company')")
    @PostMapping()
    @Operation(summary = "Register a new job")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDto jobDto, HttpServletRequest request) {
      try {
        Object companyId = request.getAttribute("company_id");

        Job job = Job.builder()
            .name(jobDto.getName())
            .description(jobDto.getDescription())
            .level(jobDto.getLevel())
            .type(jobDto.getType())
            .benefits(jobDto.getBenefits())
            .salary(jobDto.getSalary())
            .companyId(UUID.fromString(companyId.toString()))
            .build();

        Job result = this.createJobUseCase.execute(job);
        return ResponseEntity.ok().body(result);
      } catch (Exception ex) {
        String message = ex.getMessage();
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
      }
    }
    
    @GetMapping()
    @Operation(summary = "List all registered jobs based on name")
    @SecurityRequirement(name = "jwt_auth")
      public ResponseEntity<Object> findJobByFilter(@RequestParam String filter) {
      try {
        List<Job> jobs = this.getAllJobsUseCase.execute(filter);
        return ResponseEntity.ok().body(jobs);
      } catch (Exception ex) {
        String message = ex.getMessage();
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
      }
    }
}

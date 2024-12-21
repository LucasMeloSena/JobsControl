package br.dev.lucasena.jobs_control.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.lucasena.jobs_control.domain.dtos.candidate.AuthCandidateDto;
import br.dev.lucasena.jobs_control.domain.dtos.candidate.AuthCandidateResponseDto;
import br.dev.lucasena.jobs_control.domain.dtos.candidate.ProfileResponseDto;
import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.domain.models.CandidateJob;
import br.dev.lucasena.jobs_control.domain.useCases.candidate.ApplyJobUseCase;
import br.dev.lucasena.jobs_control.domain.useCases.candidate.AuthCandidateUseCase;
import br.dev.lucasena.jobs_control.domain.useCases.candidate.CreateCandidateUseCase;
import br.dev.lucasena.jobs_control.domain.useCases.candidate.GetProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidate", description = "Candidate Operations")
public class CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;
    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;
    @Autowired
    private GetProfileUseCase getProfileUseCase;
    @Autowired
    private ApplyJobUseCase applyJobUseCase;

    @PostMapping()
    @Operation(summary = "Register a candidate")
    public ResponseEntity<Object> create(@Valid @RequestBody Candidate candidate) {
        try {
            Candidate result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            String message = ex.getMessage();
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Sign in as candidate")
    public ResponseEntity<Object> create(@Valid @RequestBody AuthCandidateDto candidateDto) {
        try {
            AuthCandidateResponseDto result = this.authCandidateUseCase.execute(candidateDto);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            String message = ex.getMessage();
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('candidate')")
    @Operation(summary = "List candidate profile data")
        @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        try {
            UUID id = UUID.fromString(request.getAttribute("candidate_id").toString());
            ProfileResponseDto profile = getProfileUseCase.execute(id);
            return ResponseEntity.ok().body(profile);
        } catch (Exception ex) {
            String message = ex.getMessage();
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('candidate')")
    @Operation(summary = "Apply job from a candidate")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
        try {
            UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
            CandidateJob appliedJob = applyJobUseCase.execute(candidateId, jobId);
            return ResponseEntity.ok().body(appliedJob);
        } catch (Exception ex) {
            String message = ex.getMessage();
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        }
    }
}

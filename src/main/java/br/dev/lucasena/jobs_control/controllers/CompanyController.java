package br.dev.lucasena.jobs_control.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.lucasena.jobs_control.domain.dtos.company.AuthCommpanyResponseDto;
import br.dev.lucasena.jobs_control.domain.dtos.company.AuthCompanyDto;
import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.domain.useCases.company.AuthCompanyUseCase;
import br.dev.lucasena.jobs_control.domain.useCases.company.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Company operations")
public class CompanyController {
  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;
    
  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping()
    @Operation(summary = "Register a company")
    public ResponseEntity<Object> create(@Valid @RequestBody Company company) {
      try {
        Company result = this.createCompanyUseCase.execute(company);
        return ResponseEntity.ok().body(result);
      } catch (Exception ex) {
        String message = ex.getMessage();
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
      }
    }

    @PostMapping("/login")
    @Operation(summary = "Sign in as company")
     public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthCompanyDto authCompany) {
      try {
        AuthCommpanyResponseDto result = this.authCompanyUseCase.execute(authCompany);
        return ResponseEntity.ok().body(result);
      } catch (Exception ex) {
        String message = ex.getMessage();
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
      }
    }
}

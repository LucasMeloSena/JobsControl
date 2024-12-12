package br.dev.lucasena.jobs_control.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.domain.useCases.company.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
  @Autowired
    CreateCompanyUseCase createCompanyUseCase;

    @PostMapping()
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
}

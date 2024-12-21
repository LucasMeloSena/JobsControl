package br.dev.lucasena.jobs_control.domain.dtos.company;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthCompanyDto {
  private String email;
  private String password;
}

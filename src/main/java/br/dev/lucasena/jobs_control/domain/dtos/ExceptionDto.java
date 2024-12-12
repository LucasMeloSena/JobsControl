package br.dev.lucasena.jobs_control.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDto {
  private String message;
  private String field;
}

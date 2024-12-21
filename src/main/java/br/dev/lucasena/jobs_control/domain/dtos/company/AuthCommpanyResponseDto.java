package br.dev.lucasena.jobs_control.domain.dtos.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCommpanyResponseDto {
  private String access_token;
  private Long expires_in;
}

package br.dev.lucasena.jobs_control.domain.dtos.job;

import br.dev.lucasena.jobs_control.utils.enums.Level;
import br.dev.lucasena.jobs_control.utils.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDto {
  private String name;
  private String description;
  private String benefits;
  private Level level;
  private Type type;
  private Float salary;
}

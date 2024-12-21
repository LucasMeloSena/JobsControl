package br.dev.lucasena.jobs_control.domain.dtos.candidate;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private UUID id;
    private String name;
    private String email;    
    private String description;
    private String curriculum;
}

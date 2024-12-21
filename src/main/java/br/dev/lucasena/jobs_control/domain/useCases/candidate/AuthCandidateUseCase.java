package br.dev.lucasena.jobs_control.domain.useCases.candidate;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.dev.lucasena.jobs_control.domain.dtos.candidate.AuthCandidateDto;
import br.dev.lucasena.jobs_control.domain.dtos.candidate.AuthCandidateResponseDto;
import br.dev.lucasena.jobs_control.domain.models.Candidate;
import br.dev.lucasena.jobs_control.repositories.candidate.JpaCandidateRepository;

@Service
public class AuthCandidateUseCase {
  @Value("${security.token.secret.candidate}")
  private String jwtSecret;

  @Autowired
  private JpaCandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder validadePassword;

  public AuthCandidateResponseDto execute(AuthCandidateDto candidateDto) throws AuthenticationException {
    Candidate candidate = this.candidateRepository.findByEmail(candidateDto.email()).orElseThrow(() -> {
      throw new UsernameNotFoundException("User not found with provided email");
    });

    Boolean passwordMatches = this.validadePassword.matches(candidateDto.password(), candidate.getPassword());
    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

    Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
    String token = JWT.create().withIssuer(candidate.getName())
        .withExpiresAt(expiresIn)
        .withSubject(candidate.getId().toString())
        .withPayload(Map.of("email", candidate.getEmail()))
        .withClaim("roles", Arrays.asList("candidate"))
        .sign(algorithm);

    return AuthCandidateResponseDto.builder()
        .access_token(token)
      .expires_in(expiresIn.toEpochMilli())
        .build();
  }
}

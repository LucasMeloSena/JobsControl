package br.dev.lucasena.jobs_control.domain.useCases.company;

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

import br.dev.lucasena.jobs_control.domain.dtos.company.AuthCommpanyResponseDto;
import br.dev.lucasena.jobs_control.domain.dtos.company.AuthCompanyDto;
import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.repositories.company.JpaCompanyRepository;

@Service
public class AuthCompanyUseCase {
  @Value("${security.token.secret}")
  private String jwtSecret;

  @Autowired
  private JpaCompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder validadePassword;

  public AuthCommpanyResponseDto execute(AuthCompanyDto authCompany) throws AuthenticationException {
    Company company = this.companyRepository.findByEmail(authCompany.getEmail()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Company not found with provided email");
    });

    Boolean passwordMatches = this.validadePassword.matches(authCompany.getPassword(), company.getPassword());
    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    String token = JWT.create().withIssuer(company.getName())
        .withExpiresAt(expiresIn)
        .withSubject(company.getId().toString())
        .withPayload(Map.of("email", company.getEmail()))
        .withClaim("roles", Arrays.asList("company"))
        .sign(algorithm);

    return AuthCommpanyResponseDto.builder()
    .access_token(token)
    .expires_in(expiresIn.toEpochMilli())
        .build();
  }
}

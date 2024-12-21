package br.dev.lucasena.jobs_control.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
  public static String objectToJson(Object object) {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID id, String secret) {
    Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
    Algorithm algorithm = Algorithm.HMAC256(secret);
    String token = JWT.create().withIssuer("teste")
        .withExpiresAt(expiresIn)
        .withSubject(id.toString())
        .withPayload(Map.of("email", "test@email.com"))
        .withClaim("roles", Arrays.asList("company"))
        .sign(algorithm);
    return token;
  }
}

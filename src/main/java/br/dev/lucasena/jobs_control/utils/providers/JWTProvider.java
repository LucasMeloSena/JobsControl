package br.dev.lucasena.jobs_control.utils.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {
  @Value("${security.token.secret}")
  private String jwtSecret;

  public DecodedJWT validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
    token = token.replace("Bearer ", "");
    try {
      return JWT.require(algorithm).build().verify(token);
    } catch (JWTVerificationException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}

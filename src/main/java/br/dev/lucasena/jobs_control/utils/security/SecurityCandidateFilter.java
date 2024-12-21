package br.dev.lucasena.jobs_control.utils.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.dev.lucasena.jobs_control.utils.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
  @Autowired
  private JWTCandidateProvider jwtCandidateProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (request.getRequestURI().startsWith("/candidate")) {
      if (header != null) {
        DecodedJWT decodedJwt = this.jwtCandidateProvider.validateToken(header);
        if (decodedJwt == null) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return;
        }
        request.setAttribute("candidate_id", decodedJwt.getSubject());
        var roles = decodedJwt.getClaim("roles").asList(Object.class);
        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString())).toList();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedJwt.getSubject(), null, grants);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
}

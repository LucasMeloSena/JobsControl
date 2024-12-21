package br.dev.lucasena.jobs_control.utils.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Autowired
  private SecurityCompanyFilter securityCompanyFilter;
  @Autowired
  SecurityCandidateFilter securityCandidateFilter;

  private static final String[] PERMIT_LIST = {
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/swagger-resource/**",
    "/actuator/**"
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/candidate").permitAll()
              .requestMatchers("/company").permitAll()
              .requestMatchers("/company/login").permitAll()
              .requestMatchers("/candidate/login").permitAll()
              .requestMatchers(PERMIT_LIST).permitAll();

          auth.anyRequest().authenticated();
        })
        .addFilterBefore(this.securityCandidateFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(this.securityCompanyFilter, BasicAuthenticationFilter.class);
    return http.build();
  }
  
  @Bean
  public PasswordEncoder encodePass() {
    return new BCryptPasswordEncoder();
  }
}

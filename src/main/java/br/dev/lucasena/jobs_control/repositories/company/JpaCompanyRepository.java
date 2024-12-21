package br.dev.lucasena.jobs_control.repositories.company;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.lucasena.jobs_control.domain.models.Company;

public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findByCnpjOrEmail(String cnpj, String email);

  Optional<Company> findByEmail(String email);
}


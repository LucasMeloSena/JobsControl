package br.dev.lucasena.jobs_control.domain.useCases.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.exceptions.CompanyAlreadyExistsException;
import br.dev.lucasena.jobs_control.repositories.company.JpaCompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private JpaCompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder encodePassword;

  public Company execute(Company company) {
    this.companyRepository
    .findByCnpjOrEmail(company.getCnpj(), company.getEmail())
        .ifPresent((foundCompany) -> {
          throw new CompanyAlreadyExistsException();
        });

      String encodedPass = this.encodePassword.encode(company.getPassword());
      company.setPassword(encodedPass);
    
    return this.companyRepository.save(company);
  }
}

package br.dev.lucasena.jobs_control.domain.useCases.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.jobs_control.domain.models.Company;
import br.dev.lucasena.jobs_control.exceptions.CompanyAlreadyExistsException;
import br.dev.lucasena.jobs_control.repositories.company.JpaCompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  JpaCompanyRepository companyRepository;

  public Company execute(Company company) {
    this.companyRepository
    .findByCnpjOrEmail(company.getCnpj(), company.getEmail())
        .ifPresent((foundCompany) -> {
          throw new CompanyAlreadyExistsException();
        });
    
    return this.companyRepository.save(company);
  }
}

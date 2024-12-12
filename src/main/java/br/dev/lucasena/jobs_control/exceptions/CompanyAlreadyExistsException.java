package br.dev.lucasena.jobs_control.exceptions;

public class CompanyAlreadyExistsException extends RuntimeException {
  public CompanyAlreadyExistsException() {
    super("A company with provided email or cnpj already exists.");
  }
}

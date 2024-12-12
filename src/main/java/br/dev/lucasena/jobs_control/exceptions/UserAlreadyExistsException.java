package br.dev.lucasena.jobs_control.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super("A candidate with provided email already exists.");
  }
}

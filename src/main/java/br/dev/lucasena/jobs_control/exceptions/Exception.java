package br.dev.lucasena.jobs_control.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.dev.lucasena.jobs_control.domain.dtos.ExceptionDto;

@ControllerAdvice
public class Exception {
  private MessageSource messageSource;

  public Exception(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ExceptionDto>> handleInvalidArgumentException(MethodArgumentNotValidException ex) {
    List<ExceptionDto> dto = new ArrayList<>();

    ex.getBindingResult().getFieldErrors().forEach(err -> {
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      ExceptionDto exDto = new ExceptionDto(message, err.getField());
      dto.add(exDto);
    });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
  }
}

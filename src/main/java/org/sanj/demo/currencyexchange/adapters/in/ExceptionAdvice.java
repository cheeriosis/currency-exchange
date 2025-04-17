package org.sanj.demo.currencyexchange.adapters.in;

import org.sanj.demo.currencyexchange.domain.exceptions.AccountCurrencyException;
import org.sanj.demo.currencyexchange.domain.exceptions.InvalidAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler(AccountCurrencyException.class)
  ErrorResponse handle(final AccountCurrencyException e) {
    return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
  }

  @ExceptionHandler(InvalidAmountException.class)
  ErrorResponse handle(final InvalidAmountException e) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ErrorResponse handle(final IllegalArgumentException e) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
  }
}

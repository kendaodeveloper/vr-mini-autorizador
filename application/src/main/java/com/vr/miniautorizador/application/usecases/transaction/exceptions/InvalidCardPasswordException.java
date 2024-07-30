package com.vr.miniautorizador.application.usecases.transaction.exceptions;

import com.vr.miniautorizador.domain.exceptions.UnprocessableEntityException;

public class InvalidCardPasswordException extends UnprocessableEntityException {
  public InvalidCardPasswordException() {
    super("Invalid Card Password");
  }
}

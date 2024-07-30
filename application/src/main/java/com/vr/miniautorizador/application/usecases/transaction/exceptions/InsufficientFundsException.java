package com.vr.miniautorizador.application.usecases.transaction.exceptions;

import com.vr.miniautorizador.domain.exceptions.UnprocessableEntityException;

public class InsufficientFundsException extends UnprocessableEntityException {
  public InsufficientFundsException() {
    super("Insufficient Funds");
  }
}

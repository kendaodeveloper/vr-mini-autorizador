package com.vr.miniautorizador.application.usecases.transaction.exceptions;

import com.vr.miniautorizador.domain.exceptions.NotFoundException;

public class TransactionCardNotFoundException extends NotFoundException {
  public TransactionCardNotFoundException() {
    super("Transaction Card not found");
  }
}

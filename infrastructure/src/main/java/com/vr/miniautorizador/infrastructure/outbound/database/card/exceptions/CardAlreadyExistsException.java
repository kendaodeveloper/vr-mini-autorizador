package com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions;

import com.vr.miniautorizador.domain.exceptions.ConflictException;

public class CardAlreadyExistsException extends ConflictException {
  public CardAlreadyExistsException() {
    super("Card already exists");
  }
}

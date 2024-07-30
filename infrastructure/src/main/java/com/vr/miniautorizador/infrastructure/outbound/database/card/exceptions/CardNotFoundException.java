package com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions;

import com.vr.miniautorizador.domain.exceptions.NotFoundException;

public class CardNotFoundException extends NotFoundException {
  public CardNotFoundException() {
    super("Card not found");
  }
}

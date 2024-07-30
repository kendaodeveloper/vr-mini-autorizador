package com.vr.miniautorizador.application.usecases.card.exceptions;

import com.vr.miniautorizador.domain.exceptions.NotFoundException;

public class CardNotFoundException extends NotFoundException {
  public CardNotFoundException() {
    super("Card not found");
  }
}

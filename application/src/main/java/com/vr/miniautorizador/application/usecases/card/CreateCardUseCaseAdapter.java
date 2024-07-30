package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCardUseCaseAdapter implements CreateCardUseCasePort {
  private final CreateCardGatewayPort createCardGatewayPort;

  @Override
  public Card execute(Card card) {
    return this.createCardGatewayPort.createCard(card);
  }
}

package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.application.usecases.card.exceptions.CardNotFoundException;
import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.in.card.GetCardByNumberUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCardByNumberUseCaseAdapter implements GetCardByNumberUseCasePort {
  private final GetCardByNumberGatewayPort getCardByNumberGatewayPort;

  @Override
  public Card execute(String cardNumber) {
    final var card = this.getCardByNumberGatewayPort.getCardByNumber(cardNumber);
    return card.orElseThrow(CardNotFoundException::new);
  }
}

package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.base.Pair;
import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.entities.card.CardAggregate;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCardUseCaseAdapter implements CreateCardUseCasePort {
  private final GetCardByNumberGatewayPort getCardByNumberGatewayPort;
  private final CreateCardGatewayPort createCardGatewayPort;

  @Override
  public Pair<Card, Boolean> execute(Card card) {
    return this.getCardByNumberGatewayPort.getCardByNumber(card.getNumber())
        .map((c) -> new Pair<>(c, true))
        .orElseGet(() -> new Pair<>(this.createCard(card), false));
  }

  private Card createCard(Card card) {
    final var cardAggregate = CardAggregate.of(card);

    cardAggregate.setDefaultCardBalanceValueIfNecessary();
    cardAggregate.encryptCardPassword();

    return this.createCardGatewayPort.createCard(cardAggregate.getCard());
  }
}

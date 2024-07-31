package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.base.Pair;
import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateCardUseCaseAdapter implements CreateCardUseCasePort {
  private static final BigDecimal MINIMUM_CARD_BALANCE = new BigDecimal(500);
  private final GetCardByNumberGatewayPort getCardByNumberGatewayPort;
  private final CreateCardGatewayPort createCardGatewayPort;

  @Override
  public Pair<Card, Boolean> execute(Card card) {
    return this.getCardByNumberGatewayPort.getCardByNumber(card.getNumber())
        .map((c) -> new Pair<>(c, true))
        .orElseGet(() -> new Pair<>(this.createCard(card), false));
  }

  private Card createCard(Card card) {
    if (card.getBalance() == null || card.getBalance().compareTo(MINIMUM_CARD_BALANCE) < 0) {
      card.setBalance(MINIMUM_CARD_BALANCE);
    }

    card.setPassword(PasswordUtil.encrypt(card.getPassword()));

    return this.createCardGatewayPort.createCard(card);
  }
}

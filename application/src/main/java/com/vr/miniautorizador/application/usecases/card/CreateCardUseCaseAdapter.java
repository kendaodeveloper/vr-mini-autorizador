package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateCardUseCaseAdapter implements CreateCardUseCasePort {
  private static final BigDecimal MINIMUM_CARD_BALANCE = new BigDecimal(500);
  private final CreateCardGatewayPort createCardGatewayPort;

  @Override
  public Card execute(Card card) {
    if (card.getBalance() == null || card.getBalance().compareTo(MINIMUM_CARD_BALANCE) < 0) {
      card.setBalance(MINIMUM_CARD_BALANCE);
    }
    card.setPassword(PasswordUtil.encrypt(card.getPassword()));
    return this.createCardGatewayPort.createCard(card);
  }
}

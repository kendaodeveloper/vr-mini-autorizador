package com.vr.miniautorizador.domain.ports.out.card;

import com.vr.miniautorizador.domain.entities.card.Card;

import java.util.Optional;

public interface GetCardByNumberGatewayPort {
  Optional<Card> getCardByNumber(String cardNumber);
}

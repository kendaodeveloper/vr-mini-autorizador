package com.vr.miniautorizador.domain.ports.in.card;

import com.vr.miniautorizador.domain.entities.card.Card;

public interface GetCardByNumberUseCasePort {
  Card execute(String cardNumber);
}

package com.vr.miniautorizador.domain.ports.out.card;

import com.vr.miniautorizador.domain.entities.card.Card;

public interface CreateCardGatewayPort {
  Card createCard(Card card);
}

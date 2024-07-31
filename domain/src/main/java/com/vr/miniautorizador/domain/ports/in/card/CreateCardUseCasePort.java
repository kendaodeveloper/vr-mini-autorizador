package com.vr.miniautorizador.domain.ports.in.card;

import com.vr.miniautorizador.domain.entities.base.Pair;
import com.vr.miniautorizador.domain.entities.card.Card;

public interface CreateCardUseCasePort {
  Pair<Card, Boolean> execute(Card card);
}

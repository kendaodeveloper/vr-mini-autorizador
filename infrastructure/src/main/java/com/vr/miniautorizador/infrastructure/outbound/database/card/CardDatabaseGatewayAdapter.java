package com.vr.miniautorizador.infrastructure.outbound.database.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.infrastructure.outbound.database.card.mapper.CardTableMapper;
import com.vr.miniautorizador.infrastructure.outbound.database.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardDatabaseGatewayAdapter implements CreateCardGatewayPort {
  private final CardRepository cardRepository;

  @Override
  public Card createCard(Card card) {
    final var cardTable = CardTableMapper.toTable(card);

    this.cardRepository.saveAndFlush(cardTable);

    return CardTableMapper.toEntity(cardTable);
  }
}

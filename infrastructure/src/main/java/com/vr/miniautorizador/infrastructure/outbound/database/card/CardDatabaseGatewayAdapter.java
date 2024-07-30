package com.vr.miniautorizador.infrastructure.outbound.database.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions.CardAlreadyExistsException;
import com.vr.miniautorizador.infrastructure.outbound.database.card.mapper.CardTableMapper;
import com.vr.miniautorizador.infrastructure.outbound.database.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardDatabaseGatewayAdapter implements
    CreateCardGatewayPort,
    GetCardByNumberGatewayPort {
  private final CardRepository cardRepository;

  @Override
  public Card createCard(Card card) {
    final var cardRequest = CardTableMapper.toTable(card);

    final var existingCard = this.cardRepository.findOneByNumber(cardRequest.getNumber());

    if (existingCard.isPresent()) {
      throw new CardAlreadyExistsException();
    }

    final var persistedCard = this.cardRepository.saveAndFlush(cardRequest);

    return CardTableMapper.toEntity(persistedCard);
  }

  @Override
  public Optional<Card> getCardByNumber(String cardNumber) {
    final var card = this.cardRepository.findOneByNumber(cardNumber);

    return CardTableMapper.toOptionalEntity(card);
  }
}

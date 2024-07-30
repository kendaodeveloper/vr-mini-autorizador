package com.vr.miniautorizador.infrastructure.outbound.database.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.UpdateCardBalanceByIdGatewayPort;
import com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions.CardAlreadyExistsException;
import com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions.CardNotFoundException;
import com.vr.miniautorizador.infrastructure.outbound.database.card.mapper.CardTableMapper;
import com.vr.miniautorizador.infrastructure.outbound.database.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardDatabaseGatewayAdapter implements
    CreateCardGatewayPort,
    GetCardByNumberGatewayPort,
    UpdateCardBalanceByIdGatewayPort {
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

  @Override
  public void updateCardBalanceById(UUID id, BigDecimal balance) {
    final var card = this.cardRepository.findById(id).orElseThrow(CardNotFoundException::new);

    card.setBalance(balance);

    this.cardRepository.saveAndFlush(card);
  }
}

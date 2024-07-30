package com.vr.miniautorizador.infrastructure.outbound.database.card.mapper;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardTableMapper {
  public static Optional<Card> toOptionalEntity(Optional<CardTable> optionalCard) {
    return optionalCard.map(CardTableMapper::toEntity);
  }

  public static Card toEntity(CardTable card) {
    return Card.builder()
        .id(card.getId())
        .number(card.getNumber())
        .password(card.getPassword())
        .cvv(card.getCvv())
        .expirationDate(card.getExpirationDate())
        .balance(card.getBalance())
        .createdAt(card.getCreatedAt())
        .updatedAt(card.getUpdatedAt())
        .build();
  }

  public static CardTable toTable(Card card) {
    return CardTable.builder()
        .id(card.getId() == null ? UUID.randomUUID() : card.getId())
        .number(card.getNumber())
        .password(card.getPassword())
        .cvv(card.getCvv())
        .expirationDate(card.getExpirationDate())
        .balance(card.getBalance())
        .createdAt(null)
        .updatedAt(null)
        .build();
  }
}

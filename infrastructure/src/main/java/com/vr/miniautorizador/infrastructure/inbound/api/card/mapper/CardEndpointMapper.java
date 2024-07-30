package com.vr.miniautorizador.infrastructure.inbound.api.card.mapper;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.infrastructure.inbound.api.card.dto.CardEndpointRequest;
import com.vr.miniautorizador.infrastructure.inbound.api.card.dto.CardEndpointResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardEndpointMapper {
  public static Card toEntity(CardEndpointRequest card) {
    return Card.builder()
        .number(card.getNumber())
        .password(card.getPassword())
        .cvv(card.getCvv())
        .expirationDate(card.getExpirationDate())
        .ownerName(card.getOwnerName())
        .balance(card.getBalance())
        .build();
  }

  public static CardEndpointResponse toResponse(Card card) {
    return CardEndpointResponse.builder()
        .id(card.getId())
        .number(card.getNumber())
        .password(card.getPassword())
        .cvv(card.getCvv())
        .expirationDate(card.getExpirationDate())
        .ownerName(card.getOwnerName())
        .balance(card.getBalance())
        .build();
  }
}

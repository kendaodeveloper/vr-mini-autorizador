package com.vr.miniautorizador.infrastructure.inbound.api.transaction.mapper;

import com.vr.miniautorizador.domain.entities.transaction.Transaction;
import com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto.TransactionEndpointRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionEndpointMapper {
  public static Transaction toEntity(TransactionEndpointRequest transaction) {
    return Transaction.builder()
        .cardNumber(transaction.getCardNumber())
        .cardPassword(transaction.getCardPassword())
        .amount(transaction.getAmount())
        .build();
  }
}

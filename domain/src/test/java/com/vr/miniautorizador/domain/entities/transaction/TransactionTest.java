package com.vr.miniautorizador.domain.entities.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionTest {
  @Test
  public void instantiateCardWithNonNullValues() {
    final var id = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "374245455400126";
    final var amount = new BigDecimal("10.25");

    final var card = Transaction.builder()
        .id(id)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    Assertions.assertNotNull(card);
    Assertions.assertEquals(card.getId(), id);
    Assertions.assertEquals(card.getCardNumber(), cardNumber);
    Assertions.assertEquals(card.getCardPassword(), cardPassword);
    Assertions.assertEquals(card.getAmount(), amount);
  }

  @Test
  public void instantiateCardWithNullValues() {
    final var card = Transaction.builder()
        .id(null)
        .cardNumber(null)
        .cardPassword(null)
        .amount(null)
        .build();

    Assertions.assertNotNull(card);
    Assertions.assertNull(card.getId());
    Assertions.assertNull(card.getCardNumber());
    Assertions.assertNull(card.getCardPassword());
    Assertions.assertNull(card.getAmount());
  }
}

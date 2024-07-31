package com.vr.miniautorizador.domain.entities.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardAggregateTest {
  @Test
  public void instantiateCardAggregateWithValidations() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("475.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var cardAggregate = CardAggregate.of(card);

    cardAggregate.setDefaultCardBalanceValueIfNecessary();
    cardAggregate.encryptCardPassword();

    Assertions.assertNotNull(card);
    Assertions.assertEquals(cardAggregate.getCard().getId(), id);
    Assertions.assertEquals(cardAggregate.getCard().getNumber(), number);
    Assertions.assertEquals(cardAggregate.getCard().getPassword(), "C5CTUVUj7A9V9zYoL8kHAw==");
    Assertions.assertEquals(cardAggregate.getCard().getCvv(), cvv);
    Assertions.assertEquals(cardAggregate.getCard().getExpirationDate(), expirationDate);
    Assertions.assertEquals(cardAggregate.getCard().getOwnerName(), ownerName);
    Assertions.assertEquals(cardAggregate.getCard().getBalance(), new BigDecimal(500));
    Assertions.assertEquals(cardAggregate.getCard().getCreatedAt(), createdAt);
    Assertions.assertEquals(cardAggregate.getCard().getUpdatedAt(), updatedAt);
  }
}

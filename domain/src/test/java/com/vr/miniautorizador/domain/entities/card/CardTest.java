package com.vr.miniautorizador.domain.entities.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardTest {
  @Test
  public void instantiateCardWithNonNullValues() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
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

    Assertions.assertNotNull(card);
    Assertions.assertEquals(card.getId(), id);
    Assertions.assertEquals(card.getNumber(), number);
    Assertions.assertEquals(card.getPassword(), password);
    Assertions.assertEquals(card.getCvv(), cvv);
    Assertions.assertEquals(card.getExpirationDate(), expirationDate);
    Assertions.assertEquals(card.getOwnerName(), ownerName);
    Assertions.assertEquals(card.getBalance(), balance);
    Assertions.assertEquals(card.getCreatedAt(), createdAt);
    Assertions.assertEquals(card.getUpdatedAt(), updatedAt);
  }

  @Test
  public void instantiateCardWithNullValues() {
    final var card = Card.builder()
        .id(null)
        .number(null)
        .password(null)
        .cvv(null)
        .expirationDate(null)
        .ownerName(null)
        .balance(null)
        .createdAt(null)
        .updatedAt(null)
        .build();

    Assertions.assertNotNull(card);
    Assertions.assertNull(card.getId());
    Assertions.assertNull(card.getNumber());
    Assertions.assertNull(card.getPassword());
    Assertions.assertNull(card.getCvv());
    Assertions.assertNull(card.getExpirationDate());
    Assertions.assertNull(card.getOwnerName());
    Assertions.assertNull(card.getBalance());
    Assertions.assertNull(card.getCreatedAt());
    Assertions.assertNull(card.getUpdatedAt());
  }
}

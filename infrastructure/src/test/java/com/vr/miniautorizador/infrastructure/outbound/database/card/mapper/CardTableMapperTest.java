package com.vr.miniautorizador.infrastructure.outbound.database.card.mapper;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardTableMapperTest {
  @Test
  public void mapFromTableToEntityWithSuccess() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var cardTable = CardTable.builder()
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

    final var card = CardTableMapper.toEntity(cardTable);

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
  public void mapFromEntityToTableWithId() {
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

    final var cardTable = CardTableMapper.toTable(card);

    Assertions.assertNotNull(cardTable);
    Assertions.assertEquals(cardTable.getId(), id);
    Assertions.assertEquals(cardTable.getNumber(), number);
    Assertions.assertEquals(cardTable.getPassword(), password);
    Assertions.assertEquals(cardTable.getCvv(), cvv);
    Assertions.assertEquals(cardTable.getExpirationDate(), expirationDate);
    Assertions.assertEquals(cardTable.getOwnerName(), ownerName);
    Assertions.assertEquals(cardTable.getBalance(), balance);
    Assertions.assertNull(cardTable.getCreatedAt());
    Assertions.assertNull(cardTable.getUpdatedAt());
  }

  @Test
  public void mapFromEntityToTableWithoutId() {
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(null)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var cardTable = CardTableMapper.toTable(card);

    Assertions.assertNotNull(cardTable);
    Assertions.assertNotNull(cardTable.getId());
    Assertions.assertEquals(cardTable.getNumber(), number);
    Assertions.assertEquals(cardTable.getPassword(), password);
    Assertions.assertEquals(cardTable.getCvv(), cvv);
    Assertions.assertEquals(cardTable.getExpirationDate(), expirationDate);
    Assertions.assertEquals(cardTable.getOwnerName(), ownerName);
    Assertions.assertEquals(cardTable.getBalance(), balance);
    Assertions.assertNull(cardTable.getCreatedAt());
    Assertions.assertNull(cardTable.getUpdatedAt());
  }
}

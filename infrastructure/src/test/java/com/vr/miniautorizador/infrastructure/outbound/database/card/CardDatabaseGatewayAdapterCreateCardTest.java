package com.vr.miniautorizador.infrastructure.outbound.database.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.infrastructure.outbound.database.card.exceptions.CardAlreadyExistsException;
import com.vr.miniautorizador.infrastructure.outbound.database.card.repository.CardRepository;
import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class CardDatabaseGatewayAdapterCreateCardTest {
  @InjectMocks
  private CardDatabaseGatewayAdapter adapter;
  @Mock
  private CardRepository cardRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createCardWhenIsCardNumberNotExists() {
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

    Mockito.doReturn(Optional.empty()).when(this.cardRepository).findOneByNumber(number);
    Mockito.doReturn(cardTable).when(this.cardRepository).saveAndFlush(Mockito.any(CardTable.class));

    final var response = this.adapter.createCard(card);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getId(), cardTable.getId());
    Assertions.assertEquals(response.getNumber(), cardTable.getNumber());
    Assertions.assertEquals(response.getPassword(), cardTable.getPassword());
    Assertions.assertEquals(response.getCvv(), cardTable.getCvv());
    Assertions.assertEquals(response.getExpirationDate(), cardTable.getExpirationDate());
    Assertions.assertEquals(response.getOwnerName(), cardTable.getOwnerName());
    Assertions.assertEquals(response.getBalance(), cardTable.getBalance());
    Assertions.assertEquals(response.getCreatedAt(), cardTable.getCreatedAt());
    Assertions.assertNotNull(response.getUpdatedAt());
  }

  @Test
  public void createCardWhenIsCardNumberExists() {
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

    Mockito.doReturn(Optional.of(cardTable)).when(this.cardRepository).findOneByNumber(number);

    Assertions.assertThrows(CardAlreadyExistsException.class, () -> this.adapter.createCard(card));
  }

  @Test
  public void getCardByNumberWhenExists() {
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

    Mockito.doReturn(Optional.of(cardTable)).when(this.cardRepository).findOneByNumber(number);

    final var response = this.adapter.getCardByNumber(number);

    Assertions.assertNotNull(response);
    Assertions.assertTrue(response.isPresent());
    Assertions.assertEquals(response.get().getId(), cardTable.getId());
    Assertions.assertEquals(response.get().getNumber(), cardTable.getNumber());
    Assertions.assertEquals(response.get().getPassword(), cardTable.getPassword());
    Assertions.assertEquals(response.get().getCvv(), cardTable.getCvv());
    Assertions.assertEquals(response.get().getExpirationDate(), cardTable.getExpirationDate());
    Assertions.assertEquals(response.get().getOwnerName(), cardTable.getOwnerName());
    Assertions.assertEquals(response.get().getBalance(), cardTable.getBalance());
    Assertions.assertEquals(response.get().getCreatedAt(), cardTable.getCreatedAt());
    Assertions.assertNotNull(response.get().getUpdatedAt());
  }

  @Test
  public void getCardByNumberWhenNotExists() {
    final var number = "374245455400126";

    Mockito.doReturn(Optional.empty()).when(this.cardRepository).findOneByNumber(number);

    final var response = this.adapter.getCardByNumber(number);

    Assertions.assertNotNull(response);
    Assertions.assertFalse(response.isPresent());
  }

  @Test
  public void updateCardBalanceWhenHasSufficientFunds() {
    final var id = UUID.randomUUID();
    final var amount = new BigDecimal(10.25);

    Mockito.doReturn(1).when(this.cardRepository).updateCardBalanceIfSufficient(id, amount);

    final var response = this.adapter.updateCardBalanceById(id, amount);

    Assertions.assertNotNull(response);
    Assertions.assertTrue(response);
  }

  @Test
  public void updateCardBalanceWhenHasInsufficientFunds() {
    final var id = UUID.randomUUID();
    final var amount = new BigDecimal(10.25);

    Mockito.doReturn(0).when(this.cardRepository).updateCardBalanceIfSufficient(id, amount);

    final var response = this.adapter.updateCardBalanceById(id, amount);

    Assertions.assertNotNull(response);
    Assertions.assertFalse(response);
  }
}

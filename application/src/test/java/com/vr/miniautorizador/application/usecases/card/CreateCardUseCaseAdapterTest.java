package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.util.PasswordUtil;
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

public class CreateCardUseCaseAdapterTest {
  @InjectMocks
  private CreateCardUseCaseAdapter createCardUseCaseAdapter;
  @Mock
  private GetCardByNumberGatewayPort getCardByNumberGatewayPort;
  @Mock
  private CreateCardGatewayPort createCardGatewayPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createCardWhenNumberAlreadyExists() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var requestedCard = Card.builder()
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

    final var existingCard = Card.builder()
        .id(id)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.of(existingCard)).when(this.getCardByNumberGatewayPort).getCardByNumber(requestedCard.getNumber());

    final var response = this.createCardUseCaseAdapter.execute(requestedCard);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getSecond(), true);
    Assertions.assertEquals(response.getFirst().getId(), existingCard.getId());
    Assertions.assertEquals(response.getFirst().getNumber(), existingCard.getNumber());
    Assertions.assertEquals(response.getFirst().getPassword(), existingCard.getPassword());
    Assertions.assertEquals(response.getFirst().getCvv(), existingCard.getCvv());
    Assertions.assertEquals(response.getFirst().getExpirationDate(), existingCard.getExpirationDate());
    Assertions.assertEquals(response.getFirst().getOwnerName(), existingCard.getOwnerName());
    Assertions.assertEquals(response.getFirst().getBalance(), existingCard.getBalance());
    Assertions.assertNotNull(response.getFirst().getCreatedAt());
    Assertions.assertNotNull(response.getFirst().getUpdatedAt());
  }

  @Test
  public void createCardWhenNumberNotExistsAndBalanceIsNull() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var requestedCard = Card.builder()
        .id(null)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(null)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = Card.builder()
        .id(id)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(new BigDecimal(500))
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.empty()).when(this.getCardByNumberGatewayPort).getCardByNumber(requestedCard.getNumber());
    Mockito.doReturn(persistedCard).when(this.createCardGatewayPort).createCard(requestedCard);

    final var response = this.createCardUseCaseAdapter.execute(requestedCard);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getSecond(), false);
    Assertions.assertEquals(response.getFirst().getId(), persistedCard.getId());
    Assertions.assertEquals(response.getFirst().getNumber(), persistedCard.getNumber());
    Assertions.assertEquals(response.getFirst().getPassword(), persistedCard.getPassword());
    Assertions.assertEquals(response.getFirst().getCvv(), persistedCard.getCvv());
    Assertions.assertEquals(response.getFirst().getExpirationDate(), persistedCard.getExpirationDate());
    Assertions.assertEquals(response.getFirst().getOwnerName(), persistedCard.getOwnerName());
    Assertions.assertEquals(response.getFirst().getBalance(), persistedCard.getBalance());
    Assertions.assertNotNull(response.getFirst().getCreatedAt());
    Assertions.assertNotNull(response.getFirst().getUpdatedAt());
  }

  @Test
  public void createCardWhenNumberNotExistsAndBalanceIsGreaterThan500() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var requestedCard = Card.builder()
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

    final var persistedCard = Card.builder()
        .id(id)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.empty()).when(this.getCardByNumberGatewayPort).getCardByNumber(requestedCard.getNumber());
    Mockito.doReturn(persistedCard).when(this.createCardGatewayPort).createCard(requestedCard);

    final var response = this.createCardUseCaseAdapter.execute(requestedCard);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getSecond(), false);
    Assertions.assertEquals(response.getFirst().getId(), persistedCard.getId());
    Assertions.assertEquals(response.getFirst().getNumber(), persistedCard.getNumber());
    Assertions.assertEquals(response.getFirst().getPassword(), persistedCard.getPassword());
    Assertions.assertEquals(response.getFirst().getCvv(), persistedCard.getCvv());
    Assertions.assertEquals(response.getFirst().getExpirationDate(), persistedCard.getExpirationDate());
    Assertions.assertEquals(response.getFirst().getOwnerName(), persistedCard.getOwnerName());
    Assertions.assertEquals(response.getFirst().getBalance(), persistedCard.getBalance());
    Assertions.assertNotNull(response.getFirst().getCreatedAt());
    Assertions.assertNotNull(response.getFirst().getUpdatedAt());
  }
}

package com.vr.miniautorizador.application.usecases.transaction;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.entities.transaction.Transaction;
import com.vr.miniautorizador.domain.entities.transaction.TransactionStatus;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.UpdateCardBalanceByIdGatewayPort;
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

public class CreateTransactionUseCaseAdapterTest {
  @InjectMocks
  private CreateTransactionUseCaseAdapter createTransactionUseCaseAdapter;
  @Mock
  private GetCardByNumberGatewayPort getCardByNumberGatewayPort;
  @Mock
  private UpdateCardBalanceByIdGatewayPort updateCardBalanceByIdGatewayPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void tryToCreateTransactionWhenCardIsNotFound() {
    final var id = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "12345";
    final var amount = new BigDecimal("10.25");

    final var transaction = Transaction.builder()
        .id(id)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    Mockito.doReturn(Optional.empty()).when(this.getCardByNumberGatewayPort).getCardByNumber(transaction.getCardNumber());

    final var response = this.createTransactionUseCaseAdapter.execute(transaction);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, TransactionStatus.CARD_NOT_FOUND.getText());
  }

  @Test
  public void tryToCreateTransactionWhenPasswordIsWrong() {
    final var transactionId = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "12345";
    final var amount = new BigDecimal("10.25");

    final var transaction = Transaction.builder()
        .id(transactionId)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    final var cardId = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(cardId)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.of(card)).when(this.getCardByNumberGatewayPort).getCardByNumber(transaction.getCardNumber());

    final var response = this.createTransactionUseCaseAdapter.execute(transaction);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, TransactionStatus.INVALID_CARD_PASSWORD.getText());
  }

  @Test
  public void tryToCreateTransactionWhenIsInsufficientFunds() {
    final var transactionId = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal("10.25");

    final var transaction = Transaction.builder()
        .id(transactionId)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    final var cardId = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(cardId)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.of(card)).when(this.getCardByNumberGatewayPort).getCardByNumber(transaction.getCardNumber());
    Mockito.doReturn(Boolean.FALSE).when(this.updateCardBalanceByIdGatewayPort).updateCardBalanceById(cardId, transaction.getAmount());

    final var response = this.createTransactionUseCaseAdapter.execute(transaction);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, TransactionStatus.INSUFFICIENT_FUNDS.getText());
  }

  @Test
  public void tryToCreateTransactionWhenIsOk() {
    final var transactionId = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal("10.25");

    final var transaction = Transaction.builder()
        .id(transactionId)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    final var cardId = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(cardId)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.of(card)).when(this.getCardByNumberGatewayPort).getCardByNumber(transaction.getCardNumber());
    Mockito.doReturn(Boolean.TRUE).when(this.updateCardBalanceByIdGatewayPort).updateCardBalanceById(cardId, transaction.getAmount());

    final var response = this.createTransactionUseCaseAdapter.execute(transaction);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, TransactionStatus.CREATED.getText());
  }

  @Test
  public void tryToCreateTransactionWhenIsUnexpectedError() {
    final var transactionId = UUID.randomUUID();
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal("10.25");

    final var transaction = Transaction.builder()
        .id(transactionId)
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    Mockito.doThrow(new RuntimeException("Unexpected Error")).when(this.getCardByNumberGatewayPort).getCardByNumber(transaction.getCardNumber());

    final var response = this.createTransactionUseCaseAdapter.execute(transaction);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, TransactionStatus.UNEXPECTED_ERROR.getText());
  }
}

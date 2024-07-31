package com.vr.miniautorizador.domain.entities.transaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionStatusTest {
  @Test
  public void validateTransactionStatusWhenIsCreated() {
    Assertions.assertEquals(TransactionStatus.CREATED.getText(), "OK");
  }

  @Test
  public void validateTransactionStatusWhenIsInsufficientFunds() {
    Assertions.assertEquals(TransactionStatus.INSUFFICIENT_FUNDS.getText(), "SALDO_INSUFICIENTE");
  }

  @Test
  public void validateTransactionStatusWhenIsCardNotFound() {
    Assertions.assertEquals(TransactionStatus.CARD_NOT_FOUND.getText(), "CARTAO_INEXISTENTE");
  }

  @Test
  public void validateTransactionStatusWhenIsInvalidCardPassword() {
    Assertions.assertEquals(TransactionStatus.INVALID_CARD_PASSWORD.getText(), "SENHA_INVALIDA");
  }

  @Test
  public void validateTransactionStatusWhenIsUnexpectedError() {
    Assertions.assertEquals(TransactionStatus.UNEXPECTED_ERROR.getText(), "ERRO_INESPERADO");
  }
}

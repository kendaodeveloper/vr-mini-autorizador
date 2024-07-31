package com.vr.miniautorizador.domain.entities.transaction;

public enum TransactionStatus {
  CREATED("OK"),
  INSUFFICIENT_FUNDS("SALDO_INSUFICIENTE"),
  INVALID_CARD_PASSWORD("SENHA_INVALIDA"),
  CARD_NOT_FOUND("CARTAO_INEXISTENTE"),
  UNEXPECTED_ERROR("ERRO_INESPERADO");

  private final String text;

  TransactionStatus(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}


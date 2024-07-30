package com.vr.miniautorizador.application.usecases.transaction;

import com.vr.miniautorizador.application.usecases.transaction.exceptions.InsufficientFundsException;
import com.vr.miniautorizador.application.usecases.transaction.exceptions.InvalidCardPasswordException;
import com.vr.miniautorizador.application.usecases.transaction.exceptions.TransactionCardNotFoundException;
import com.vr.miniautorizador.domain.entities.transaction.Transaction;
import com.vr.miniautorizador.domain.ports.in.transaction.CreateTransactionUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.UpdateCardBalanceByIdGatewayPort;
import com.vr.miniautorizador.domain.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateTransactionUseCaseAdapter implements CreateTransactionUseCasePort {
  private final GetCardByNumberGatewayPort getCardByNumberGatewayPort;
  private final UpdateCardBalanceByIdGatewayPort updateCardBalanceByIdGatewayPort;

  @Override
  public String execute(Transaction transaction) {
    final var card =
        this.getCardByNumberGatewayPort.getCardByNumber(transaction.getCardNumber())
            .orElseThrow(TransactionCardNotFoundException::new);

    if (!(PasswordUtil.encrypt(transaction.getCardPassword()).equals(card.getPassword()))) {
      throw new InvalidCardPasswordException();
    }

    final var balance = card.getBalance().subtract(transaction.getValue());

    if (balance.compareTo(new BigDecimal(0)) < 0) {
      throw new InsufficientFundsException();
    }

    this.updateCardBalanceByIdGatewayPort.updateCardBalanceById(card.getId(), balance);

    return "OK";
  }
}

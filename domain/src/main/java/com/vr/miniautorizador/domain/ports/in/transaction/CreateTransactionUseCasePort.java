package com.vr.miniautorizador.domain.ports.in.transaction;

import com.vr.miniautorizador.domain.entities.transaction.Transaction;

public interface CreateTransactionUseCasePort {
  String execute(Transaction transaction);
}

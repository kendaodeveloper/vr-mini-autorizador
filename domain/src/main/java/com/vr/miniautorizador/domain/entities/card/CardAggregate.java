package com.vr.miniautorizador.domain.entities.card;

import com.vr.miniautorizador.domain.entities.base.AggregateRoot;
import com.vr.miniautorizador.domain.util.PasswordUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CardAggregate extends AggregateRoot {
  private static final BigDecimal MINIMUM_CARD_BALANCE = new BigDecimal(500);

  private Card card;

  public static CardAggregate of(Card card) {
    return new CardAggregate(card);
  }

  public void setDefaultCardBalanceValueIfNecessary() {
    if (this.card.getBalance() == null || this.card.getBalance().compareTo(MINIMUM_CARD_BALANCE) < 0) {
      this.card.setBalance(MINIMUM_CARD_BALANCE);
    }
  }

  public void encryptCardPassword() {
    this.card.setPassword(PasswordUtil.encrypt(this.card.getPassword()));
  }
}

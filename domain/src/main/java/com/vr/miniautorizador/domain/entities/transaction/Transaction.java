package com.vr.miniautorizador.domain.entities.transaction;

import com.vr.miniautorizador.domain.entities.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transaction extends Entity<UUID> implements Serializable {
  private String cardNumber;
  private String cardPassword;
  private BigDecimal value;
}

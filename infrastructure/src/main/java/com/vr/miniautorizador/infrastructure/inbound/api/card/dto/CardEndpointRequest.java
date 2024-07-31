package com.vr.miniautorizador.infrastructure.inbound.api.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEndpointRequest {
  @Schema(example = "6549873025634501")
  @Size(max = 25)
  @NotNull
  @NotEmpty
  @JsonProperty("numeroCartao")
  private String number;
  @Schema(example = "1234")
  @Size(max = 10)
  @NotNull
  @NotEmpty
  @JsonProperty("senha")
  private String password;
  @Schema(example = "001")
  @Size(max = 5)
  @JsonProperty("cvv")
  private String cvv;
  @Schema(example = "01/25")
  @Size(max = 5)
  @JsonProperty("dataExpiracao")
  private String expirationDate;
  @Schema(example = "Maria")
  @Size(max = 50)
  @JsonProperty("nomeDono")
  private String ownerName;
  @Schema(example = "500")
  @JsonProperty("saldo")
  private BigDecimal balance;
}

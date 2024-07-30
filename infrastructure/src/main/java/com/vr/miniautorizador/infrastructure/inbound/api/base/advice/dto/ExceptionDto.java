package com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
  private Integer code;
  private String message;
  private List<String> errors;
}

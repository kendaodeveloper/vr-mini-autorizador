package com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto;

import com.vr.miniautorizador.domain.exceptions.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ExceptionDtoTest {
  @Test
  public void instantiateExceptionDtoWithNonNullValues() {
    final var status = HttpStatus.CONFLICT.value();
    final var exception = new ConflictException("Entity already exists");
    final var dto = new ExceptionDto(
        status,
        exception.toString(),
        List.of(exception.toString())
    );

    Assertions.assertNotNull(dto);
    Assertions.assertEquals(dto.getCode(), status);
    Assertions.assertEquals(dto.getMessage(), exception.toString());
    Assertions.assertEquals(dto.getErrors(), List.of(exception.toString()));
  }

  @Test
  public void instantiateExceptionDtoWithNullValues() {
    final var dto = new ExceptionDto(
        null,
        null,
        null
    );

    Assertions.assertNotNull(dto);
    Assertions.assertNull(dto.getCode());
    Assertions.assertNull(dto.getMessage());
    Assertions.assertNull(dto.getErrors());
  }
}

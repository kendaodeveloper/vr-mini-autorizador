package com.vr.miniautorizador.infrastructure.base.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class JwtUtilTest {
  @Test
  public void generateAndValidateJwtWithSuccess() {
    final var key = UUID.randomUUID();
    final var auth = JwtUtil.generateEncodedJwt(key);

    Assertions.assertNotNull(auth);
    Assertions.assertTrue(JwtUtil.isValidJwt(key, auth));
  }

  @Test
  public void isNotValidJwtWhenParamIsNull() {
    final var key = UUID.randomUUID();

    Assertions.assertFalse(JwtUtil.isValidJwt(key, null));
  }

  @Test
  public void isNotValidJwtWhenParamIsNotNull() {
    final var key = UUID.randomUUID();

    Assertions.assertFalse(JwtUtil.isValidJwt(key, "xpto"));
  }
}

package com.vr.miniautorizador.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordUtilTest {
  @Test
  public void encryptPasswordWithSuccess() {
    final var request = "123";
    final var response = PasswordUtil.encrypt(request);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response, "3nndjdBVG15Z9Js6IrBCWA==");
  }
}

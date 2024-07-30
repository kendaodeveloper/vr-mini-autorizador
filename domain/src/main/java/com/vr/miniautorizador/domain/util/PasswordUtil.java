package com.vr.miniautorizador.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordUtil {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES";
  private static final UUID SECRET_KEY = UUID.fromString("e5d6273a-005b-4b87-a9be-6213e405a859");

  public static String encrypt(String input) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = sha.digest(SECRET_KEY.toString().getBytes(StandardCharsets.UTF_8));
      SecretKey key = new SecretKeySpec(hashBytes, ALGORITHM);

      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] encryptedBytes = cipher.doFinal(input.getBytes());
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Throwable t) {
      System.err.println("Unable to encrypt " + input + ": " + t);
      return input;
    }
  }
}

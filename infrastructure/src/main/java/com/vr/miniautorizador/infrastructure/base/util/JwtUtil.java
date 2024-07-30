package com.vr.miniautorizador.infrastructure.base.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {
  public static String generateEncodedJwt(UUID jwtSecretKey) {
    return "Bearer " +
        Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .addClaims(new HashMap<>() {
              {
                put("foo", "bar");
              }
            }).signWith(SignatureAlgorithm.HS256, jwtSecretKey.toString().getBytes())
            .compact();
  }

  public static Boolean isValidJwt(UUID jwtSecretKey, String authorization) {
    try {
      Jwts.parser()
          .setSigningKey(jwtSecretKey.toString().getBytes())
          .parseClaimsJws(
              authorization == null ?
                  "" : authorization.replace("Bearer ", "").trim()
          );
      return Boolean.TRUE;
    } catch (Throwable t) {
      return Boolean.FALSE;
    }
  }
}

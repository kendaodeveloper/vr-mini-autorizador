package com.vr.miniautorizador.infrastructure.base.configuration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniautorizador.infrastructure.base.util.JwtUtil;
import com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto.ExceptionDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Configuration
public class JwtInterceptorConfiguration extends OncePerRequestFilter {
  private static final List<String> IGNORED_AUTH_PATHS = List.of(
      "/",
      "/docs",
      "/swagger",
      "/swagger-ui",
      "/favicon.ico",
      "/actuator",
      "/actuator/health"
  );

  private final Boolean jwtEnabled;
  private final UUID jwtSecretKey;
  private final ObjectMapper objectMapper;

  public JwtInterceptorConfiguration(
      @Value("${spring.security.jwt.enabled}") final Boolean jwtEnabled,
      @Value("${spring.security.jwt.secret-key}") final UUID jwtSecretKey,
      final ObjectMapper objectMapper
  ) {
    this.jwtEnabled = jwtEnabled;
    this.jwtSecretKey = jwtSecretKey;
    this.objectMapper = objectMapper;
  }

  @Override
  public void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws IOException, ServletException {
    if (
        this.jwtEnabled &&
            !this.isIgnoredPath(request.getRequestURI()) &&
            !JwtUtil.isValidJwt(this.jwtSecretKey, request.getHeader("Authorization"))
    ) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      response.getWriter().write(
          this.objectMapper.writeValueAsString(
              new ExceptionDto(
                  HttpStatus.UNAUTHORIZED.value(),
                  "Invalid Token",
                  List.of("Invalid Token")
              )
          )
      );
      response.getWriter().flush();

      return;
    }

    filterChain.doFilter(request, response);
  }

  private Boolean isIgnoredPath(String url) {
    if (
        url.toLowerCase().startsWith("/swagger-ui/") ||
            url.toLowerCase().startsWith("/v3/api-docs")
    ) {
      return Boolean.TRUE;
    }

    return JwtInterceptorConfiguration.IGNORED_AUTH_PATHS.stream()
        .anyMatch(path -> url.toLowerCase().equals(path));
  }
}

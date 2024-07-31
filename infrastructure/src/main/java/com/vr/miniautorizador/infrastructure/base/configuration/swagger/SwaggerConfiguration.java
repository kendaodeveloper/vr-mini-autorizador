package com.vr.miniautorizador.infrastructure.base.configuration.swagger;

import com.vr.miniautorizador.infrastructure.base.enumerable.Environment;
import com.vr.miniautorizador.infrastructure.base.util.JwtUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class SwaggerConfiguration {
  private final Environment env;
  private final Boolean jwtEnabled;
  private final UUID jwtSecretKey;

  public SwaggerConfiguration(
      @Value("${spring.application.env}") final Environment env,
      @Value("${spring.security.jwt.enabled}") final Boolean jwtEnabled,
      @Value("${spring.security.jwt.secret-key}") final UUID jwtSecretKey
  ) {
    this.env = env;
    this.jwtEnabled = jwtEnabled;
    this.jwtSecretKey = jwtSecretKey;
  }

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(
        new Info()
            .title("VR - API de Gerenciamento de cartões e transações")
            .description(
                (this.jwtEnabled && Environment.LOCAL.equals(this.env)) ?
                    ("Valid Token for tests:" + "<br/>" + JwtUtil.generateEncodedJwt(this.jwtSecretKey)) : ""
            )
            .version("v1.0.0")
    ).addSecurityItem(
        new SecurityRequirement().addList("apiKey")
    ).components(
        new Components()
            .addSecuritySchemes("apiKey",
                new SecurityScheme()
                    .name("Authorization")
                    .in(SecurityScheme.In.HEADER)
                    .type(SecurityScheme.Type.APIKEY)
            )
    );
  }
}

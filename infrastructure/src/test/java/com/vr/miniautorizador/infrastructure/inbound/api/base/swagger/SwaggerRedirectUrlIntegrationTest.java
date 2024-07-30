package com.vr.miniautorizador.infrastructure.inbound.api.base.swagger;

import com.vr.miniautorizador.infrastructure.base.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerRedirectUrlIntegrationTest {
  @Value("${spring.security.jwt.secret-key}")
  private UUID jwtSecretKey;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void accessSwaggerByDefaultPath() throws Throwable {
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andReturn();
  }

  @Test
  public void accessSwaggerByDocsPath() throws Throwable {
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/docs")
        )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andReturn();
  }

  @Test
  public void accessSwaggerBySwaggerPath() throws Throwable {
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/swagger")
        )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andReturn();
  }

  @Test
  public void accessSwaggerBySwaggerUiPath() throws Throwable {
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/swagger-ui")
        )
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andReturn();
  }

  @Test
  public void doNotAccessSwaggerWhenIsUsingInvalidPath() throws Throwable {
    this.mockMvc.perform(
            MockMvcRequestBuilders.get("/xpto")
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andReturn();
  }
}

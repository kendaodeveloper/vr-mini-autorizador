package com.vr.miniautorizador.infrastructure.inbound.api.base.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerRedirectUrl {
  @RequestMapping({"/", "/docs", "/swagger", "/swagger-ui"})
  public String redirect() {
    return "redirect:/swagger-ui/index.html";
  }
}

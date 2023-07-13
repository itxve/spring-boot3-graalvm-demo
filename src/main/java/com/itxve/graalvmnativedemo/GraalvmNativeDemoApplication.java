package com.itxve.graalvmnativedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@SpringBootApplication
@RestController
@Slf4j
public class GraalvmNativeDemoApplication {

  @RequestMapping("/")
  String home(ServerWebExchange exchange) {
    log.info("{}", exchange.getRequest().getHeaders());
    return "Hello World!";
  }

  public static void main(String[] args) {
    SpringApplication.run(GraalvmNativeDemoApplication.class, args);
  }

}

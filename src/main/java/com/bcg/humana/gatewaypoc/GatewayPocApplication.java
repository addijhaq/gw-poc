package com.bcg.humana.gatewaypoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GatewayPocApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayPocApplication.class, args);
  }
}

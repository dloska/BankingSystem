package com.spring.config;

import com.spring.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:persistence-sqlserver-test.properties")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:test-config.properties")
@ComponentScan(basePackageClasses = {Application.class})
class ApplicationConfigTest {

  @Configuration
  @Import(ApplicationConfig.class)
  static class InnerConfiguration {

  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
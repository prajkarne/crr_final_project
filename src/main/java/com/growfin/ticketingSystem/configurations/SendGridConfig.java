package com.growfin.ticketingSystem.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

/**
 * SendGridConfig class is responsible for configuring the SendGrid API key.
 */
@Configuration
public class SendGridConfig {

    // Injects the SendGrid API key from application.properties
    @Value("${sendgrid.api.key}")
    String sendGridAPIKey;

    /**
     * Creates and returns a SendGrid bean with the configured API key.
     * 
     * @return SendGrid
     */
    @Bean
    public SendGrid getSendgridKey() {
        return new SendGrid(sendGridAPIKey);
    }
}

package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * AdminResponseRequest class represents the payload for admin responses to
 * tickets.
 */
public class AdminResponseRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String message;

    /**
     * Gets the response message.
     * 
     * @return String representing the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     * 
     * @param message String representing the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

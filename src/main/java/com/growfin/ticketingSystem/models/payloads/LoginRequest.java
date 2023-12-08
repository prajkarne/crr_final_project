package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.NotBlank;

/**
 * LoginRequest class represents the payload for user login requests.
 */
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    /**
     * Gets the user's email.
     * 
     * @return String representing the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email String representing the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * 
     * @return String representing the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password String representing the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

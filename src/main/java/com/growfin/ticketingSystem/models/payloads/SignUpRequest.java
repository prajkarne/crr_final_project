package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * SignUpRequest class represents the payload for user registration requests.
 */
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String secret;

    /**
     * Gets the user's first name.
     * 
     * @return String representing the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     * 
     * @param firstName String representing the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the user's last name.
     * 
     * @return String representing the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     * 
     * @param lastName String representing the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    /**
     * Gets the secret associated with the user.
     * 
     * @return String representing the user's secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret associated with the user.
     * 
     * @param secret String representing the user's secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}

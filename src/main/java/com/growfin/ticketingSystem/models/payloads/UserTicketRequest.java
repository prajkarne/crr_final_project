package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * UserTicketRequest class represents the payload for creating a user ticket.
 */
public class UserTicketRequest {

    @NotBlank
    @Size(min = 1, max = 500, message = "Max description 500 characters")
    private String description;

    @NotBlank
    @Size(min = 1, max = 50, message = "Max title 50 characters")
    private String title;

    @NotBlank
    @Size(max = 100)
    private String secret;

    @NotBlank
    @Email
    private String addedBy;

    /**
     * Default constructor.
     */
    public UserTicketRequest() {
        super();
    }

    /**
     * Constructor with parameters.
     */
    public UserTicketRequest(
            @NotBlank @Size(min = 1, max = 500, message = "Max description 500 characters") String description,
            @NotBlank @Size(min = 1, max = 50, message = "Max title 50 characters") String title,
            @NotBlank @Size(max = 100) String secret, @NotBlank @Email String addedBy) {
        super();
        this.description = description;
        this.title = title;
        this.secret = secret;
        this.addedBy = addedBy;
    }

    /**
     * Gets the ticket description.
     * 
     * @return String representing the ticket description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the ticket description.
     * 
     * @param description String representing the ticket description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the ticket title.
     * 
     * @return String representing the ticket title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the ticket title.
     * 
     * @param title String representing the ticket title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the secret associated with the ticket.
     * 
     * @return String representing the ticket secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Sets the secret associated with the ticket.
     * 
     * @param secret String representing the ticket secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets the email of the user who added the ticket.
     * 
     * @return String representing the user's email
     */
    public String getAddedBy() {
        return addedBy;
    }

    /**
     * Sets the email of the user who added the ticket.
     * 
     * @param addedBy String representing the user's email
     */
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}

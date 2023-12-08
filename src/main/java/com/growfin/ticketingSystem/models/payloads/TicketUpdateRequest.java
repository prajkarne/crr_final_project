package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * TicketUpdateRequest class represents the payload for updating ticket details.
 */
public class TicketUpdateRequest {

    @NotBlank
    @Size(min = 1, max = 500, message = "Max description 500 characters")
    private String description;

    @NotBlank
    @Size(min = 1, max = 50, message = "Max title 50 characters")
    private String title;

    @NotBlank
    private String adminId;

    @NotBlank
    private String status;

    @NotBlank
    private String orgId;

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
     * Gets the admin ID associated with the ticket.
     * 
     * @return String representing the admin ID
     */
    public String getAdminId() {
        return adminId;
    }

    /**
     * Sets the admin ID associated with the ticket.
     * 
     * @param adminId String representing the admin ID
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    /**
     * Gets the ticket status.
     * 
     * @return String representing the ticket status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the ticket status.
     * 
     * @param status String representing the ticket status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the organization ID associated with the ticket.
     * 
     * @return String representing the organization ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * Sets the organization ID associated with the ticket.
     * This is a Hack, and should ideally be taken from Admin's principal.
     * 
     * @param orgId String representing the organization ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}

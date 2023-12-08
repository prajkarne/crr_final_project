package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * StatusChangeRequest class represents the payload for changing the status of a
 * ticket.
 */
public class StatusChangeRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String status;

    /**
     * Gets the new status.
     * 
     * @return String representing the new status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the new status.
     * 
     * @param status String representing the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

package com.growfin.ticketingSystem.models.payloads;

import javax.validation.constraints.NotBlank;

/**
 * OrganizationRequest class represents the payload for organization
 * registration requests.
 */
public class OrganizationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String domain;

    /**
     * Gets the organization name.
     * 
     * @return String representing the organization name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the organization name.
     * 
     * @param name String representing the organization name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the organization domain.
     * 
     * @return String representing the organization domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the organization domain.
     * 
     * @param domain String representing the organization domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
}

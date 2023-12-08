package com.growfin.ticketingSystem.models.payloads;

/**
 * TicketStatus enum represents the possible statuses for a ticket.
 */
public enum TicketStatus {

    OPEN("OPEN"),
    WAITING_ON_CUSTOMER("WAITING_ON_CUSTOMER"),
    CUSTOMER_RESPONDED("CUSTOMER_RESPONDED"),
    RESOLVED("RESOLVED"),
    CLOSED("CLOSED");

    private final String name;

    TicketStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the ticket status.
     * 
     * @return String representing the name of the ticket status
     */
    public String getName() {
        return name;
    }
}

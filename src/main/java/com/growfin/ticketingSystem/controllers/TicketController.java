package com.growfin.ticketingSystem.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.growfin.ticketingSystem.models.Ticket;
import com.growfin.ticketingSystem.models.payloads.AdminResponseRequest;
import com.growfin.ticketingSystem.models.payloads.StatusChangeRequest;
import com.growfin.ticketingSystem.models.payloads.TicketUpdateRequest;
import com.growfin.ticketingSystem.models.payloads.UserTicketRequest;
import com.growfin.ticketingSystem.services.OrganizationSecretMappingService;
import com.growfin.ticketingSystem.services.TicketService;

/**
 * TicketController class handles HTTP requests related to tickets.
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketservice;

    @Autowired
    private OrganizationSecretMappingService organizationSecretMappingService;

    /**
     * Handles POST requests to create a new ticket.
     * 
     * @param userTicketRequest UserTicketRequest payload containing ticket details
     * @return ResponseEntity with ticket creation response and HTTP status
     */
    @PostMapping("")
    public ResponseEntity<?> createTicket(@RequestBody UserTicketRequest userTicketRequest) {

        ResponseEntity<?> response;

        if (!organizationSecretMappingService.existsBySecret(userTicketRequest.getSecret())) {
            response = new ResponseEntity<>("Wrong Secrets", HttpStatus.UNAUTHORIZED);
        } else {
            response = new ResponseEntity<>(ticketservice.createTicket(userTicketRequest), HttpStatus.OK);
        }

        return response;
    }

    /**
     * Handles POST requests to update an existing ticket.
     * 
     * @param ticketId            String representing the ticket's ID
     * @param ticketUpdateRequest TicketUpdateRequest payload containing updated
     *                            ticket details
     * @return ResponseEntity with ticket update response and HTTP status
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable("id") String ticketId,
            @RequestBody TicketUpdateRequest ticketUpdateRequest) {

        ResponseEntity<?> response;
        Optional<Ticket> ticket = ticketservice.findById(ticketId);

        if (ticket.isPresent()) {
            response = new ResponseEntity<>(ticketservice.updateTicket(ticketId, ticketUpdateRequest), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Ticket with the given ID not found", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Handles GET requests to retrieve a specific ticket by ID.
     * 
     * @param ticketId String representing the ticket's ID
     * @return ResponseEntity with the retrieved ticket and HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicket(@PathVariable("id") String ticketId) {

        ResponseEntity<?> response;
        Optional<Ticket> ticket = ticketservice.findById(ticketId);

        if (ticket.isPresent()) {
            response = new ResponseEntity<>(ticket.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Ticket with the given ID not found", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * Handles GET requests to retrieve tickets based on query parameters.
     * 
     * @param adminEmail    String representing the admin's email
     * @param status        String representing the ticket's status
     * @param customerEmail String representing the customer's email
     * @return ResponseEntity with the list of tickets matching the query parameters
     *         and HTTP status
     */
    @GetMapping("")
    public ResponseEntity<?> getAllByFilter(@RequestParam("adminEmail") String adminEmail,
            @RequestParam("status") String status, @RequestParam("customerEmail") String customerEmail) {

        ResponseEntity<?> response = new ResponseEntity<>(
                ticketservice.getTicketByQueryParam(adminEmail, status, customerEmail), HttpStatus.OK);

        return response;
    }

    /**
     * Handles POST requests to update the status of a ticket.
     * 
     * @param ticketId String representing the ticket's ID
     * @param status   StatusChangeRequest payload containing the new ticket status
     * @return ResponseEntity with the updated ticket and HTTP status
     */
    @PostMapping("/{id}/change_status")
    public ResponseEntity<Ticket> updateStatus(@PathVariable("id") String ticketId,
            @RequestBody StatusChangeRequest status) {

        return new ResponseEntity<Ticket>(ticketservice.updateStatus(ticketId, status.getStatus()), HttpStatus.OK);
    }

    /**
     * Handles POST requests to add a response to a ticket by an admin.
     * 
     * @param ticketId      String representing the ticket's ID
     * @param adminResponse AdminResponseRequest payload containing the admin's
     *                      response
     * @return ResponseEntity with the updated ticket and HTTP status
     */
    @PostMapping("/{id}/respond")
    public ResponseEntity<?> addResponse(@PathVariable("id") String ticketId,
            @RequestBody AdminResponseRequest adminResponse) {

        ResponseEntity<?> response;
        Optional<Ticket> ticket = ticketservice.findById(ticketId);

        if (!ticket.isPresent()) {
            response = new ResponseEntity<>("Ticket with the given ID not found", HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(ticketservice.addResponseToTicket(ticketId, adminResponse), HttpStatus.OK);
        }

        return response;
    }
}

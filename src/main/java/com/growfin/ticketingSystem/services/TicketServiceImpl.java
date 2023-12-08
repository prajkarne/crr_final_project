package com.growfin.ticketingSystem.services;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.growfin.ticketingSystem.models.AdminResponse;
import com.growfin.ticketingSystem.models.Administrator;
import com.growfin.ticketingSystem.models.Organization;
import com.growfin.ticketingSystem.models.Ticket;
import com.growfin.ticketingSystem.models.payloads.AdminResponseRequest;
import com.growfin.ticketingSystem.models.payloads.TicketStatus;
import com.growfin.ticketingSystem.models.payloads.TicketUpdateRequest;
import com.growfin.ticketingSystem.models.payloads.UserTicketRequest;
import com.growfin.ticketingSystem.repositories.AdminResponseRepository;
import com.growfin.ticketingSystem.repositories.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	AdministratorService administratorService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	private JobScheduler jobScheduler;

	@Autowired
	AdminResponseRepository adminResponseRepository;

	@Autowired
	EmailService emailService;

	/**
	 * Creates a new ticket based on the user's ticket request.
	 * 
	 * @param userTicketRequest The user's ticket request.
	 * @return The created ticket.
	 */
	@Override
	public Ticket createTicket(UserTicketRequest userTicketRequest) {

		Organization organization = organizationService.findOrgBySecret(userTicketRequest.getSecret());
		assertTrue("Invalid Org ID, Secret Mapping", organization != null);

		Administrator adminToBeAssigned = administratorService.findAvailableAdmin(organization.getId(),
				TicketStatus.OPEN.toString());

		Optional<Organization> org = organizationService.findById(organization.getId());
		assertTrue("Invalid Org ID", org.isPresent());

		Ticket ticket = new Ticket(userTicketRequest.getDescription(), userTicketRequest.getTitle(),
				TicketStatus.OPEN.toString().toString(), adminToBeAssigned, org.get(), userTicketRequest.getAddedBy());

		return upsertTicket(ticket);
	}

	/**
	 * Updates the status of a ticket.
	 * 
	 * @param ticketId The ID of the ticket to be updated.
	 * @param status   The new status of the ticket.
	 * @return The updated ticket.
	 */
	@Override
	public Ticket updateStatus(String ticketId, String status) {
		Ticket ticket = ticketRepository.findById(ticketId).get();

		assertTrue(TicketStatus.valueOf(status) != null);

		ticket.setStatus(TicketStatus.valueOf(status).toString());

		return upsertTicket(ticket);
	}

	/**
	 * Updates the details of a ticket.
	 * 
	 * @param ticketId            The ID of the ticket to be updated.
	 * @param ticketUpdateRequest The updated details of the ticket.
	 * @return The updated ticket.
	 */
	@Override
	public Ticket updateTicket(String ticketId, TicketUpdateRequest ticketUpdateRequest) {

		Optional<Administrator> adminIdToBeAssigned = administratorService.findById(ticketUpdateRequest.getAdminId());
		assertTrue("Invalid Admin_id, Admin Not Found", adminIdToBeAssigned.isPresent());

		Optional<Organization> org = organizationService.findById(ticketUpdateRequest.getOrgId());
		assertTrue("Invalid Org ID", org.isPresent());

		Ticket persistedTicket = ticketRepository.findById(ticketId).get();

		Ticket ticket = new Ticket(ticketId, ticketUpdateRequest.getDescription(), ticketUpdateRequest.getTitle(),
				TicketStatus.valueOf(ticketUpdateRequest.getStatus()).toString(), adminIdToBeAssigned.get(), org.get());

		persistedTicket.setOrganization(ticket.getOrganization());
		persistedTicket.setDescription(ticket.getDescription());
		persistedTicket.setTitle(ticket.getTitle());
		persistedTicket.setAdministrator(ticket.getAdministrator());
		persistedTicket.setStatus(ticket.getStatus());

		return upsertTicket(persistedTicket);

	}

	/**
	 * Retrieves a ticket by its ID.
	 * 
	 * @param ticketId The ID of the ticket to be retrieved.
	 * @return The ticket with the specified ID.
	 */
	@Override
	public Optional<Ticket> findById(String ticketId) {

		return ticketRepository.findById(ticketId);

	}

	/**
	 * Upserts a ticket, either by creating a new one or updating an existing one.
	 * 
	 * @param ticket The ticket to be upserted.
	 * @return The upserted ticket.
	 */
	@Override
	public Ticket upsertTicket(Ticket ticket) {

		if (ticket.getId() != null) {

			if (ticket.getStatus() == TicketStatus.CLOSED.toString()) {

				ticket.setClosedAt(new Date());

			}
			if (ticket.getStatus() == TicketStatus.RESOLVED.toString()) {

				scheduleAJob(ticket.getId(), TicketStatus.CLOSED.toString());

			}
		}

		return ticketRepository.save(ticket);

	}

	/**
	 * Schedules a job to update the status of a ticket after a specified delay.
	 * 
	 * @param ticketId  The ID of the ticket.
	 * @param newStatus The new status to be set after the delay.
	 */
	private void scheduleAJob(String ticketId, String newStatus) {

		jobScheduler.schedule(() -> updateStatus(ticketId, newStatus), LocalDateTime.now().plusSeconds(30));

	}

	/**
	 * Adds a response to a ticket and notifies the user via email.
	 * 
	 * @param ticketId             The ID of the ticket.
	 * @param adminResponseRequest The admin's response to the ticket.
	 * @return The added admin response.
	 */
	@Override
	public AdminResponse addResponseToTicket(String ticketId, AdminResponseRequest adminResponseRequest) {

		Ticket ticket = ticketRepository.findById(ticketId).get();

		AdminResponse adminResponse = new AdminResponse(ticket, adminResponseRequest.getMessage());

		AdminResponse persistedAdminResponse = adminResponseRepository.save(adminResponse);

		emailService.sendTextEmail("yogesh@sinecycle.com", ticket.getAddedBy(), "Reply to your Ticket",
				adminResponseRequest.getMessage());

		updateStatus(ticketId, TicketStatus.WAITING_ON_CUSTOMER.toString());

		return persistedAdminResponse;
	}

	/**
	 * Retrieves tickets based on specified query parameters.
	 * 
	 * @param assignedTo The administrator assigned to the ticket.
	 * @param status     The status of the ticket.
	 * @param customer   The customer who created the ticket.
	 * @return List of tickets matching the query parameters.
	 */
	@Override
	public List<Ticket> getTicketByQueryParam(String assignedTo, String status, String customer) {
		List<Ticket> tickets = ticketRepository.getTicketByQueryParam(assignedTo, status, customer);
		return tickets;
	}

}

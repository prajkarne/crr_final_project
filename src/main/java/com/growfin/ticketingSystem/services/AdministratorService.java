package com.growfin.ticketingSystem.services;

import java.util.List;
import java.util.Optional;

import com.growfin.ticketingSystem.models.Administrator;
import com.growfin.ticketingSystem.models.payloads.SignUpRequest;

/**
 * Service interface for Administrator-related operations.
 */
public interface AdministratorService {

    /**
     * Creates a new Administrator based on the provided SignUpRequest.
     *
     * @param signUpRequest The SignUpRequest containing admin details
     * @return The created Administrator
     */
    Administrator createAdmin(SignUpRequest signUpRequest);

    /**
     * Finds an available Administrator based on organization ID and status.
     *
     * @param orgId  The ID of the organization
     * @param status The status of the Administrator
     * @return The available Administrator
     */
    Administrator findAvailableAdmin(String orgId, String status);

    /**
     * Finds the first Administrator for the given organization ID.
     *
     * @param orgId The ID of the organization
     * @return The first Administrator
     */
    Administrator findFirstAdminstrator(String orgId);

    /**
     * Finds an Administrator by their ID.
     *
     * @param id The ID of the Administrator
     * @return An Optional containing the Administrator, or empty if not found
     */
    Optional<Administrator> findById(String id);

    /**
     * Checks if an Administrator with the given email exists.
     *
     * @param email The email to check
     * @return True if an Administrator with the email exists, false otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Finds an Administrator by their email.
     *
     * @param email The email of the Administrator
     * @return The Administrator with the given email
     */
    Administrator findByEmail(String email);

    /**
     * Finds all Administrators.
     *
     * @return A list of all Administrators
     */
    List<Administrator> findAll();
}

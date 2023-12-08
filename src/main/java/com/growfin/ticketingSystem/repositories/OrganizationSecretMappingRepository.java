package com.growfin.ticketingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growfin.ticketingSystem.models.OrganizationSecretMapping;

/**
 * Repository interface for the OrganizationSecretMapping entity.
 */
@Repository("organizationSecretMappingRepository")
public interface OrganizationSecretMappingRepository extends JpaRepository<OrganizationSecretMapping, String> {

    /**
     * Check if an organization secret mapping exists by its ID.
     *
     * @param secretId The ID of the secret.
     * @return True if the organization secret mapping with the given ID exists,
     *         otherwise false.
     */
    boolean existsById(String secretId);

    /**
     * Check if an organization secret mapping exists by its secret.
     *
     * @param secret The secret to check for existence.
     * @return True if the organization secret mapping with the given secret exists,
     *         otherwise false.
     */
    boolean existsBySecret(String secret);
}

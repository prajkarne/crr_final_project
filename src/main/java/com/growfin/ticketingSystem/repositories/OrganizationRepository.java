package com.growfin.ticketingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.growfin.ticketingSystem.models.Organization;

/**
 * Repository interface for the Organization entity.
 */
@Repository("organizationRepository")
public interface OrganizationRepository extends JpaRepository<Organization, String> {

    /**
     * Custom query to find an organization by its secret.
     *
     * @param secret The secret to search for.
     * @return The organization associated with the given secret.
     */
    @Query(nativeQuery = true, value = "SELECT org.* FROM organizations org JOIN organization_secret_mappings org_mapping ON org.secret_mapping_id = org_mapping.id WHERE org_mapping.secret= (:secret)")
    Organization findOrgBySecret(@Param("secret") String secret);
}

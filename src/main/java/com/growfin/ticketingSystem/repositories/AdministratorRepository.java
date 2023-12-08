package com.growfin.ticketingSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.growfin.ticketingSystem.models.Administrator;

/**
 * Repository interface for the Administrator entity.
 */
@Repository("administratorRepository")
public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    /**
     * Find an administrator by email.
     *
     * @param email the email to search for
     * @return the administrator with the given email or null if not found
     */
    Administrator findByEmail(String email);

    /**
     * Check if an administrator with the given email exists.
     *
     * @param email the email to check for
     * @return true if an administrator with the given email exists, false otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Find the ID of an available administrator for a given organization and
     * status.
     *
     * @param orgId  the ID of the organization
     * @param status the status to filter by
     * @return the ID of an available administrator or null if none is found
     */
    @Query(nativeQuery = true, value = "SELECT admin_id FROM tickets tic WHERE tic.org_id = (:orgId) and tic.status = (:status) group by tic.admin_id order by count(*) ASC limit 1")
    String findAvailableAdmin(@Param("orgId") String orgId, @Param("status") String status);

    /**
     * Find the first administrator for a given organization.
     *
     * @param orgId the ID of the organization
     * @return the first administrator for the organization or null if none is found
     */
    @Query(nativeQuery = true, value = "SELECT * FROM administrators admin WHERE admin.org_id = (:orgId) limit 1")
    Administrator findFirstAdminstrator(@Param("orgId") String orgId);
}

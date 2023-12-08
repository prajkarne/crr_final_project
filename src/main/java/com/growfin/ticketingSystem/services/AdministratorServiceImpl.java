package com.growfin.ticketingSystem.services;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.growfin.ticketingSystem.models.Administrator;
import com.growfin.ticketingSystem.models.Organization;
import com.growfin.ticketingSystem.models.payloads.SignUpRequest;
import com.growfin.ticketingSystem.repositories.AdministratorRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	AdministratorRepository administratorRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OrganizationService organizationService;

	/**
	 * Creates a new administrator based on the provided sign-up request.
	 *
	 * @param signUpRequest The sign-up request containing administrator details.
	 * @return The newly created administrator.
	 */
	@Override
	public Administrator createAdmin(SignUpRequest signUpRequest) {
		Organization organization = organizationService.findOrgBySecret(signUpRequest.getSecret());
		assertTrue("Invalid Org ID, Secret Mapping", organization != null);

		Administrator admin = new Administrator(signUpRequest.getFirstName(), signUpRequest.getLastName(),
				signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), organization);

		return administratorRepository.save(admin);
	}

	/**
	 * Checks if an administrator with the given email already exists.
	 *
	 * @param email The email to check.
	 * @return True if an administrator with the given email exists, false
	 *         otherwise.
	 */
	@Override
	public Boolean existsByEmail(String email) {
		return administratorRepository.existsByEmail(email);
	}

	/**
	 * Retrieves an administrator by email.
	 *
	 * @param email The email of the administrator.
	 * @return The administrator with the given email.
	 */
	@Override
	public Administrator findByEmail(String email) {
		return administratorRepository.findByEmail(email);
	}

	/**
	 * Retrieves an administrator by ID.
	 *
	 * @param id The ID of the administrator.
	 * @return The administrator with the given ID.
	 */
	@Override
	public Optional<Administrator> findById(String id) {
		return administratorRepository.findById(id);
	}

	/**
	 * Retrieves all administrators.
	 *
	 * @return A list of all administrators.
	 */
	@Override
	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	/**
	 * Finds an available administrator based on the provided organization ID and
	 * status.
	 * If no available administrator is found, it returns the first administrator
	 * for the organization.
	 *
	 * @param orgId  The ID of the organization.
	 * @param status The status for which an administrator is needed.
	 * @return The available administrator or the first administrator for the
	 *         organization.
	 */
	@Override
	public Administrator findAvailableAdmin(String orgId, String status) {
		String adminId = administratorRepository.findAvailableAdmin(orgId, status);

		Administrator admin = null;

		if (adminId == null) {
			admin = findFirstAdminstrator(orgId);
		} else {
			admin = administratorRepository.findById(adminId).orElse(null);
		}

		return admin;
	}

	/**
	 * Finds the first administrator for the provided organization ID.
	 *
	 * @param orgId The ID of the organization.
	 * @return The first administrator for the organization.
	 */
	@Override
	public Administrator findFirstAdminstrator(String orgId) {
		return administratorRepository.findFirstAdminstrator(orgId);
	}

}

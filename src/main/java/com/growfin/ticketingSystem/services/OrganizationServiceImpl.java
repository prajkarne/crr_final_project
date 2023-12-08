package com.growfin.ticketingSystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.growfin.ticketingSystem.models.Organization;
import com.growfin.ticketingSystem.models.OrganizationSecretMapping;
import com.growfin.ticketingSystem.models.payloads.OrganizationRequest;
import com.growfin.ticketingSystem.repositories.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	OrganizationSecretMappingService organizationSecretMappingService;

	/**
	 * Upsert (Insert or Update) an organization with the provided details.
	 *
	 * @param organizationRequest The details of the organization to upsert.
	 * @return The updated or newly created Organization.
	 */
	@Override
	public Organization upsertOrg(OrganizationRequest organizationRequest) {
		OrganizationSecretMapping organizationSecretMapping = organizationSecretMappingService.upsertOrg();
		Organization org = new Organization(organizationRequest.getName(), organizationRequest.getDomain(),
				organizationSecretMapping);
		return organizationRepository.save(org);
	}

	/**
	 * Retrieve an organization by its ID.
	 *
	 * @param id The ID of the organization.
	 * @return An Optional containing the Organization if found, or empty otherwise.
	 */
	@Override
	public Optional<Organization> findById(String id) {
		return organizationRepository.findById(id);
	}

	/**
	 * Find an organization by its secret.
	 *
	 * @param secret The secret to search for.
	 * @return The Organization associated with the specified secret, or null if not
	 *         found.
	 */
	@Override
	public Organization findOrgBySecret(String secret) {
		return organizationRepository.findOrgBySecret(secret);
	}

}

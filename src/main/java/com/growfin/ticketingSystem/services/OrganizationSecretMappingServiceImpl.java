package com.growfin.ticketingSystem.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.growfin.ticketingSystem.models.OrganizationSecretMapping;
import com.growfin.ticketingSystem.repositories.OrganizationSecretMappingRepository;

@Service
public class OrganizationSecretMappingServiceImpl implements OrganizationSecretMappingService {

	@Autowired
	OrganizationSecretMappingRepository organizationSecretMappingRepository;

	/**
	 * Upsert (Insert or Update) an organization secret mapping.
	 *
	 * @return The updated or newly created OrganizationSecretMapping.
	 */
	@Override
	public OrganizationSecretMapping upsertOrg() {
		OrganizationSecretMapping organizationSecretMapping = new OrganizationSecretMapping();
		organizationSecretMapping.setSecret(generateSecret(50));

		return organizationSecretMappingRepository.save(organizationSecretMapping);
	}

	/**
	 * Retrieve an organization secret mapping by its ID.
	 *
	 * @param id The ID of the organization secret mapping.
	 * @return An Optional containing the OrganizationSecretMapping if found, or
	 *         empty otherwise.
	 */
	@Override
	public Optional<OrganizationSecretMapping> findById(String id) {
		return organizationSecretMappingRepository.findById(id);
	}

	/**
	 * Generate a random secret of the specified size.
	 *
	 * @param size The size of the secret to generate.
	 * @return The generated secret.
	 */
	private String generateSecret(int size) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = size;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}

	/**
	 * Check if an organization secret mapping exists for the specified secret.
	 *
	 * @param secret The secret to check.
	 * @return True if the organization secret mapping exists, false otherwise.
	 */
	@Override
	public boolean existsBySecret(String secret) {
		return organizationSecretMappingRepository.existsBySecret(secret);
	}
}

package com.growfin.ticketingSystem.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.growfin.ticketingSystem.models.Organization;
import com.growfin.ticketingSystem.models.payloads.JwtAuthenticationResponse;
import com.growfin.ticketingSystem.models.payloads.LoginRequest;
import com.growfin.ticketingSystem.models.payloads.OrganizationRequest;
import com.growfin.ticketingSystem.models.payloads.SignUpRequest;
import com.growfin.ticketingSystem.security.JwtTokenProvider;
import com.growfin.ticketingSystem.services.AdministratorService;
import com.growfin.ticketingSystem.services.CustomUserDetailsService;
import com.growfin.ticketingSystem.services.OrganizationSecretMappingService;
import com.growfin.ticketingSystem.services.OrganizationService;

import org.springframework.stereotype.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * AuthenticationController class handles authentication-related HTTP requests.
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationSecretMappingService organizationSecretMappingService;

    /**
     * Handles POST requests for authenticating admin users.
     * 
     * @param loginRequest LoginRequest payload containing email and password
     * @return ResponseEntity with JWT authentication response and HTTP status
     * @throws Exception if an error occurs during authentication
     */
    @PostMapping("/admin/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(loginRequest.getEmail());

        String jwt = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Handles POST requests for registering admin users.
     * 
     * @param signUpRequest SignUpRequest payload containing user details
     * @return ResponseEntity with registration response and HTTP status
     */
    @PostMapping("/admin/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        ResponseEntity<?> response;

        if (!organizationSecretMappingService.existsBySecret(signUpRequest.getSecret())) {
            response = new ResponseEntity<>("Wrong Secrets", HttpStatus.UNAUTHORIZED);
        } else if (administratorService.existsByEmail(signUpRequest.getEmail())) {
            response = new ResponseEntity<>("Email Address already in use", HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>(administratorService.createAdmin(signUpRequest), HttpStatus.OK);
        }

        return response;
    }

    /**
     * Handles POST requests for registering organizations.
     * 
     * @param organizationRequest OrganizationRequest payload containing
     *                            organization details
     * @return ResponseEntity with organization registration response and HTTP
     *         status
     */
    @PostMapping("/org/signup")
    public ResponseEntity<?> registerOrg(@Valid @RequestBody OrganizationRequest organizationRequest) {
        return new ResponseEntity<Organization>(organizationService.upsertOrg(organizationRequest), HttpStatus.OK);
    }
}

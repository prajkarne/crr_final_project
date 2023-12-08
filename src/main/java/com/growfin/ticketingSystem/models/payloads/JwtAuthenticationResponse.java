package com.growfin.ticketingSystem.models.payloads;

/**
 * JwtAuthenticationResponse class represents the payload for JWT authentication
 * response.
 */
public class JwtAuthenticationResponse {

    private final String jwt;

    /**
     * Constructs a JwtAuthenticationResponse with the given JWT.
     * 
     * @param jwt String representing the JWT
     */
    public JwtAuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Gets the JWT.
     * 
     * @return String representing the JWT
     */
    public String getJwt() {
        return jwt;
    }
}

package com.growfin.ticketingSystem.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.BeanIds;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.growfin.ticketingSystem.security.JwtAuthenticationFilter;
import com.growfin.ticketingSystem.services.CustomUserDetailsService;

/**
 * SecurityConfiguration class configures the security settings for the
 * application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Creates and returns a JwtAuthenticationFilter bean.
     * 
     * @return JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Exposes the AuthenticationManager bean.
     * 
     * @return AuthenticationManager
     * @throws Exception if an error occurs
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Creates and returns a PasswordEncoder bean using BCrypt.
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationManagerBuilder with customUserDetailsService and
     * passwordEncoder.
     * 
     * @param authenticationManagerBuilder AuthenticationManagerBuilder
     * @throws Exception if an error occurs
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures HttpSecurity for various URL patterns and filters.
     * 
     * @param httpSecurity HttpSecurity
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                // Permit access to static resources
                .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
                        "/**/*.css", "/**/*.js")
                .permitAll()
                // Permit access to authentication-related endpoints
                .antMatchers("/api/auth/**").permitAll()
                // Permit access to specific endpoints without authentication
                .antMatchers("/auth/admin/signin", "/auth/admin/signup", "/auth/org/signup", "/tickets").permitAll()
                // Require authentication for any other request
                .anyRequest().authenticated();

        // Add JwtAuthenticationFilter before the default
        // UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

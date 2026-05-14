package com.salesianostriana.dam.coworkspace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login").permitAll()
				.requestMatchers("/admin/**", "/usuarios/**", "/espacios/**", "/reservas/**").authenticated()
				.anyRequest().permitAll())
				.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/admin", true).permitAll())
				.logout(logout -> logout.logoutSuccessUrl("/").permitAll());

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {

		var admin = User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build();

		return new InMemoryUserDetailsManager(admin);
	}

}
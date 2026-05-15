package com.salesianostriana.dam.coworkspace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/index", "/login", "/css/**", "/js/**", "/img/**")
				.permitAll().requestMatchers("/admin/**", "/usuarios/**", "/espacios/**", "/reservas/**")
				.authenticated().anyRequest().authenticated())

				.formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/admin", true).permitAll())

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

		var admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();

		return new InMemoryUserDetailsManager(admin);
	}

}
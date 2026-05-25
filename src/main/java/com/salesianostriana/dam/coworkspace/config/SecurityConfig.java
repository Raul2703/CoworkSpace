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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		RequestCache requestCache = new NullRequestCache();

		http.authorizeHttpRequests(auth -> auth

				.requestMatchers("/", "/index", "/login", "/espacios", "/css/**", "/js/**", "/img/**", "/error",
						"/h2-console/**")
				.permitAll()

				.requestMatchers("/admin/**", "/usuarios/borrar/**", "/usuarios/editar/**", "/usuarios/nuevo/**",
						"/usuarios/guardar/**", "/espacios/nuevo/**", "/espacios/guardar/**", "/espacios/borrar/**",
						"/espacios/editar/**", "/reservas", "/reservas/", "/reservas/borrar/**", "/reservas/editar/**")
				.hasRole("ADMIN")

				.requestMatchers("/reservas/nuevo", "/reservas/guardar", "/reservas/confirmada")
				.hasAnyRole("ADMIN", "USER")

				.anyRequest().authenticated())

				.requestCache(cache -> cache.requestCache(requestCache))

				.formLogin(login -> login.loginPage("/login").successHandler(authenticationSuccessHandler).permitAll())

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").permitAll());

		http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

		var admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();

		var user = User.builder().username("user").password(passwordEncoder.encode("user")).roles("USER").build();

		return new InMemoryUserDetailsManager(admin, user);
	}

}
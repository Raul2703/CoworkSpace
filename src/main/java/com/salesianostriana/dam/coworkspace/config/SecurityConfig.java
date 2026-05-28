package com.salesianostriana.dam.coworkspace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.salesianostriana.dam.coworkspace.repository.UsuarioRepository;

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
						"/registro", "/h2-console/**")
				.permitAll()

				.requestMatchers("/admin/**", "/usuarios/borrar/**", "/usuarios/editar/**", "/usuarios/nuevo/**",
						"/usuarios/guardar/**", "/espacios/nuevo/**", "/espacios/guardar/**", "/espacios/borrar/**",
						"/espacios/editar/**", "/reservas", "/reservas/", "/reservas/borrar/**",
						"/reservas/editar/**", "/reservas/*/estado")
				.hasRole("ADMIN")

				.requestMatchers("/reservas/nuevo", "/reservas/guardar", "/reservas/confirmada", "/mis-reservas",
						"/mis-reservas/pdf")
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
	UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {

		return username -> usuarioRepository.findByNombreIgnoreCase(username)
				.map(usuario -> org.springframework.security.core.userdetails.User.builder()
						.username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getRol()).build())
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

}	

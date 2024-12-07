package com.examenfinal.FabrizioValerSanchez.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.examenfinal.FabrizioValerSanchez.serviceImpl.UserServiceImplement;

import lombok.AllArgsConstructor;

@Configuration

public class SecurityConfig {
	private final UserServiceImplement dao;
	private final UserDetailsService userDetailsService;
	private final JWTAuthorizationFilter jwtAuthorizationFilter;
	
	public SecurityConfig(UserServiceImplement dao, UserDetailsService userDetailsService,
			JWTAuthorizationFilter jwtAuthorizationFilter) {
		super();
		this.dao = dao;
		this.userDetailsService = userDetailsService;
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

	    JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(dao);
	    jwtAuthenticationFilter.setAuthenticationManager(authManager);
	    jwtAuthenticationFilter.setFilterProcessesUrl("/login");

	    return http
	            .cors()
	            .and()
	            .csrf().disable()
	            .authorizeHttpRequests()
	                // Permitir acceso público a /usuarios/** antes de cualquier otra regla de seguridad
	                .antMatchers("/usuarios/**").permitAll() 
	                // Requerir autenticación para todas las demás rutas
	                .anyRequest().authenticated()
	            .and()
	            .httpBasic()
	            .and()
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .addFilter(jwtAuthenticationFilter) // Este filtro debe ser después de la regla de public routes
	            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();
	}

	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager memoryManager = new InMemoryUserDetailsManager();
		memoryManager.createUser(
				User
				.withUsername("javier")
				.password(passwordEncoder().encode("password"))
				.roles()
				.build());
		return memoryManager;
	}
	*/
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
			
	}
	
	//METODO PARA ENCRYPTAR Y VER LA PASSWORD
	/*
	public static void main(String[] args) {
		System.out.println("Pasword: "+ new BCryptPasswordEncoder().encode("cibertec"));
	}
	*/
	
	
	
}

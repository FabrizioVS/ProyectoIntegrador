package com.examenfinal.FabrizioValerSanchez.security;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.examenfinal.FabrizioValerSanchez.model.Auth;
import com.examenfinal.FabrizioValerSanchez.model.Usuario;
import com.examenfinal.FabrizioValerSanchez.serviceImpl.UserDetailImplement;
import com.examenfinal.FabrizioValerSanchez.serviceImpl.UserServiceImplement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.examenfinal.FabrizioValerSanchez.util.Token;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserServiceImplement dao;

	@Autowired
	public JWTAuthenticationFilter(UserServiceImplement dao) {
		this.dao = dao;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		Auth authCredenciales = new Auth();

		try {
			authCredenciales = new ObjectMapper().readValue(request.getReader(), Auth.class);
		} catch (Exception e) {
		}

		UsernamePasswordAuthenticationToken userPAT = new UsernamePasswordAuthenticationToken(
				authCredenciales.getEmail(), authCredenciales.getPassword(), Collections.emptyList());

		return getAuthenticationManager().authenticate(userPAT);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserDetailImplement userDetails = (UserDetailImplement) authResult.getPrincipal();
		String name = userDetails.getNombre();
	    String email = userDetails.getUsername();
		
		
		String token = Token.crearToken(name, email);
		
		
		
		response.addHeader("Authorization", "Bearer " + token);
		
		dao.actualizarFechaAlaActual(name);
		
		response.getWriter().flush();

		super.successfulAuthentication(request, response, chain, authResult);
	}

}

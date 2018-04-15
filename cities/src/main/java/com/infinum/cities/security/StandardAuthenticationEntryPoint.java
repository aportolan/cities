package com.infinum.cities.security;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StandardAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

	

		ObjectMapper mapper = new ObjectMapper();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
		responseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Writer out = responseWrapper.getWriter();
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setLoggedIn(false);
		loginResponse.setError(authException.getMessage());
		mapper.writeValue(out, loginResponse);
		out.close();
	
	}
}
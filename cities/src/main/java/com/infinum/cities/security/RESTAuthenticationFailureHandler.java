package com.infinum.cities.security;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();
		super.onAuthenticationFailure(request, response, exception);
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
		responseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Writer out = responseWrapper.getWriter();
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setLoggedIn(false);
		loginResponse.setError(exception.getMessage());
		mapper.writeValue(out, loginResponse);
		out.close();
	}
}
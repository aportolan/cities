package com.infinum.cities.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinum.cities.dto.SecUserDTO;
import com.infinum.cities.service.UserService;

public class RESTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	public RESTAuthenticationFilter(RequestMatcher loginPage, AuthenticationManager authenticationManager,
			UserService userService) {
		super(loginPage);
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override 	
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		if (!request.getMethod().equals(HttpMethod.POST.name())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		final String accessToken = request.getHeader(HeaderToken.X_AUTH_TOKEN.getValue());
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("", "");
		SecUserDTO user = getUser(request);
		if (null != accessToken)
			user = userService.getUserByToken(accessToken);
		else
			user = userService.getUser(user);

		authRequest = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword());
		// if auth request is empty no token exists and no user exists

		// PUT into the authentication request's details
		setDetails(request, authRequest);

		return authenticationManager.authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();
		SecUserDTO user = getUser(request);
		if (user == null)
			this.unsuccessfulAuthentication(request, response, new AuthenticationServiceException(
					"Invalid authentication request! Please send json in {username:x password:y} format!"));
		SavedRequestAwareAuthenticationSuccessHandler srh = new SavedRequestAwareAuthenticationSuccessHandler();
		this.setAuthenticationSuccessHandler(srh);
		srh.setRedirectStrategy(new RedirectStrategy() {
			@Override
			public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
					throws IOException {
				logger.debug("No redirection!");
				// No redirection

			}
		});

		super.successfulAuthentication(request, response, null, authResult);

		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
		responseWrapper.setContentType(MediaType.APPLICATION_JSON_VALUE);
		Writer out = responseWrapper.getWriter();
		responseWrapper.setHeader(HeaderToken.X_AUTH_TOKEN.getValue(), user.getToken());
		mapper.writeValue(out, new LoginResponse());
		out.close();

	}

	private SecUserDTO getUser(ServletRequest request) {
		// BufferedReader reader = null;
		InputStream inputStream = null;
		ObjectMapper mapper = new ObjectMapper();
		SecUserDTO user = new SecUserDTO();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// READ JSON FROM REQUEST
		try {
			inputStream = request.getInputStream();
			// reader = request.getReader();
			user = mapper.readValue(inputStream, SecUserDTO.class);
		} catch (IOException ex) {
			logger.error("AUTHENTICATION FAILED - request : " + ex);
		} finally {
			try {
				inputStream.close();
			} catch (IOException ex) {
				logger.error("AUTHENTICATION FAILED - request: " + ex);
			}
		}

		return user;
	}

}
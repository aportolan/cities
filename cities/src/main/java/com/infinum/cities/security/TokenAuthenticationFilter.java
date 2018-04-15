package com.infinum.cities.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.infinum.cities.dto.SecUserDTO;
import com.infinum.cities.service.UserService;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private UserService userService;
	private UserToUserDetails userToUserDetails;

	public TokenAuthenticationFilter(UserService userService,UserToUserDetails userToUserDetails) {
		super();
		this.userService = userService;
		this.userToUserDetails = userToUserDetails;
	};

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		final String accessToken = httpRequest.getHeader(HeaderToken.X_AUTH_TOKEN.getValue());
		if (null != accessToken) {
			SecUserDTO user = userService.getUserByToken(accessToken);
			UserDetails details = userToUserDetails.convert(user);
			

			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details,
					details.getPassword(), details.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		chain.doFilter(request, response);

	}

}
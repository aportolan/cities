package com.infinum.cities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.infinum.cities.security.RESTAuthenticationFailureHandler;
import com.infinum.cities.security.RESTAuthenticationFilter;
import com.infinum.cities.security.StandardAuthenticationEntryPoint;
import com.infinum.cities.security.TokenAuthenticationFilter;
import com.infinum.cities.security.UserToUserDetails;
import com.infinum.cities.service.UserService;

@EnableWebSecurity
public class CitiesSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	@Autowired
	private UserToUserDetails userToUserDetails;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		final RESTAuthenticationFailureHandler authenticationFailureHandler = new RESTAuthenticationFailureHandler();
		http.addFilterBefore(getTokenFilter(), BasicAuthenticationFilter.class);
		http.addFilterBefore(jsonFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.defaultAuthenticationEntryPointFor(customAuthenticationEntryPoint(), new AntPathRequestMatcher("/**"));

		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.formLogin()
			.failureHandler(authenticationFailureHandler);
		http.authorizeRequests()
			.antMatchers("/public/**","/token", "/", "/login","/h2-console/**")
			.permitAll()
			.antMatchers("/**")
			.fullyAuthenticated()
			.and()
			.logout()
			.permitAll()
			.and()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.csrf()
			.disable();
		// @formatter:on


	}
	private RESTAuthenticationFilter jsonFilter() throws Exception {
		return new RESTAuthenticationFilter(new AntPathRequestMatcher("/login"), authenticationManager(), userService);
	}
	private TokenAuthenticationFilter getTokenFilter() throws Exception {
		return new TokenAuthenticationFilter(userService,userToUserDetails);
	}

	private AuthenticationEntryPoint customAuthenticationEntryPoint() {
		return new StandardAuthenticationEntryPoint();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider authenticationProvider)
			throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
}
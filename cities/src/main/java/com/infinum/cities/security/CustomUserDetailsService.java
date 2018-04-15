package com.infinum.cities.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.infinum.cities.domain.QUser;
import com.infinum.cities.dto.SecUserDTO;
import com.infinum.cities.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserToUserDetails userToUserDetails;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userToUserDetails
				.convert(new SecUserDTO(userRepository.findOne(QUser.user.mail.equalsIgnoreCase(username)).orElse(null)));
	}

}

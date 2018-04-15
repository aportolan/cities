package com.infinum.cities.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.infinum.cities.dto.SecUserDTO;

@Component
public class UserToUserDetails implements Converter<SecUserDTO, UserDetails> {
	@Override
    public UserDetails convert(SecUserDTO user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
 
        if (user != null) {
            userDetails.setUsername(user.getMail());
            userDetails.setPassword(user.getPassword());
            userDetails.setEnabled(true);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
  
            authorities.add(new SimpleGrantedAuthority(DefaultAuthority.USER.name()));
    
            userDetails.setAuthorities(authorities);
        }
 
        return userDetails;
    }
}

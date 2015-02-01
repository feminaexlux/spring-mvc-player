package net.feminaexlux.player.service.security;

import net.feminaexlux.player.exception.MediaPlayerAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationManagerImpl implements AuthenticationManager {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
			String encryptedPassword = passwordEncoder.encode((String) authentication.getCredentials());
			assert userDetails.getPassword().equals(encryptedPassword);

			return new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(),
					authentication.getCredentials(),
					userDetails.getAuthorities());
		} catch (AssertionError | UsernameNotFoundException e) {
			throw new MediaPlayerAuthenticationException("Credentials failed");
		}
	}
}

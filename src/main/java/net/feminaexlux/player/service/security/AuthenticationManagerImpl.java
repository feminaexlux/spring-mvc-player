package net.feminaexlux.player.service.security;

import net.feminaexlux.player.exception.BadPasswordException;
import net.feminaexlux.player.exception.MediaPlayerAuthenticationException;
import net.feminaexlux.player.service.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationManagerImpl implements AuthenticationManager {

	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationManagerImpl.class);

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserLoginService userLoginService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		try {
			UserDetails userDetails = userLoginService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));
			if (!passwordEncoder.matches(String.valueOf(authentication.getCredentials()), userDetails.getPassword())) {
				throw new BadPasswordException("Bad password for user " + authentication.getPrincipal());
			}

			userLoginService.updateLastLogin(userDetails.getUsername());
			return new UsernamePasswordAuthenticationToken(
					authentication.getPrincipal(),
					authentication.getCredentials(),
					userDetails.getAuthorities());
		} catch (AuthenticationException e) {
			LOG.warn(e.getMessage());
			// Throw generic exception
			throw new MediaPlayerAuthenticationException("Credentials failed");
		}
	}
}

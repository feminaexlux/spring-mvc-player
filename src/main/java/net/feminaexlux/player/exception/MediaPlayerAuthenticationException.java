package net.feminaexlux.player.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class MediaPlayerAuthenticationException extends BadCredentialsException {

	public MediaPlayerAuthenticationException(final String message) {
		super(message);
	}

}

package net.feminaexlux.player.exception;

import org.springframework.security.core.AuthenticationException;

public class MediaPlayerAuthenticationException extends AuthenticationException {

	public MediaPlayerAuthenticationException(final String message) {
		super(message);
	}

}

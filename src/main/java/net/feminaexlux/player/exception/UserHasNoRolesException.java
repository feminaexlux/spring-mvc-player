package net.feminaexlux.player.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserHasNoRolesException extends UsernameNotFoundException {

	// This doesn't make a lot of sense but I needed to extend UsernameNotFound
	public UserHasNoRolesException(final String message) {
		super(message);
	}

}

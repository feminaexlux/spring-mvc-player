package net.feminaexlux.player.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserLoginService extends UserDetailsService {

	void updateLastLogin(final String username);

}

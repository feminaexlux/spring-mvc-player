package net.feminaexlux.player.service.security;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private DSLContext database;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	public static final class MediaUserDetails implements UserDetails {

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return null;
		}

		@Override
		public String getPassword() {
			return null;
		}

		@Override
		public String getUsername() {
			return null;
		}

		@Override
		public boolean isAccountNonExpired() {
			return false;
		}

		@Override
		public boolean isAccountNonLocked() {
			return false;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return false;
		}

		@Override
		public boolean isEnabled() {
			return false;
		}

	}

}

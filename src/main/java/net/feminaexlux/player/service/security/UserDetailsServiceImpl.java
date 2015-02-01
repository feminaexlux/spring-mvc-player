package net.feminaexlux.player.service.security;

import net.feminaexlux.player.model.table.Principal;
import net.feminaexlux.player.model.table.PrincipalRole;
import net.feminaexlux.player.model.table.record.PrincipalRecord;
import net.feminaexlux.player.model.table.record.PrincipalRoleRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private DSLContext database;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		PrincipalRecord principal = getPrincipal(username);
		if (principal == null) {
			LOG.warn("Failed to find user by {}", username);
			throw new UsernameNotFoundException("Username not found");
		}

		List<PrincipalRoleRecord> principalRoles = getPrincipalRoles(username);
		if (CollectionUtils.isEmpty(principalRoles)) {
			LOG.warn("User {} has no roles", username);
			throw new UsernameNotFoundException("Roles not found");
		}

		return new MediaUserDetails(principal, principalRoles);
	}

	private PrincipalRecord getPrincipal(final String username) {
		return database
				.select().from(Principal.PRINCIPAL)
				.where(Principal.PRINCIPAL.USERNAME.equal(username))
				.fetchOneInto(PrincipalRecord.class);
	}

	private List<PrincipalRoleRecord> getPrincipalRoles(final String username) {
		return database
				.select().from(PrincipalRole.PRINCIPAL_ROLE)
				.where(PrincipalRole.PRINCIPAL_ROLE.USERNAME.equal(username))
				.fetchInto(PrincipalRoleRecord.class);
	}

	public static final class MediaUserDetails implements UserDetails {

		private final PrincipalRecord principal;
		private final List<PrincipalRoleRecord> roles;

		public MediaUserDetails(final PrincipalRecord principal, final List<PrincipalRoleRecord> roles) {
			this.principal = principal;
			this.roles = roles;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return roles.stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole()))
					.collect(Collectors.toList());
		}

		@Override
		public String getPassword() {
			return principal.getPassword();
		}

		@Override
		public String getUsername() {
			return principal.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

	}

}

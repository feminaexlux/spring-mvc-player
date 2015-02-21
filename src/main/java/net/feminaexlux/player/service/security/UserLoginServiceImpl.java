package net.feminaexlux.player.service.security;

import net.feminaexlux.player.exception.UserHasNoRolesException;
import net.feminaexlux.player.model.table.record.UserRecord;
import net.feminaexlux.player.model.table.record.UserRoleRecord;
import net.feminaexlux.player.service.UserLoginService;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static net.feminaexlux.player.model.table.User.USER;
import static net.feminaexlux.player.model.table.UserRole.USER_ROLE;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	private static final Logger LOG = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@Autowired
	private DSLContext database;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		UserRecord user = getUser(username);
		if (user == null) {
			LOG.warn("Failed to find user by {}", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}

		List<UserRoleRecord> userRoles = getUserRoles(username);
		if (CollectionUtils.isEmpty(userRoles)) {
			LOG.warn("User {} has no roles", username);
			throw new UserHasNoRolesException("Roles not found for " + username);
		}

		return new MediaUserDetails(user, userRoles);
	}

	@Override
	public void updateLastLogin(final String username) {
		database.update(USER)
				.set(USER.LAST_LOGIN, new Timestamp(System.currentTimeMillis()))
				.where(USER.USERNAME.equal(username))
				.execute();
	}

	private UserRecord getUser(final String username) {
		return database
				.select().from(USER)
				.where(USER.USERNAME.equal(username))
				.fetchOneInto(UserRecord.class);
	}

	private List<UserRoleRecord> getUserRoles(final String username) {
		return database.selectFrom(USER_ROLE)
				.where(USER_ROLE.USERNAME.equal(username))
				.fetchInto(UserRoleRecord.class);
	}

	public static final class MediaUserDetails implements UserDetails {

		private final UserRecord user;
		private final List<UserRoleRecord> roles;

		public MediaUserDetails(final UserRecord user, final List<UserRoleRecord> roles) {
			this.user = user;
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
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getUsername();
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

package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.table.record.UserRecord;
import net.feminaexlux.player.model.table.record.UserRoleRecord;
import net.feminaexlux.player.model.type.Role;
import net.feminaexlux.player.service.UserService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static net.feminaexlux.player.model.table.User.USER;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private DSLContext database;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserRecord> users() {
		return database.fetch(USER)
				.into(UserRecord.class);
	}

	@Override
	public List<UserRecord> find(final String query) {
		return database.select()
				.from(USER)
				.where(USER.USERNAME.contains(query))
				.or(USER.NAME.contains(query))
				.fetchInto(UserRecord.class);
	}

	@Override
	public void add(final String username, final String password, final String name, final Role... roles) {
		UserRecord user = new UserRecord();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		database.executeInsert(user);

		List<UserRoleRecord> userRoles = new ArrayList<>();
		for (Role role : roles) {
			userRoles.add(new UserRoleRecord(username, role.name()));
		}
		database.batchInsert(userRoles).execute();
	}

	@Override
	public void updatePassword(final UserRecord user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		database.executeUpdate(user);
	}

	@Override
	public void updateLastLogin(final String username) {
		database.update(USER)
				.set(USER.LAST_LOGIN, new Timestamp(System.currentTimeMillis()))
				.where(USER.USERNAME.equal(username))
				.execute();
	}
}

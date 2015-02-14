package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.table.record.PrincipalRecord;
import net.feminaexlux.player.model.table.record.PrincipalRoleRecord;
import net.feminaexlux.player.model.type.Role;
import net.feminaexlux.player.service.UserService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.feminaexlux.player.model.table.Principal.PRINCIPAL;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private DSLContext database;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<PrincipalRecord> users() {
		return database.fetch(PRINCIPAL)
				.into(PrincipalRecord.class);
	}

	@Override
	public List<PrincipalRecord> find(final String query) {
		return database.select()
				.from(PRINCIPAL)
				.where(PRINCIPAL.USERNAME.contains(query))
				.or(PRINCIPAL.NAME.contains(query))
				.fetchInto(PrincipalRecord.class);
	}

	@Override
	public void add(final String username, final String password, final String name, final Role... roles) {
		PrincipalRecord user = new PrincipalRecord();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setName(name);
		database.executeInsert(user);

		List<PrincipalRoleRecord> userRoles = new ArrayList<>();
		for (Role role : roles) {
			userRoles.add(new PrincipalRoleRecord(username, role.name()));
		}
		database.batchInsert(userRoles).execute();
	}

	@Override
	public void updatePassword(final PrincipalRecord user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		database.executeUpdate(user);
	}
}

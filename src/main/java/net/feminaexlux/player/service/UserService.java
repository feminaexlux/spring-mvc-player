package net.feminaexlux.player.service;

import net.feminaexlux.player.model.table.record.UserRecord;
import net.feminaexlux.player.model.type.Role;

import java.util.List;

public interface UserService {

	List<UserRecord> users();

	List<UserRecord> find(final String query);

	void add(final String username, final String password, final String name, final Role... roles);

	void updatePassword(final UserRecord user, final String password);

	void updateLastLogin(final String username);

}

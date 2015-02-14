package net.feminaexlux.player.service;

import net.feminaexlux.player.model.table.record.PrincipalRecord;
import net.feminaexlux.player.model.type.Role;

import java.util.List;

public interface UserService {

	List<PrincipalRecord> users();

	List<PrincipalRecord> find(final String query);

	void add(final String username, final String password, final String name, final Role... roles);

	void updatePassword(final PrincipalRecord user, final String password);

}

package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.Type;
import net.feminaexlux.player.model.TypeRepository;
import net.feminaexlux.player.service.TypeService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository<Type, String> typeRepository;

	@Override
	public List<Type> getAll() {
		return IteratorUtils.toList(typeRepository.findAll().iterator());
	}
}

package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.type.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

	@Override
	public List<Media> getAll(MediaType mediaType) {
		return Collections.emptyList();
	}
}

package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.model.MediaRepository;
import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.type.MediaType;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImpl implements MediaService {

	@Autowired
	private MediaRepository<Media, String> mediaRepository;

	@Override
	public List<Media> getAll(MediaType mediaType) {
		return IteratorUtils.toList(mediaRepository.findAll().iterator());
	}
}

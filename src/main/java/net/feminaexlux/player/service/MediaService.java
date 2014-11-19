package net.feminaexlux.player.service;

import net.feminaexlux.player.model.Media;
import net.feminaexlux.player.type.MediaType;

import java.util.List;

public interface MediaService {

    List<Media> getAll(MediaType mediaType);

}

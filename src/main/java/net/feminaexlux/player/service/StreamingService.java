package net.feminaexlux.player.service;

import net.feminaexlux.player.model.container.MusicResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface StreamingService {

	void setMusicHeadersOnResponse(final MusicResource musicResource,
	                               final HttpServletRequest httpServletRequest,
	                               final HttpServletResponse httpServletResponse) throws IOException;

	void setImageHeadersOnResponse(final MusicResource musicResource,
	                               final HttpServletRequest httpServletRequest,
	                               final HttpServletResponse httpServletResponse) throws Exception;

}

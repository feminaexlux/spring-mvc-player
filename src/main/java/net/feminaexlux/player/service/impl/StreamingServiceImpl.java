package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.service.StreamingService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class StreamingServiceImpl implements StreamingService {

	private static final long ONE_MONTH = 2678400000l;
	private static final String E_TAG_FORMAT = "\"%s-%d\"";

	@Override
	public void setHeadersOnResponse(final MusicResource musicResource,
	                                 final HttpServletRequest httpServletRequest,
	                                 final HttpServletResponse httpServletResponse) throws IOException {
		File song = Paths.get(musicResource.getFullFilePath()).toFile();

		long expires = System.currentTimeMillis() + ONE_MONTH;
		String eTag = String.format(E_TAG_FORMAT, musicResource.getResourceRecord().getChecksum(), song.lastModified());
		httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);
		httpServletResponse.setDateHeader(HttpHeaders.EXPIRES, expires);

		if (notModified(httpServletRequest, eTag, song.lastModified())) {
			httpServletResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			return;
		}

		httpServletResponse.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=" + ONE_MONTH + ", no-cache");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + musicResource.getFullFilePath() + "\"");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(song.length()));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
		httpServletResponse.setHeader(HttpHeaders.PRAGMA, "");
		streamIntoResponse(httpServletRequest, httpServletResponse, song);
	}

	private boolean notModified(final HttpServletRequest httpServletRequest, final String eTag, final long lastModified) {
		String ifNoneMatch = httpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH);
		long dateHeader = httpServletRequest.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		return eTag.equals(ifNoneMatch) || (dateHeader > 0 && (dateHeader + 1000) > lastModified);
	}

	private void streamIntoResponse(final HttpServletRequest httpServletRequest,
	                                final HttpServletResponse httpServletResponse,
	                                final File file) throws IOException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			String range = httpServletRequest.getHeader(HttpHeaders.RANGE);
			byte[] stream = buildStream(range, fileInputStream, file.length());
			IOUtils.write(stream, httpServletResponse.getOutputStream());
		} finally {
			IOUtils.closeQuietly(fileInputStream);
			IOUtils.closeQuietly(httpServletResponse.getOutputStream());
		}
	}

	private byte[] buildStream(final String range, final FileInputStream fileInputStream, final long length) throws IOException {
		byte[] stream = IOUtils.toByteArray(fileInputStream);
		if (StringUtils.isNotEmpty(range) && range.startsWith("bytes=")) {
			String[] rangeParts = range.substring(6).split("-");
			int start = Integer.parseInt(rangeParts[0]);
			int end = (int) length;

			if (rangeParts.length > 1) {
				end = Integer.parseInt(rangeParts[1]);
			}

			// TODO we have issues if any media file (think video) is over 2GB...
			stream = ArrayUtils.subarray(stream, start, end);
		}

		return stream;
	}
}

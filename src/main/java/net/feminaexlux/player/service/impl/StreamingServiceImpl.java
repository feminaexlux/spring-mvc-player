package net.feminaexlux.player.service.impl;

import net.feminaexlux.player.model.container.MusicResource;
import net.feminaexlux.player.service.StreamingService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class StreamingServiceImpl implements StreamingService {

	private static final long ONE_MONTH = 2678400000l;
	private static final String E_TAG_FORMAT = "\"%s-%d\"";

	@Override
	public void setMusicHeadersOnResponse(final MusicResource musicResource,
	                                      final HttpServletRequest httpServletRequest,
	                                      final HttpServletResponse httpServletResponse) throws IOException {
		File song = Paths.get(musicResource.getFullFilePath()).toFile();
		String eTag = String.format(E_TAG_FORMAT, musicResource.getResourceRecord().getChecksum(), song.lastModified());
		defaultResponseProperties(httpServletResponse, eTag);

		if (notModified(httpServletRequest, eTag, song.lastModified())) {
			httpServletResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			return;
		}

		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + musicResource.getFullFilePath() + "\"");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(song.length()));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
		streamIntoResponse(httpServletRequest, httpServletResponse, song);
	}

	@Override
	public void setImageHeadersOnResponse(final MusicResource musicResource,
	                                      final HttpServletRequest httpServletRequest,
	                                      final HttpServletResponse httpServletResponse) throws Exception {
		File song = Paths.get(musicResource.getFullFilePath()).toFile();
		AudioFile audioFile = AudioFileIO.read(song);
		Tag tag = audioFile.getTag();
		if (tag == null || tag.getFirstArtwork() == null) {
			httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}

		String eTag = String.format(E_TAG_FORMAT, musicResource.getResourceRecord().getChecksum() + "-img", song.lastModified());
		defaultResponseProperties(httpServletResponse, eTag);

		if (notModified(httpServletRequest, eTag, song.lastModified())) {
			httpServletResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			return;
		}

		Artwork artwork = tag.getFirstArtwork();
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + artwork.getImageUrl() + "\"");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(artwork.getBinaryData().length));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, artwork.getMimeType());
		streamIntoResponse(httpServletResponse, artwork.getBinaryData());
	}

	private void defaultResponseProperties(HttpServletResponse httpServletResponse, String eTag) {
		long expires = System.currentTimeMillis() + ONE_MONTH;
		httpServletResponse.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=" + ONE_MONTH + ", no-cache");
		httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);
		httpServletResponse.setDateHeader(HttpHeaders.EXPIRES, expires);
		httpServletResponse.setHeader(HttpHeaders.PRAGMA, "no-cache");
	}

	private boolean notModified(final HttpServletRequest httpServletRequest, final String eTag, final long lastModified) {
		String ifNoneMatch = httpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH);
		long dateHeader = httpServletRequest.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		return eTag.equals(ifNoneMatch) || (dateHeader > 0 && (dateHeader + 1000) > lastModified);
	}

	private void streamIntoResponse(final HttpServletRequest httpServletRequest,
	                                final HttpServletResponse httpServletResponse,
	                                final File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		String range = httpServletRequest.getHeader(HttpHeaders.RANGE);
		byte[] stream = buildStream(range, fileInputStream, file.length());
		streamIntoResponse(httpServletResponse, stream);
	}

	private void streamIntoResponse(final HttpServletResponse httpServletResponse,
	                                final byte[] stream) throws IOException {
		try {
			IOUtils.write(stream, httpServletResponse.getOutputStream());
		} finally {
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

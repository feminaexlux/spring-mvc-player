package net.feminaexlux.player.controller;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.impl.MusicServiceImpl.MusicResource;
import net.feminaexlux.player.type.MediaType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PlayerController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);
	private static final long ONE_MONTH = 2678400000l;

	@Autowired
	private DSLContext database;

	@Autowired
	private DirectoryScannerService directoryScannerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Model model) {
		List<MusicRecord> recentlyPlayedResult = musicService.recentlyPlayed(10);
		model.addAttribute("recentlyPlayed", viewService.toMusicItems(recentlyPlayedResult));
		return "player";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam final String query,
	                     final Model model,
	                     final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(query)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a search term");
			return "redirect:/";
		}

		List<MusicRecord> searchResults = musicService.search(query);
		model.addAttribute("searchResults", viewService.toMusicItems(searchResults));
		return "player";
	}

	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public String scan(@RequestParam final String directory, final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(directory)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a directory");
			return "redirect:/";
		}

		try {
			directoryScannerService.buildLibrary(directory, MediaType.MUSIC);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Scan of " + directory + " failed");
			LOG.error("Scanning library {}\n{}", directory, e);
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/play/{checksum}.mp3", method = RequestMethod.GET)
	public void play(@PathVariable final String checksum, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws IOException {
		MusicResource musicResource = musicService.find(checksum);
		File song = Paths.get(musicResource.getFullFilePath()).toFile();

		long expires = System.currentTimeMillis() + ONE_MONTH;
		String eTag = String.format("\"%s-%d\"", checksum, song.lastModified());
		httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);
		httpServletResponse.setDateHeader(HttpHeaders.EXPIRES, expires);

		String ifNoneMatch = httpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH);
		long dateHeader = httpServletRequest.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		if (eTag.equals(ifNoneMatch) || (dateHeader > 0 && (dateHeader + 1000) > song.lastModified())) {
			httpServletResponse.setStatus(HttpStatus.NOT_MODIFIED.value());
			return;
		}

		httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
		httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(song.length()));
		httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + musicResource.getFullFilePath() + "\"");

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(song);
			byte[] stream = IOUtils.toByteArray(fileInputStream);
			String range = httpServletRequest.getHeader(HttpHeaders.RANGE);
			if (StringUtils.isNotEmpty(range) && range.startsWith("bytes=")) {
				String[] rangeParts = range.substring(6).split("-");
				int start = Integer.parseInt(rangeParts[0]);
				int end = (int) song.length();

				if (rangeParts.length > 1) {
					end = Integer.parseInt(rangeParts[1]);
				}

				// TODO we have issues if any media file (think video) is over 2GB...
				stream = ArrayUtils.subarray(stream, start, end);
			}

			IOUtils.write(stream, httpServletResponse.getOutputStream());
		} finally {
			IOUtils.closeQuietly(fileInputStream);
			IOUtils.closeQuietly(httpServletResponse.getOutputStream());
		}
	}

	@RequestMapping(value = "/{checksum}", method = RequestMethod.GET)
	public String song(@PathVariable final String checksum, final Model model) {
		MusicResource musicResource = musicService.find(checksum);
		model.addAttribute("title", musicResource.getMusicRecord().getTitle());
		model.addAttribute("artist", musicResource.getMusicRecord().getArtist());
		model.addAttribute("album", musicResource.getMusicRecord().getAlbum());
		model.addAttribute("checksum", checksum);
		return "song";
	}

}

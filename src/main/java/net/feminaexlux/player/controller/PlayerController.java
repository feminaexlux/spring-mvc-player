package net.feminaexlux.player.controller;

import net.feminaexlux.player.model.tables.records.MusicRecord;
import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.impl.MusicServiceImpl.MusicResource;
import net.feminaexlux.player.type.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PlayerController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

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

	@RequestMapping(value = "/load/{checksum}.mp3", method = RequestMethod.GET)
	public void load(@PathVariable final String checksum, final HttpServletResponse httpServletResponse) throws IOException {
		MusicResource musicResource = musicService.find(checksum);

		ServletOutputStream servletOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			servletOutputStream = httpServletResponse.getOutputStream();
			File song = Paths.get(musicResource.getFullFilePath()).toFile();

			httpServletResponse.setContentType("audio/mpeg");
			httpServletResponse.setContentLength((int) song.length());

			FileInputStream fileInputStream = new FileInputStream(song);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			int readBytes = 0;
			while ((readBytes = bufferedInputStream.read()) != -1) {
				servletOutputStream.write(readBytes);
			}
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}

			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}
	}

	@RequestMapping(value = "/{checksum}", method = RequestMethod.GET)
	public String play(@PathVariable final String checksum, final Model model) {
		MusicResource musicResource = musicService.find(checksum);
		model.addAttribute("title", musicResource.getMusicRecord().getTitle());
		model.addAttribute("artist", musicResource.getMusicRecord().getArtist());
		model.addAttribute("album", musicResource.getMusicRecord().getAlbum());
		model.addAttribute("checksum", checksum);
		return "song";
	}

}

package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.DirectoryScannerService;
import net.feminaexlux.player.service.MusicService;
import net.feminaexlux.player.service.ViewService;
import net.feminaexlux.player.type.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class PlayerController {

	private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private DirectoryScannerService directoryScannerService;
	@Autowired
	private MusicService            musicService;
	@Autowired
	private ViewService             viewService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Model model) {
		Result<Record> recentlyPlayedResult = musicService.recentlyPlayed(10);
		model.addAttribute("recentlyPlayed", viewService.toMusicItems(recentlyPlayedResult));
		return "player";
	}

	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public String scan(@RequestParam final String directory, final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(directory)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a directory");
			return "redirect:/";
		}

		new Thread(() -> {
			try {
				long start = System.currentTimeMillis();
				directoryScannerService.buildLibrary(directory, MediaType.MUSIC);
				LOG.info("Scan took {} ms on directory {}", System.currentTimeMillis() - start, directory);
			} catch (IOException e) {
				LOG.error("Scanning library {}\n{}", directory, e);
			}
		});

		return "redirect:/";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestParam final String query,
			final Model model,
			final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(query)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a search term");
			return "redirect:/";
		}

		Result<Record> searchResults = musicService.search(query);
		model.addAttribute("searchResults", viewService.toMusicItems(searchResults));
		return "player";
	}

}

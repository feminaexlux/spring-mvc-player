package net.feminaexlux.player.controller.setting;

import net.feminaexlux.player.controller.AbstractController;
import net.feminaexlux.player.model.type.MediaType;
import net.feminaexlux.player.model.type.Role;
import net.feminaexlux.player.service.DirectoryService;
import net.feminaexlux.player.service.LibraryService;
import net.feminaexlux.player.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/settings")
public class SettingController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(SettingController.class);
	private static final String REDIRECT_SETTINGS = "redirect:/settings";
	private static final String SETTINGS_VIEW = "/settings/settings";

	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private LibraryService libraryService;
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(final Model model) {
		model.addAttribute("directories", viewService.toDirectoryItems(directoryService.allDirectories()));
		return SETTINGS_VIEW;
	}

	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public String scan(@RequestParam final String directory, final RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(directory)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please enter a directory");
			return REDIRECT_SETTINGS;
		}

		try {
			libraryService.buildLibrary(directory, MediaType.MUSIC);
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Scan of " + directory + " failed");
			LOG.error("Scanning library {}\n{}", directory, e);
		}

		return REDIRECT_SETTINGS;
	}

	@RequestMapping(value = "/addUser", method = {RequestMethod.POST, RequestMethod.PUT})
	public String addUser(@RequestParam final String username,
	                      @RequestParam final String password,
	                      @RequestParam final String name,
	                      final RedirectAttributes redirectAttributes) {
		userService.add(username, password, name, Role.ROLE_USER);
		redirectAttributes.addFlashAttribute("successMessage", "Successfully added user " + username + " for " + name);

		return REDIRECT_SETTINGS;
	}
}

package net.feminaexlux.player.controller;

import net.feminaexlux.player.service.MediaService;
import net.feminaexlux.player.type.MediaType;
import net.feminaexlux.player.view.MusicView;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static net.feminaexlux.player.service.impl.MediaServiceImpl.MUSIC;

@Controller
public class PlayerController {

	@Autowired
	private MediaService mediaService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(final Model model) {
		Result<Record> recentlyPlayedResult = mediaService.recentlyPlayed(MediaType.MUSIC, 10);

		List<MusicView> recentlyPlayed = new ArrayList<>();
		for (Record record : recentlyPlayedResult) {
			recentlyPlayed.add(new MusicView.Builder()
					.resource(record.getValue(MUSIC.RESOURCE))
					.artist(record.getValue(MUSIC.ARTIST))
					.album(record.getValue(MUSIC.ALBUM))
					.title(record.getValue(MUSIC.TITLE))
					.genre(record.getValue(MUSIC.GENRE))
					.trackNumber(record.getValue(MUSIC.TRACK))
					.rating(record.getValue(MUSIC.RATING))
					.build());
		}

		model.addAttribute("recentlyPlayed", recentlyPlayed);
		return "player";
	}

	@RequestMapping(value = "/scan", method = RequestMethod.GET)
	public String scan() {
		return "player";
	}

}

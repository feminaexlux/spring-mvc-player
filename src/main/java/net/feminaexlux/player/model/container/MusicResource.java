package net.feminaexlux.player.model.container;

import net.feminaexlux.player.model.table.record.MusicRecord;
import net.feminaexlux.player.model.table.record.ResourceRecord;

public class MusicResource {

	private final MusicRecord musicRecord;
	private final ResourceRecord resourceRecord;

	public MusicResource(final MusicRecord musicRecord, final ResourceRecord resourceRecord) {
		this.musicRecord = musicRecord;
		this.resourceRecord = resourceRecord;
	}

	public MusicRecord getMusicRecord() {
		return musicRecord;
	}

	public ResourceRecord getResourceRecord() {
		return resourceRecord;
	}

	public String getFullFilePath() {
		return resourceRecord.getDirectory() + resourceRecord.getPath();
	}
}

package net.feminaexlux.player.model.type;

public enum MediaType {
	MUSIC;

	public static MediaType find(final String type) {
		for (MediaType mediaType : MediaType.values()) {
			if (type.equalsIgnoreCase(mediaType.name())) {
				return mediaType;
			}
		}

		throw new IllegalArgumentException("Type " + type + " not supported");
	}
}

package net.feminaexlux.player.util;

import org.apache.commons.lang3.StringUtils;

public final class Normalizer {

	private Normalizer() {
	}

	public static String normalizeForUrl(final String string) {
		String normalized = StringUtils.stripAccents(string);
		normalized = normalized.toLowerCase();
		normalized = normalized.replaceAll("[^a-z0-9\\s]", "");
		normalized = normalized.replace(" ", "-");
		normalized = normalized.trim();

		return normalized;
	}

}

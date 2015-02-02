package net.feminaexlux.player.model.element;

enum HashCodePrimes {

	MUSIC(127, 131);

	private final int prime;
	private final int multiplier;

	private HashCodePrimes(final int prime, final int multiplier) {
		this.prime = prime;
		this.multiplier = multiplier;
	}

	public int getPrime() {
		return prime;
	}

	public int getMultiplier() {
		return multiplier;
	}
}

package net.feminaexlux.player.model.element;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class KeyValue<K extends Comparable & Serializable, V extends Serializable> implements Serializable, Comparable<KeyValue<K, V>> {

	private final K key;
	private final V value;

	private KeyValue(final Builder<K, V> builder) {
		this.key = builder.key;
		this.value = builder.value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}

		if (object == this) {
			return true;
		}

		if (object.getClass() != getClass()) {
			return false;
		}

		KeyValue that = (KeyValue) object;

		return new EqualsBuilder()
				.append(this.key, that.key)
				.append(this.value, that.value)
				.isEquals();
	}

	@Override
	public int compareTo(final KeyValue<K, V> that) {
		return new CompareToBuilder()
				.append(this.key, that.key)
				.toComparison();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodePrimes.KEY_VALUE.getPrime(), HashCodePrimes.KEY_VALUE.getMultiplier())
				.append(this.key)
				.append(this.value)
				.toHashCode();
	}

	public static final class Builder<K extends Comparable & Serializable, V extends Serializable> {

		private K key;
		private V value;

		public Builder<K, V> key(final K key) {
			this.key = key;
			return this;
		}

		public Builder<K, V> value(final V value) {
			this.value = value;
			return this;
		}

		public KeyValue<K, V> build() {
			return new KeyValue(this);
		}
	}

}

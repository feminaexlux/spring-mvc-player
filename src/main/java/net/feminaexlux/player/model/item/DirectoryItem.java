package net.feminaexlux.player.model.item;

import net.feminaexlux.player.model.type.MediaType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class DirectoryItem implements Serializable {

	private static final long serialVersionUID = 5814831040242290603L;
	private final String location;
	private final MediaType type;

	public DirectoryItem(final Builder builder) {
		this.location = builder.location;
		this.type = builder.type;
	}

	public String getLocation() {
		return location;
	}

	public MediaType getType() {
		return type;
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

		DirectoryItem that = (DirectoryItem) object;
		return new EqualsBuilder()
				.append(this.location, that.location)
				.append(this.type, that.type)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(HashCodePrimes.DIRECTORY.getPrime(), HashCodePrimes.DIRECTORY.getMultiplier())
				.append(this.location)
				.append(this.type)
				.toHashCode();
	}

	public static final class Builder {

		private String location;
		private MediaType type;

		public Builder location(final String location) {
			this.location = location;
			return this;
		}

		public Builder type(final String type) {
			this.type = MediaType.valueOf(type);
			return this;
		}

		public DirectoryItem build() {
			return new DirectoryItem(this);
		}
	}
}

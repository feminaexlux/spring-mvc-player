package net.feminaexlux.player.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "directory")
public class Directory {

	@Id
	private String location;

	@JoinColumn(nullable = false)
	@ManyToOne
	private Type type;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastScanned;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getLastScanned() {
		return lastScanned;
	}

	public void setLastScanned(Date lastScanned) {
		this.lastScanned = lastScanned;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		Directory that = (Directory) object;
		return new EqualsBuilder()
				.append(location, that.location)
				.append(type, that.type)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(location)
				.append(type)
				.hashCode();
	}
}

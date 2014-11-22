package net.feminaexlux.player.model;

import net.feminaexlux.player.type.MediaType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "type")
public class Type {

	@Id
	@Column(length = 50)
	private String type;

	private String extensions;

	@OneToMany(mappedBy = "type")
	private List<Directory> directories;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public List<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<Directory> directories) {
		this.directories = directories;
	}

	@Transient
	public MediaType getMediaType() {
		return MediaType.find(this.type);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		Type that = (Type) object;
		return new EqualsBuilder()
				.append(type, that.type)
				.append(extensions, that.extensions)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 31)
				.append(type)
				.append(extensions)
				.hashCode();
	}
}

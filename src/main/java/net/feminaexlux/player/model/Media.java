package net.feminaexlux.player.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
@Inheritance(strategy = InheritanceType.JOINED)
public class Media {

	@Id
	@Column(length = 40)
	protected String checkSum;

	@JoinColumn(name = "location", nullable = false)
	@ManyToOne(optional = false)
	protected Directory directory;

	@Column(nullable = false)
	protected String name;

	@Column(nullable = false)
	protected String localPath;

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
}

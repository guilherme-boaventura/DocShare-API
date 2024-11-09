package br.ucsal.docshare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="fileContent")
public class FileContent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="content")
	private byte[] content;
	
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;
	
	public FileContent() {
		
	}

	public FileContent(byte[] content, File file) {
		super();
		this.content = content;
		this.file = file;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}
}

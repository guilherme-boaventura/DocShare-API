package br.ucsal.docshare.entity;

import br.ucsal.docshare.util.data.FolderVisibility;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="folder")
public class Folder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Embedded
	@AttributeOverride(name="acronym", column=@Column(name="visibility"))
	private FolderVisibility visibility;
	
	@ManyToOne
	@JoinColumn(name="tag_id")
	private Tag tag;
	
	public Folder() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FolderVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(FolderVisibility visibility) {
		this.visibility = visibility;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}
}

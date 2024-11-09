package br.ucsal.docshare.util.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

@Embeddable
public class FolderVisibility {
	
	public static final FolderVisibility PRIVATE = new FolderVisibility("Private", "PRIVT");
	public static final FolderVisibility DEPARTMENTAL = new FolderVisibility("Departmental", "DPMET");
	public static final FolderVisibility CORPORATIVE = new FolderVisibility("Corporative", "CPRTV");
	
	
	@Transient
	private String name;
	
	private String acronym;
	
	public FolderVisibility() {
		
	}
	
	public FolderVisibility(String name, String acronym) {
		super();
		this.name = name;
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAcronym() {
		return acronym;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	public static FolderVisibility get(String acronym) {
		List<FolderVisibility> visibility = Arrays.asList(PRIVATE,DEPARTMENTAL,CORPORATIVE)
														.stream()
														.filter(v -> v.getAcronym().equals(acronym))
														.collect(Collectors.toList());
		if(visibility.isEmpty()) {
			return null;
		}
		return visibility.get(0);
	}
	
	public static List<FolderVisibility> findAll() {
		return Arrays.asList(PRIVATE,DEPARTMENTAL,CORPORATIVE);
	}
	
	public FolderVisibility getInstance(String acronym) {
		List<FolderVisibility> filteredVisibility = findAll().stream().filter((v)->v.getAcronym().contentEquals(acronym.toUpperCase())).collect(Collectors.toList());
		if(filteredVisibility.isEmpty()) {
			return null;
		} else {
			return filteredVisibility.get(0);
		}
	}
	
	@Override
    public boolean equals(Object obj) {

		if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        FolderVisibility visibility = (FolderVisibility) obj;

        return this.getAcronym().contentEquals(visibility.getAcronym());
    }

    @Override
    public int hashCode() {
        return 31 * this.getAcronym().hashCode();
    }
	
}
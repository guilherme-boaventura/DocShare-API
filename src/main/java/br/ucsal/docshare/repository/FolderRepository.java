package br.ucsal.docshare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ucsal.docshare.entity.Folder;
import br.ucsal.docshare.entity.User;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
	
	List<Folder> findByUser(User user);
	
	List<Folder> findByReferenceFolder(Folder referenceFolder);
	
	@Query("SELECT f FROM Folder f WHERE f.visibility.acronym != 'PRIVT'")
	List<Folder> findSharedFolders();
	
}

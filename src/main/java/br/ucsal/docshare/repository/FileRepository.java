package br.ucsal.docshare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ucsal.docshare.entity.File;
import br.ucsal.docshare.entity.Folder;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
	
	List<File> findByFolder(Folder folder);
	
}

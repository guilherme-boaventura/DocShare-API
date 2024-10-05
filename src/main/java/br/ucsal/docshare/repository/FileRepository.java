package br.ucsal.docshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ucsal.docshare.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
	
}

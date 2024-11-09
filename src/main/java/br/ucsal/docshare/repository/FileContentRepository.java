package br.ucsal.docshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ucsal.docshare.entity.FileContent;

@Repository
public interface FileContentRepository extends JpaRepository<FileContent, Long> {

}

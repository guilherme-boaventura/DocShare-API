package br.ucsal.docshare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ucsal.docshare.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	Optional<Tag> findByAcronym(String acronym);
	
}

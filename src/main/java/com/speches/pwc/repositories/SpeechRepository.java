package com.speches.pwc.repositories;

import com.speches.pwc.dtos.SpeechListDTO;
import com.speches.pwc.entities.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpeechRepository extends JpaRepository<Speech, Long> {
    List<Speech> findByGrupoId(Long grupoId);


    @Query("""
           select new com.speches.pwc.dtos.SpeechListDTO(
               s.id, s.titulo, s.contenido, g.id, g.nombre
           )
           from Speech s
           join s.grupo g
           order by s.createdAt desc
           """)
    List<SpeechListDTO> findAllWithGrupo();
}

package com.speches.pwc.repositories;

import com.speches.pwc.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByAreaId(Long areaId);

}

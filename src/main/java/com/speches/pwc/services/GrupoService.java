package com.speches.pwc.services;

import com.speches.pwc.entities.Area;
import com.speches.pwc.entities.Grupo;
import com.speches.pwc.repositories.AreaRepository;
import com.speches.pwc.repositories.GrupoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepo;
    private final AreaRepository areaRepo;

    public GrupoService(GrupoRepository grupoRepo, AreaRepository areaRepo) {
        this.grupoRepo = grupoRepo;
        this.areaRepo = areaRepo;
    }

    public List<Grupo> listarPorArea(Long areaId) {
        return grupoRepo.findByAreaId(areaId);
    }

    public Grupo crear(Long areaId, Grupo grupo) {
        Area area = areaRepo.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        grupo.setArea(area);
        return grupoRepo.save(grupo);
    }


}

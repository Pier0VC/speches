package com.speches.pwc.services;

import com.speches.pwc.entities.Area;
import com.speches.pwc.repositories.AreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AreaService {
    private final AreaRepository repo;

    public AreaService(AreaRepository repo) {
        this.repo = repo;
    }

    public List<Area> listar() {
        return repo.findAll();
    }

    public Area crear(Area area) {
        return repo.save(area);
    }

    @Transactional(readOnly = true)
    public Area obtener(Long id){ return repo.findById(id).orElseThrow(); }

    @Transactional
    public Area actualizar(Long id, String nombre, String descripcion){
        Area a = obtener(id);
        a.setNombre(nombre);
        a.setDescripcion(descripcion);
        return repo.save(a);
    }

    @Transactional
    public void eliminar(Long id){
        repo.deleteById(id);
    }

}

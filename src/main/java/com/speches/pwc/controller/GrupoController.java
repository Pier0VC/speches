package com.speches.pwc.controller;

import com.speches.pwc.dtos.GrupoCreateDTO;
import com.speches.pwc.dtos.GrupoDTO;
import com.speches.pwc.dtos.Mappers;
import com.speches.pwc.entities.Area;
import com.speches.pwc.entities.Grupo;
import com.speches.pwc.repositories.AreaRepository;
import com.speches.pwc.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin
public class GrupoController {
    private final GrupoService service;
    private final AreaRepository areaRepo;

    public GrupoController(GrupoService s, AreaRepository a){ this.service = s; this.areaRepo = a; }

    @GetMapping
    public List<GrupoDTO> listarPorArea(@RequestParam Long areaId) {
        return service.listarPorArea(areaId).stream().map(Mappers::toDTO).toList();
    }

    @PostMapping
    public GrupoDTO crear(@RequestBody @Valid GrupoCreateDTO body) {
        Grupo g = new Grupo();
        g.setId(null);
        g.setNombre(body.nombre);
        g.setDescripcion(body.descripcion);
        Area area = areaRepo.findById(body.areaId).orElseThrow();
        g.setArea(area);
        return Mappers.toDTO(service.crear(area.getId(), g));
    }

}

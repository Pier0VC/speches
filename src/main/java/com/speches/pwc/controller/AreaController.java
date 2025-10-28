package com.speches.pwc.controller;

import com.speches.pwc.dtos.AreaCreateDTO;
import com.speches.pwc.dtos.AreaDTO;
import com.speches.pwc.dtos.Mappers;
import com.speches.pwc.entities.Area;
import com.speches.pwc.services.AreaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/areas")
@CrossOrigin
public class AreaController {

    private final AreaService service;

    public AreaController(AreaService service) {
        this.service = service;
    }

    @PostMapping
    public AreaDTO crear(@RequestBody @Valid AreaCreateDTO body) {
        Area a = new Area();
        a.setId(null);
        a.setNombre(body.nombre);
        a.setDescripcion(body.descripcion);
        return Mappers.toDTO(service.crear(a));
    }

    @GetMapping
    public List<AreaDTO> listar() {
        return service.listar().stream().map(Mappers::toDTO).toList();
    }

    @GetMapping("/{id}")
    public AreaDTO obtener(@PathVariable Long id) {
        return Mappers.toDTO(service.obtener(id));
    }

    @PutMapping("/{id}")
    public AreaDTO actualizar(@PathVariable Long id, @RequestBody @Valid AreaCreateDTO body) {
        var a = service.actualizar(id, body.nombre, body.descripcion);
        return Mappers.toDTO(a);
    }


}

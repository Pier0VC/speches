package com.speches.pwc.dtos;

import com.speches.pwc.entities.*;

public class Mappers {

    public static AreaDTO toDTO(Area e) {
        AreaDTO d = new AreaDTO();
        d.id = e.getId();
        d.nombre = e.getNombre();
        d.descripcion = e.getDescripcion();
        return d;
    }

    public static GrupoDTO toDTO(Grupo e) {
        GrupoDTO d = new GrupoDTO();
        d.id = e.getId();
        d.areaId = e.getArea() != null ? e.getArea().getId() : null;
        d.nombre = e.getNombre();
        d.descripcion = e.getDescripcion();
        return d;
    }

    public static SpeechDTO toDTO(Speech e) {
        SpeechDTO d = new SpeechDTO();
        d.id = e.getId();
        d.grupoId = e.getGrupo() != null ? e.getGrupo().getId() : null;
        d.titulo = e.getTitulo();
        d.contenido = e.getContenido();
        d.estado = e.getEstado();
        d.idioma = e.getIdioma();
        d.createdAt = e.getCreatedAt() != null ? e.getCreatedAt().toString() : null;
        return d;
    }
}
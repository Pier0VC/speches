package com.speches.pwc.dtos;

public record SpeechListDTO(
        Long id,
        String titulo,
        String contenido,
        Long grupoId,
        String grupoNombre
) {}

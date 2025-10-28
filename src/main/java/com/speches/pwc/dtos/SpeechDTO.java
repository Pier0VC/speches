package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SpeechDTO {
    public Long id;
    public Long grupoId;
    public String titulo;
    public String contenido;
    public String estado;
    public String idioma;
    public String createdAt;
}
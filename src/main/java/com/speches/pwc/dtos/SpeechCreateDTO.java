package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SpeechCreateDTO {
    @NotNull
    public Long grupoId;

    @NotBlank
    public String titulo;

    @NotBlank
    public String contenido;

    public String estado = "draft";
    public String idioma = "es";
}
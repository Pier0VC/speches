package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateSpeechRequest {
    @NotBlank public String titulo;
    @NotBlank public String contenido;
}

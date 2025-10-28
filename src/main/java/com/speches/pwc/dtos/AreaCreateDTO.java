package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;

public class AreaCreateDTO {
    @NotBlank
    public String nombre;
    public String descripcion;
}

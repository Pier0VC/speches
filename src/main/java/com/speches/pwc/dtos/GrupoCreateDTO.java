package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GrupoCreateDTO {
    @NotNull
    public Long areaId;

    @NotBlank
    public String nombre;

    public String descripcion;
}
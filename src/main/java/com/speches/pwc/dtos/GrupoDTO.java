package com.speches.pwc.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GrupoDTO {
    public Long id;
    public Long areaId;
    public String nombre;
    public String descripcion;
}
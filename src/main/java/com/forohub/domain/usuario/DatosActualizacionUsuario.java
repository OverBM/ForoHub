package com.forohub.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionUsuario(
        @NotNull
        Long id,
        String nombre,
        String correoElectronico
) {
}
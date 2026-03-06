package com.forohub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionRespuesta(

        @NotNull
        Long id,

        String mensaje,

        Boolean solucion
) {
}
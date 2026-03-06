package com.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(
        Long id,
        String mensaje,
        String autor,
        LocalDateTime fechaCreacion,
        Boolean solucion
) {
    public DatosListaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getAutor().getNombre(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion()
        );
    }
}
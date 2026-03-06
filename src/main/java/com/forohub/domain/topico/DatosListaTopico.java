package com.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosListaTopico(
        Long id,
        String titulo,
        String autor,
        String curso,
        LocalDateTime fechaCreacion,
        StatusTopico status
) {
    public DatosListaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre(),
                topico.getFechaCreacion(),
                topico.getStatus()
        );
    }
}
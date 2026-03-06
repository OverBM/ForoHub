package com.forohub.domain.usuario;

public record DatosListaUsuario(
        Long id,
        String nombre,
        String correoElectronico
) {
    public DatosListaUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico()
        );
    }
}

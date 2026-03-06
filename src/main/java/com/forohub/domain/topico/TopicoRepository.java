package com.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);
    @Query("""
            SELECT t FROM Topico t
            WHERE (:curso IS NULL OR t.curso.nombre = :curso)
            AND (:anio IS NULL OR YEAR(t.fechaCreacion) = :anio)
            ORDER BY t.fechaCreacion ASC
            """)
    Page<Topico> listarPorFiltros(
            @Param("curso") String curso,
            @Param("anio") Integer anio,
            Pageable paginacion
    );
}
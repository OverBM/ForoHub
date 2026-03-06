package com.forohub.controller;

import com.forohub.domain.curso.CursoRepository;
import com.forohub.domain.topico.*;
import com.forohub.domain.usuario.UsuarioRepository;
import com.forohub.domain.ValidacionException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje.");
        }

        var optionalAutor = usuarioRepository.findById(datos.autorId());
        if (!optionalAutor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var optionalCurso = cursoRepository.findById(datos.cursoId());
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var topico = new Topico(datos, optionalAutor.get(), optionalCurso.get());
        topicoRepository.save(topico);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {

        var page = topicoRepository.listarPorFiltros(curso, anio, paginacion).map(DatosListaTopico::new);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionTopico datos) {
        var optionalTopico = topicoRepository.findById(datos.id());
        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (topicoRepository.existsByTituloAndMensajeAndIdNot(datos.titulo(), datos.mensaje(), datos.id())) {
            throw new ValidacionException("Ya existe un tópico con el mismo título y mensaje.");
        }

        var topico = optionalTopico.get();
        topico.actualizar(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) {
        var optionalTopico = topicoRepository.findById(id);
        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
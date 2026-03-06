package com.forohub.controller;

import com.forohub.domain.respuesta.*;
import com.forohub.domain.topico.TopicoRepository;
import com.forohub.domain.usuario.UsuarioRepository;
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
@RequestMapping("/topicos/{topicoId}/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@PathVariable Long topicoId,
                                    @RequestBody @Valid DatosRegistroRespuesta datos,
                                    UriComponentsBuilder uriComponentsBuilder) {
        var optionalTopico = topicoRepository.findById(topicoId);
        if (!optionalTopico.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var optionalAutor = usuarioRepository.findById(datos.autorId());
        if (!optionalAutor.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var respuesta = new Respuesta(datos, optionalTopico.get(), optionalAutor.get());
        respuestaRepository.save(respuesta);
        var uri = uriComponentsBuilder.path("/topicos/{topicoId}/respuestas/{id}")
                .buildAndExpand(topicoId, respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleRespuesta(respuesta));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listar(@PathVariable Long topicoId,
                                                            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var page = respuestaRepository.findByTopicoId(topicoId, paginacion).map(DatosListaRespuesta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long topicoId, @PathVariable Long id) {
        var respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@PathVariable Long topicoId,
                                     @RequestBody @Valid DatosActualizacionRespuesta datos) {
        var optionalRespuesta = respuestaRepository.findById(datos.id());
        if (!optionalRespuesta.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var respuesta = optionalRespuesta.get();
        respuesta.actualizar(datos);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long topicoId, @PathVariable Long id) {
        var optionalRespuesta = respuestaRepository.findById(id);
        if (!optionalRespuesta.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
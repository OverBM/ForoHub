CREATE TABLE respuestas (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            mensaje TEXT NOT NULL,
                            topico_id BIGINT NOT NULL,
                            fecha_creacion DATETIME NOT NULL,
                            autor_id BIGINT NOT NULL,
                            solucion BOOLEAN DEFAULT FALSE,
                            PRIMARY KEY (id),
                            CONSTRAINT fk_respuestas_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
                            CONSTRAINT fk_respuestas_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);
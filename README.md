# ForoHub
---
## Descripción
ForoHub es una API REST desarrollada en Java con Spring Boot conectada a MySQL,  
la aplicación permite gestionar tópicos, respuestas y usuarios de un foro,  
con autenticación y autorización mediante JWT.

---
## Funcionalidades
- Registro y autenticación de usuarios con JWT.
- Crear, listar, detallar, actualizar y eliminar tópicos, usuarios y respuestas
- Validación de datos y manejo de errores.
- Documentación de la API con Swagger.

---
## Estructura del Proyecto
```
orohub/
├── src/main/java/com/forohub/
│   ├── controller/
│   │   ├── AutenticacionController   # Endpoint de login y generación de token JWT
│   │   ├── TopicoController          # CRUD de tópicos
│   │   ├── RespuestaController       # CRUD de respuestas anidadas a tópicos
│   │   └── UsuarioController         # CRUD de usuarios
│   ├── domain/
│   │   ├── curso/
│   │   │   ├── Curso                        # Entidad JPA que representa un curso
│   │   │   └── CursoRepository              # Interfaz JPA para consultas de cursos
│   │   ├── respuesta/
│   │   │   │                                # DTOs de respuesta
│   │   │   ├── DatosActualizacionRespuesta
│   │   │   ├── DatosDetalleRespuesta
│   │   │   ├── DatosListaRespuesta
│   │   │   ├── DatosRegistroRespuesta
│   │   │   │
│   │   │   ├── Respuesta                    # Entidad JPA que representa una respuesta
│   │   │   └── RespuestaRepository          # Interfaz JPA para consultas de respuestas
│   │   ├── topico/
│   │   │   │                                # DTOs de tópico
│   │   │   ├── DatosActualizacionTopico
│   │   │   ├── DatosDetalleTopico
│   │   │   ├── DatosListaTopico
│   │   │   ├── DatosRegistroTopico
│   │   │   │
│   │   │   ├── StatusTopico                 # Enum de estados del tópico (ABIERTO, CERRADO, SOLUCIONADO)
│   │   │   ├── Topico                       # Entidad JPA que representa un tópico
│   │   │   └── TopicoRepository             # Interfaz JPA para consultas de tópicos
│   │   ├── usuario/
│   │   │   ├── AutenticacionService         # Servicio de autenticación con Spring Security
│   │   │   │                                # DTOs de usuario
│   │   │   ├── DatosActualizacionUsuario
│   │   │   ├── DatosAutenticacion
│   │   │   ├── DatosDetalleUsuario
│   │   │   ├── DatosListaUsuario
│   │   │   ├── DatosRegistroUsuario
│   │   │   │
│   │   │   ├── Usuario                      # Entidad JPA que representa un usuario
│   │   │   └── UsuarioRepository            # Interfaz JPA para consultas de usuarios
│   │   └── ValidacionException              # Excepción personalizada para validaciones de negocio
│   ├── infra/
│   │   ├── exceptions/
│   │   │   └── GestorDeErrores       # Manejo global de errores con @RestControllerAdvice
│   │   ├── security/
│   │   │   ├── SecurityConfigurations # Configuración de Spring Security
│   │   │   ├── SecurityFilter         # Filtro JWT para validar tokens
│   │   │   ├── TokenService           # Generación y validación de tokens JWT
│   │   │   └── DatosTokenJWT          # Record para la respuesta del token
│   │   └── springdoc/
│   │       └── SpringDocConfiguration # Configuración de Swagger
│   └── ForohubApplication             # Clase principal de Spring Boot
├── src/main/resources/
│   ├── db/migration/                  # Migraciones Flyway
│   │   ├── V1__create-table-usuarios.sql
│   │   ├── V2__create-table-cursos.sql
│   │   ├── V3__create-table-topicos.sql
│   │   └── V4__create-table-respuestas.sql
│   └── application.properties         # Configuración de la base de datos
└── pom.xml                            # Dependencias del proyecto
```

---
## Requisitos
- Java 17
- Maven (Version 4)
- Spring Boot 3
- MySQL

## Dependencias
- Spring Data JPA
- Spring Security
- Spring Web
- Spring Boot DevTools
- Flyway Migration
- Validation
- MySQL Driver
- Lombok
- Java JWT (Auth0)
- SpringDoc OpenAPI (Swagger)


---
## Endpoints
| Método | URI | Descripción |
|--------|-----|-------------|
| POST | /auth | Login y obtención de token JWT |
| POST | /usuarios | Registro de usuario |
| GET | /usuarios | Listar usuarios |
| GET | /usuarios/{id} | Detalle de usuario |
| PUT | /usuarios | Actualizar usuario |
| DELETE | /usuarios/{id} | Eliminar usuario |
| POST | /topicos | Registrar tópico |
| GET | /topicos | Listar tópicos |
| GET | /topicos/{id} | Detalle de tópico |
| PUT | /topicos | Actualizar tópico |
| DELETE | /topicos/{id} | Eliminar tópico |
| POST | /topicos/{id}/respuestas | Registrar respuesta |
| GET | /topicos/{id}/respuestas | Listar respuestas |
| GET | /topicos/{id}/respuestas/{id} | Detalle de respuesta |
| PUT | /topicos/{id}/respuestas | Actualizar respuesta |
| DELETE | /topicos/{id}/respuestas/{id} | Eliminar respuesta |

---
## Instalación
1. Clonar el repositorio:
```bash
git clone https://github.com/OverBM/ForoHub.git
```
2. Crear la base de datos en MySQL (O descargar el backup y Requests(insomnia) en el branch `resources`):
```sql
CREATE DATABASE forohub;
```
3. Configurar `src/main/resources/application.properties` para las variables de entorno O Eliminar la parte `${}` y poner directamente tus datos:
```properties
spring.datasource.url=jdbc:mysql://localhost/forohub
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${DBUSERNAME}
spring.datasource.password=${DBPASSWORD}
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
api.security.token.secret=${JWT_SECRET}
```
```
${DBUSERNAME}=tu_usuario
${DBPASSWORD}=tu_contraseña
${JWT_SECRET}=tu_secret_jwt
```
4. Recargar dependencias Maven
5. Ejecutar el proyecto
6. Acceder a la documentación Swagger en:
```
http://localhost:8081/swagger-ui.html
```

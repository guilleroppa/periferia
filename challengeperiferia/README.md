# Reto técnico de Periferia

## Requisitos
- Java 17
- Spring Boot 3.5.5
- Maven 3.9.8
- H2

## Configuración
- Clona el repositorio
- Configura la base de datos en `application.properties`
- Limpia y contruye el proyecto `mvn clean install`
- Ejecuta el proyecto con `mvn spring-boot:run`

## Endpoints
- `POST /grabarAlumnos`: Grabar alumno
    - Request Body:
      ```json
      {
          "nombre": "Pedro",
          "apellido": "Garay",
          "estado": true,
          "edad": 19
      }
- `GET /listarAlumnosActivos?estado=true`
- `GET /buscarAlumno?id=1`

## Pruebas
- Ejecuta las pruebas con `mvn test`

## Enlaces
- Swagger: http://localhost:8080/swagger-ui/index.html
- H2 DB: http://localhost:8080/h2-ui/

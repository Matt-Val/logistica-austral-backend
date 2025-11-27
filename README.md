# Logística Austral - Backend API

API REST desarrollada con **Java 17** y **Spring Boot** para la gestión logística de fltoas de camiones. Este backend maneja la lógica de negocio, autenticación de usuarios, gestión del catálogo de camiones y el procesamiento de cotizaciones, sirviendo datos a la aplicación frontend.

## Tecnologías
* **Lenguaje:** [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot) (Web, Data JPA, Test)
* **Base de Datos:** MySQL 8
* **Gestor de Dependencias:** Maven
* **Herramientas:** Lombok (Para reducción de código).

## Requisitos Previos
1. Tener instalado el **JDK 17** o superior.
2. Tener instalado **MySQL Server** (se recomienda la versión 6).

## Documentación de la API

A continuación se detallan los endpoints principales expuestos por los controladores.

### Autenticación (`/api/auth`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/registro` | Registra un nuevo usuario (Cliente). |
| `POST` | `/login` | Inicia sesión y devuelve los datos del usuario. |
| `POST` | `/registro-admin` | Crea un usuario con rol de Administrador. |

### Camiones (`/api/camiones`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/` | Obtiene camiones disponibles (filtro opcional `?tipo=`). |
| `GET` | `/{id}` | Obtiene el detalle de un camión específico. |
| `GET` | `/admin/todos` | (Admin) Lista la flota completa, incluidos no disponibles. |
| `PUT` | `/admin/{id}` | (Admin) Actualiza datos o disponibilidad de un camión. |
| `DELETE`| `/admin/{id}` | (Admin) Elimina un camión del sistema. |

### Cotizaciones (`/api/cotizaciones`)
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/` | Crea una nueva solicitud de cotización (invitado o usuario). |
| `GET` | `/admin` | (Admin) Lista todas las cotizaciones registradas. |
| `PUT` | `/admin/{id}/estado` | (Admin) Cambia el estado (ej. "Aprobada", "Pendiente"). |
| `DELETE`| `/{id}` | Elimina una cotización por ID. |

## Estructura del Proyecto

```text
com.logistica_austral.la
├── controller       # Controladores REST (Endpoints)
├── dto              # Objetos de Transferencia de Datos (Request/Response)
├── service          # Lógica de negocio

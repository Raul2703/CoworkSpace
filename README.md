<div align="center">

<img src="https://capsule-render.vercel.app/api?type=rect&height=220&color=0:0f172a,100:2563eb&text=CoworkSpace&fontSize=55&fontColor=ffffff&animation=fadeIn"/>

<br>

### Gestión de espacios de coworking desarrollada con Spring Boot

<br>

<img src="https://img.shields.io/badge/Java-17-111827?style=flat-square&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring_Boot-3.x-111827?style=flat-square&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/Bootstrap-5-111827?style=flat-square&logo=bootstrap&logoColor=white"/>
<img src="https://img.shields.io/badge/Hibernate-JPA-111827?style=flat-square&logo=hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/Database-H2-111827?style=flat-square&logo=h2&logoColor=white"/>

</div>

---

# 📌 Descripción

CoworkSpace es una aplicación web desarrollada para la gestión de espacios de coworking.  
Permite administrar espacios, usuarios y reservas, controlando disponibilidad, estados y costes de cada operación.

El proyecto ha sido desarrollado siguiendo arquitectura MVC utilizando Spring Boot, Thymeleaf y Spring Data JPA. :contentReference[oaicite:0]{index=0}

---

# ⚒️ Tecnologías utilizadas

<div align="center">

| Backend | Frontend | Base de datos | Seguridad |
|---|---|---|---|
| Spring Boot | HTML5 | H2 Database | Spring Security |
| Spring MVC | CSS3 | Hibernate | Login & Roles |
| Spring Data JPA | Bootstrap 5 | JPA | Control de acceso |

</div>

<br>

<div align="center">

<img src="https://skillicons.dev/icons?i=java,html,css,js,bootstrap,git,github,vscode" />

</div>

---

# 🧠 Funcionalidades

## Gestión de entidades
- CRUD completo de usuarios
- CRUD completo de espacios
- CRUD completo de reservas

## Gestión de reservas
- Control de disponibilidad
- Prevención de solapamientos
- Cálculo automático del coste
- Gestión de estados de reserva

## Seguridad
- Login funcional
- Roles ADMIN y USUARIO
- Protección de rutas
- Menús dinámicos según permisos

## Frontend
- Formularios validados
- Alertas dinámicas
- Confirmación antes de eliminar
- Diseño responsive con Bootstrap

---

# 🗂️ Modelo de datos

## Usuario
```txt
nombre
email
telefono
```

## Espacio
```txt
nombre
capacidad
precio
```

## Reserva
```txt
codigo
fecha
duracion
precioTotal
```

## Relación Reserva - Espacio
```txt
estado
observaciones
```

---

# 🔍 Consultas implementadas

- Espacios más utilizados
- Reservas por fecha
- Usuarios frecuentes

---

# ⚠️ Validaciones y excepciones

## Validaciones
- Uso de @Valid
- Validaciones backend
- Validaciones frontend con Javascript

## Excepciones personalizadas
```txt
ReservaSolapadaException
DuracionInvalidaException
CustomException
```

---

# 📁 Estructura del proyecto

```txt
src
 ├── main
 │    ├── java
 │    │     ├── controller
 │    │     ├── service
 │    │     ├── repository
 │    │     ├── entity
 │    │     ├── dto
 │    │     └── security
 │    │
 │    └── resources
 │          ├── templates
 │          ├── static
 │          └── application.properties
 │
 └── test
```

---

# 🚀 Cómo ejecutar el proyecto

## Clonar repositorio

```bash
git clone https://[[github.com/TU_USUARIO/coworkspace.git](https://github.com/Raul2703/CoworkSpace.git)](https://github.com/Raul2703/CoworkSpace.git)](https://github.com/Raul2703/CoworkSpace.git)
```

## Entrar al proyecto

```bash
cd CoworkSpace
```

## Ejecutar aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en:

```txt
http://localhost:8080
```

---

# 📸 Capturas

```txt
Aquí puedes añadir:
- dashboard
- listado de reservas
- login
- gestión de espacios
- formularios
```

---

# 🧩 Características técnicas

- Arquitectura MVC
- Persistencia con JPA/Hibernate
- Relaciones complejas entre entidades
- Seguridad basada en roles
- Validaciones completas
- Separación por capas
- Buenas prácticas de Git

---

# 📚 Gestión del proyecto

## Git Flow
```txt
main
develop
feature/*
```

## Conventional commits
```txt
feat:
fix:
refactor:
style:
docs:
```

---

# 🎯 Objetivos del proyecto

- Aplicar Spring Boot en un proyecto real
- Trabajar arquitectura MVC
- Gestionar relaciones complejas con JPA
- Implementar seguridad básica
- Crear una aplicación web completa y funcional

---

<div align="center">

### CoworkSpace • Spring Boot Project

</div>

<img src="https://capsule-render.vercel.app/api?type=rect&height=120&section=footer&color=0:2563eb,100:0f172a"/>

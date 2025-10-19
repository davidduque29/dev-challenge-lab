# 🏥 Hospital API - Sistema de Gestión Hospitalaria

Proyecto generado automáticamente con **Project Generator Clean**

📦 **Autor:** Iván David Duque Perdomo  
🧱 **Arquitectura:** Clean Architecture (multi-módulo Maven)  
⚙️ **Java:** 22  
🧩 **Framework:** Spring Boot + GraphQL + RabbitMQ + MongoDB

---

## 🚀 Descripción General

El **Hospital API** es un sistema backend diseñado para **gestionar los procesos hospitalarios básicos** de manera modular y escalable.  
Su objetivo es ofrecer un entorno funcional donde se puedan administrar **pacientes**, **camillas** y **eventos clínicos** en tiempo real 
mediante integración con un sistema de mensajeria (colas de mensajería (**RabbitMQ**) y base de datos documental (**MongoDB**) ).
Esta API o servicio sirve como base para proyectos de aprendizaje o despliegue real en entornos clínicos simulados.

---

## 🧠 Propósito del Negocio

El propósito principal de esta API es **digitalizar el flujo de atención hospitalaria**, garantizando trazabilidad y consistencia en los procesos operativos:

- Registrar y actualizar pacientes (estado, habitación, fecha de alta, etc.).
- Asignar y liberar camillas según la disponibilidad.
- Publicar eventos automáticos cuando una camilla se libera o un paciente recibe el alta.
- Permitir la consulta y auditoría de los datos mediante **GraphQL Queries**.

---

## ⚙️ Casos de Uso Principales

1. **Ingreso de Paciente:**  
   Se crea un nuevo paciente con su información básica y estado inicial de atención.

2. **Asignación de Camilla:**
    - El sistema verifica que haya camillas disponibles.
    - Asigna automáticamente una camilla libre al paciente.
    - Marca y actualiza el estado de la camilla como “Ocupada”.

3. **Alta del Paciente:**
    - El sistema actualiza el estado del paciente a “Alta”.
    - Libera la camilla asociada y publica un **evento de disponibilidad** en RabbitMQ.
    - Los otros servicios (por ejemplo, un sistema de monitoreo o limpieza) pueden reaccionar automáticamente a ese evento.

4. **Consulta de Datos:**  
   A través de **GraphQL**, se pueden ejecutar consultas como:
    - `obtenerPacientes`
    - `pacientePorId`
    - `camillasDisponibles`
    - `camillaPorId`

---

## 🧩 Reglas de Negocio

- **Regla 1:** Una camilla no puede ser asignada si su estado no es “Disponible”.
- **Regla 2:** Un paciente no puede darse de alta si ya está en estado “Alta” o “Egreso”.
- **Regla 3:** Al liberar una camilla, se debe publicar un evento indicando que vuelve a estar disponible.
- **Regla 4:** Las operaciones de actualización son idempotentes: si una acción ya se ejecutó, el sistema no la repite.
- **Regla 5:** Todos los registros deben tener trazabilidad (fecha, hora y origen del cambio).

---

## 🔄 Integraciones Técnicas (a nivel funcional)

- **RabbitMQ:**  
  Gestiona los eventos asincrónicos del sistema.  
  Ejemplo: cuando un paciente recibe el alta, se publica un evento `"hospital.camilla.disponible"`.

- **MongoDB:**  
  Almacena los documentos de pacientes y camillas.  
  Cada documento mantiene su estado actual y referencias entre sí.

- **GraphQL:**  
  Permite consultar y filtrar la información de manera flexible y centralizada desde un único endpoint.

---

## 📊 Qué se puede esperar del sistema

- **Disponibilidad en tiempo real** de la ocupación hospitalaria.
- **Escalabilidad**: permite agregar nuevos módulos (por ejemplo, doctores, citas, salas).
- **Mensajería reactiva**: los servicios pueden suscribirse a los eventos sin acoplarse directamente.
- **Auditoría completa** de los cambios en pacientes y camillas.

---

## 📊 Tabla de Versiones, Tecnologías y Carpetas

| Versión | Módulo / Carpeta             | Tecnologías Implementadas | Descripción breve |
|----------|------------------------------|----------------------------|--------------------|
| 🏗️ v1.0  | `hospital-api`               | Java 22 · Spring Boot · Maven · Clean Architecture | Estructura base del proyecto, entidades principales y configuración inicial. |
| 🗄️ v1.1  | `hospital-api-mongo`         | MongoDB Atlas · Spring Data Mongo · Docker (mongo:latest) | Integración avanzada con MongoDB, conexión cloud/local y repositorios reactivos. |
| 🔍 v1.2  | `hospital-api-graph`         | Spring Boot GraphQL · Altair · Postman · REST/GraphQL Controllers | Implementación de GraphQL con queries, mutations y manejo de excepciones. |
| 📡 v1.3  | `hospital-api-events`        | RabbitMQ · Spring AMQP · Event-driven Architecture | Comunicación asíncrona mediante eventos (publicación y consumo de colas). |
| ⚙️ v1.4  | `hospital-api-modules`       | Multi-módulo Maven · Project Generator Clean v2 | Organización modular del ecosistema: pacientes, camillas, tareas, hospital. |
| 🐳 v1.5  | `hospital-api-docker`        | Docker · Docker Compose · Render · GHCR.io | Contenerización completa del ecosistema con Mongo y RabbitMQ para despliegue. |

---

> 📘 **Nota:** Cada versión representa un avance funcional independiente, pero todos los módulos pueden integrarse entre sí como parte del ecosistema hospitalario.

---



---

## 📚 Conclusión

El **Hospital API** refleja una arquitectura limpia y funcional, orientada a eventos, que demuestra cómo un entorno hospitalario puede automatizar tareas críticas de disponibilidad, trazabilidad y atención al paciente de forma simple, modular y extensible.

---


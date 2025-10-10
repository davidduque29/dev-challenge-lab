# 🧠 1. Regla general

En GraphQL existen **tres tipos principales de operaciones**:

| Tipo | Propósito | Ejemplo |
|------|------------|---------|
| **Query** | Consultas de solo lectura | `pacientePorId`, `camillasDisponibles` |
| **Mutation** | Modificaciones o acciones que cambian el estado | `crearPaciente`, `actualizarPaciente`, `liberarCamilla` |
| **Subscription** *(opcional)* | Escucha de eventos en tiempo real | `pacienteActualizado`, `nuevaCamillaAsignada` |
>👉 Entonces sí: crear, actualizar, eliminar y similares van en Mutation.

>📂 2. Estructura práctica cuando hay muchas funcionalidades

Cuando el proyecto crece (como tu caso, con más de 100 funcionalidades), lo ideal es dividir el esquema en módulos lógicos.
GraphQL te permite hacerlo sin problema.

Por ejemplo:

Estructura recomendada
```text
src/
        └── main/
        └── resources/
        └── graphql/
        ├── schema.graphqls
             ├── paciente.graphqls
             ├── camilla.graphqls
             ├── hospital.graphqls
```

Y dentro de cada archivo defines el type Mutation y type Query correspondientes.
Spring Boot los fusiona automáticamente al iniciar.
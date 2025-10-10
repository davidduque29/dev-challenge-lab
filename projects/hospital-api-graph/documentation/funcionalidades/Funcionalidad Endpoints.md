# 🔥 Resumen de Mutations y Queries GraphQL - Hospital API

| Acción | Tipo | Nombre | Descripción |
|--------|------|---------|-------------|
| 🧍 Crear paciente | Mutation | `crearPaciente` | Inserta un documento en la colección **pacientes** |
| 🏥 Crear camilla | Mutation | `crearCamilla` | Inserta un documento en la colección **camillas** |
| 🔗 Asignar paciente | Mutation | `asignarPaciente` | Relaciona ambos documentos (camilla + paciente) |
| 🚪 Liberar camilla | Mutation | `liberarCamilla` | Marca la camilla como **disponible** y registra fecha de fin |
| 🔍 Consultar camillas disponibles | Query | `camillasDisponibles` | Devuelve todas las camillas con `estado = "DISPONIBLE"` |
| 🔎 Consultar camilla por ID | Query | `camillaPorId` | Busca una camilla específica por su `id` |
| 👨‍⚕️ Consultar pacientes | Query | `pacientes` | Lista todos los pacientes hospitalizados o dados de alta |

---

## 🧩 Ejemplos de uso en GraphQL Playground o Postman

### ➕ Crear paciente
```graphql
mutation {
  crearPaciente(
    nombres: "Juan Pérez",
    apellidos: "Gómez",
    documentoIdentidad: "1234567890",
    fechaNacimiento: "1990-05-20T00:00:00.000+00:00"
  ) {
    id
    nombres
    apellidos
    documentoIdentidad
  }
}
```
# 🔥 Resumen de Mutations y Queries GraphQL - Hospital API

| Acción | Tipo | Nombre | Descripción |
|--------|------|---------|-------------|
| 🧍 Crear paciente | Mutation | `crearPaciente` | Inserta un documento en la colección **pacientes** |
| 🏥 Crear camilla | Mutation | `crearCamilla` | Inserta un documento en la colección **camillas** |
| 🔗 Asignar paciente | Mutation | `asignarPaciente` | Relaciona ambos documentos (camilla + paciente) |
| 🚪 Liberar camilla | Mutation | `liberarCamilla` | Marca la camilla como **disponible** y registra fecha de fin |
| 🔍 Consultar camillas disponibles | Query | `camillasDisponibles` | Devuelve todas las camillas con `estado = "DISPONIBLE"` |
| 🔎 Consultar camilla por ID | Query | `camillaPorId` | Busca una camilla específica por su `id` |
| 👨‍⚕️ Consultar pacientes | Query | `pacientes` | Lista todos los pacientes hospitalizados o dados de alta |

---

## 🧩 Ejemplos de uso en GraphQL Playground o Postman

## ➕ Crear paciente
```graphql
mutation {
  crearPaciente(
    nombres: "Juan Pérez",
    apellidos: "Gómez",
    documentoIdentidad: "1234567890",
    fechaNacimiento: "1990-05-20T00:00:00.000+00:00"
  ) {
    id
    nombres
    apellidos
    documentoIdentidad
  }
}
```
## ➕ Crear camilla
```graphql
mutation {
    crearCamilla(
        habitacion: "301A",
        estado: "DISPONIBLE"
    ) {
        id
        habitacion
        estado
    }
}
```

## 🔗 Asignar paciente a camilla
```graphql
mutation {
    asignarPaciente(
        idCamilla: "68e84d03c693de24f9824a61",
        idPaciente: "68e847552d4447a1dc59fb76"
    ) {
        id
        estado
        habitacion
        paciente {
            nombres
            apellidos
            fechaAlta
        }
    }
}
```

## 🚪 Liberar camilla
```graphql
mutation {
    liberarCamilla(
        idCamilla: "68e84d03c693de24f9824a61",
        fechaFin: "2025-10-09"
    ) {
        id
        estado
        paciente {
            nombres
        }
    }
}

```

## 🔍 Consultar camillas disponibles
```graphql
query {
    camillasDisponibles {
        id
        habitacion
        estado
    }
}

```

## 🔎 Buscar camilla por ID
```graphql
query {
    camillaPorId(id: "68e84d03c693de24f9824a61") {
        id
        habitacion
        estado
        paciente {
            nombres
        }
    }
}

```

## 👨‍⚕️ Consultar pacientes
```graphql
query {
    pacientes {
        id
        nombres
        apellidos
        fechaAlta
    }
}

```
## 🧠 Notas técnicas

>Los resolvers (CamillaQueryResolverMock, CamillaMutationResolverMock, PacienteMutationResolver) manejan las operaciones definidas en el esquema schema.graphqls.

>MockDataService simula la base de datos, pero puedes sustituirlo fácilmente por una capa Repository conectada a MongoDB.

>Para la conexión real, el schema se carga automáticamente desde src/main/resources/graphql/schema.graphqls por Spring GraphQL Starter.

>Recuerda probar las rutas con el endpoint /graphql y usar POST en Postman.
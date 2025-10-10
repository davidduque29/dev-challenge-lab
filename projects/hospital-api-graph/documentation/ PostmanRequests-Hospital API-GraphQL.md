# 📦 Postman Requests — Hospital API (GraphQL)

# ⚡ Requests GraphQL (formato simple para Postman)

## 🧍 Crear Paciente
{
"query": "mutation { crearPaciente(nombres: \"Juan\", apellidos: \"Pérez Gómez\", documentoIdentidad: \"1234567890\", fechaNacimiento: \"1990-05-20T00:00:00.000+00:00\") { id nombres apellidos documentoIdentidad fechaNacimiento } }"
}

---

## 🏥 Crear Camilla
{
"query": "mutation { crearCamilla(habitacion: \"301A\", estado: \"DISPONIBLE\") { id habitacion estado } }"
}

---

## 🔗 Asignar Paciente a Camilla
{
"query": "mutation { asignarPaciente(idCamilla: \"68e84d03c693de24f9824a61\", idPaciente: \"68e847552d4447a1dc59fb76\") { id estado habitacion paciente { nombres apellidos fechaAlta } } }"
}

---

## 🚪 Liberar Camilla
{
"query": "mutation { liberarCamilla(idCamilla: \"68e84d03c693de24f9824a61\", fechaFin: \"2025-10-09\") { id estado habitacion paciente { nombres } } }"
}

---

## 🔍 Consultar Camillas Disponibles
{
"query": "query { camillasDisponibles { id habitacion estado paciente { nombres apellidos } } }"
}

---

## 🔎 Buscar Camilla por ID
{
"query": "query { camillaPorId(id: \"68e84d03c693de24f9824a61\") { id habitacion estado paciente { nombres apellidos fechaAlta } } }"
}

---

## 👨‍⚕️ Consultar Pacientes
{
"query": "query { pacientes { id nombres apellidos documentoIdentidad fechaNacimiento fechaAlta } }"
}

---

## ✏️ Actualizar Paciente
{
"query": "mutation { actualizarPaciente(id: \"68e847552d4447a1dc59fb76\", nombres: \"Juan Carlos\", apellidos: \"Gómez\", documentoIdentidad: \"1234567890\", fechaNacimiento: \"1990-05-20T00:00:00.000+00:00\", fechaAlta: null) { id nombres apellidos documentoIdentidad fechaNacimiento fechaAlta } }"
}

---

## 🧱 Actualizar Camilla
{
"query": "mutation { actualizarCamilla(id: \"68e84d03c693de24f9824a61\", habitacion: \"301B\", estado: \"OCUPADA\") { id habitacion estado paciente { nombres } } }"
}

---

### ⚙️ Configuración general para todos
- **Método:** POST
- **URL:** `http://localhost:8080/graphql`
- **Headers:**
  - `Content-Type: application/json`

---

¿Quieres que te los empaquete en un `.json` de **Postman Collection** listo para importar? Te lo genero con nombre, icono y ejemplos incluidos 💾


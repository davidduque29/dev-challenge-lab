# 🧠 Conceptos clave de GraphQL
## 1. GraphQL

Es un lenguaje de consultas para APIs desarrollado por Facebook.
A diferencia de REST (que tiene endpoints fijos como /pacientes, /camillas, /habitaciones), 
GraphQL usa una sola URL donde el cliente define qué datos quiere y cómo los quiere.

🔍 Definición técnica

GraphQL permite al cliente definir exactamente qué datos necesita y el servidor responde solo con esa información, evitando respuestas innecesarias o múltiples peticiones.

🧩 Conceptos claves

| 🧩 Concepto | 💡 Descripción |
|--------------|----------------|
| 🔍 **Definición técnica** | GraphQL permite al cliente **definir exactamente qué datos necesita** y el servidor responde **solo con esa información**, evitando respuestas innecesarias o múltiples peticiones. |
| 🧱 **Schema (Esquema)** | Define los **tipos de datos** y las **operaciones disponibles** en la API. |
| 🔎 **Query** | Operación para **consultar información**, equivalente a un **GET** en REST. |
| ✏️ **Mutation** | Operación para **modificar datos** (crear, actualizar o eliminar). |
| ⚙️ **Resolver** | Función que se ejecuta para cada campo solicitado; se encarga de **obtener los datos reales** desde la fuente (BD, servicio, etc.). |
| 🔔 **Subscription** | Permite **recibir datos en tiempo real** cuando ocurren cambios en el servidor. |

## ⚖️ Ventajas y Desventajas de GraphQL

| ✅ Ventajas | 🚫 Desventajas | 💬 Ejemplo |
|--------------|----------------|-------------|
| 🚀 **Obtienes solo los datos necesarios**, evitando el overfetching (traer más datos de los necesarios). | ⚙️ **Configuración inicial más compleja** que REST. | En una consulta de pacientes puedes traer solo `nombre` y `edad`, sin toda la ficha médica. |
| 🔁 **Menos peticiones** al servidor (underfetching)(tener que hacer múltiples llamadas para completar una vista). | 🧩 **Mayor carga en el servidor** si las consultas son muy anidadas. | Una sola query puede devolver paciente + camilla asignada + habitación. |
| 📚 **Autodocumentado:** el schema describe las operaciones disponibles. | 🔐 **Manejo de caché y errores** más complejo que REST. | Las herramientas como Apollo o GraphiQL permiten explorar el schema visualmente. |
| 🌐 **Flexible y escalable:** fácil agregar nuevos campos sin romper clientes existentes. | 🧠 **Curva de aprendizaje** para equipos acostumbrados a REST. | Puedes agregar el campo `telefono` al tipo `Paciente` sin afectar a las consultas actuales. |

Ejemplo REST:

GET /paciente/10
GET /camilla/5


Ejemplo GraphQL:

```graphql
{
camilla(id: 5) {
id
estado
habitacion
paciente {
nombre
fechaAlta
}
}
}
```

>👉 Con una sola consulta obtienes toda la info relacionada.

## 2. Resolver

Un resolver es como el “controlador” en GraphQL.
Cada campo o tipo de dato que defines en tu schema necesita una función resolver que diga cómo obtener esos datos.

Por ejemplo, si defines el tipo:

```graphql
type Camilla {
id: ID
estado: String
habitacion: String
paciente: Paciente
}
```

El resolver en Spring Boot podría verse así:

@Component
public class CamillaResolver implements GraphQLQueryResolver {

    private final CamillaService camillaService;

    public CamillaResolver(CamillaService camillaService) {
        this.camillaService = camillaService;
    }

    public Camilla getCamilla(Long id) {
        return camillaService.obtenerCamillaPorId(id);
    }
}


## 3. Mutation

Mientras las queries sirven para consultar datos, las mutations sirven para modificarlos (crear, actualizar, eliminar).

Por ejemplo, cuando un paciente es dado de alta y la camilla queda libre:
```graphql
mutation {
liberarCamilla(idCamilla: 5, fechaFin: "2025-10-08") {
id
estado
habitacion
disponibleDesde
}
}
```

Y su resolver:
```java
@Component
public class CamillaMutation implements GraphQLMutationResolver {

    private final CamillaService camillaService;

    public CamillaMutation(CamillaService camillaService) {
        this.camillaService = camillaService;
    }

    public Camilla liberarCamilla(Long idCamilla, String fechaFin) {
        return camillaService.liberarCamilla(idCamilla, fechaFin);
    }
}
```
### 🏥 Caso de uso: Disponibilidad de Camillas

Podrías definir el siguiente schema (schema.graphqls):

```graphql
type Camilla {
id: ID!
estado: String!
habitacion: String!
paciente: Paciente
fechaInicio: String
fechaFin: String
}

type Paciente {
id: ID!
nombre: String!
fechaAlta: String
}

type Query {
camillasDisponibles: [Camilla]
camillaPorId(id: ID!): Camilla
}

type Mutation {
liberarCamilla(idCamilla: ID!, fechaFin: String!): Camilla
}
```

Esto te permitiría:

* Ver todas las camillas disponibles.

* Consultar los datos del paciente asignado.

* Liberar una camilla al dar de alta un paciente.

⚡ ¿Qué tan útil es GraphQL en términos de performance?

Ventajas:

* Excelente para apps front-end (por ejemplo, si luego haces un panel React o Angular que muestra camas, pacientes, habitaciones, etc.).

* Reduce llamadas HTTP, porque puedes traer todo en una sola query.

* Escalable para consultas complejas (p. ej., anidaciones entre pacientes → camillas → habitaciones).

Desventajas:

* Más lógica en el backend (los resolvers pueden volverse pesados si no se optimizan).

* Mayor carga del servidor si no se cachean las consultas o si los resolvers hacen demasiadas llamadas a BD.

* En tu caso, para disponibilidad de camillas, no hay gran volumen de datos ni consultas altamente dinámicas, así que el beneficio en performance sería más en flexibilidad que en velocidad.

## Uso plugin en intelliJ “GraphQL” de JetBrains
Puedes instalar el plugin de GraphQL en IntelliJ para facilitar la escritura y validación de tus queries y schemas.

### 🧰 Qué hace el plugin

✅ Resalta y valida sintaxis de tus archivos .graphql o .graphqls.

✅ Te muestra autocompletado de tipos, queries y mutations.

✅ Te ayuda a navegar entre el schema y los resolvers Java.

✅ Permite testear queries directamente desde IntelliJ (sin tener que abrir GraphiQL)

✅ Detecta errores antes de ejecutar el backend.

🔒 En cuanto a seguridad

Es un plugin oficial de JetBrains, mantenido por ellos mismos (no de terceros).

No agrega vulnerabilidades conocidas; solo trabaja sobre el IDE, no ejecuta código externo ni expone puertos.

Las versiones como 241.14494.150 son compatibles con IntelliJ 2024.x, así que estás en rango seguro.

💡 Solo evita instalar el otro (Apollo GraphQL) si no planeas usar React o Node, ya que ese sí agrega dependencias web que no necesitas en tu proyecto Java.


>🚀 Recomendación para tu proyecto Hospital

✅ Usa GraphQL solo en la capa de consultas complejas (por ejemplo, si tu front necesita combinar datos de varios servicios).

✅ Mantén REST para operaciones simples o masivas (como registrar pacientes o listar todos).

✅ Implementa resolvers ligeros que llamen a servicios ya existentes.

✅ Luego, puedes probar herramientas como GraphiQL o Voyager para visualizar tu esquema.
# 🧠 Qué pasa internamente
## 1️⃣ Spring detecta resolvers como beans del contexto

Cualquier clase con @Controller, @Component o @Service se registra automáticamente al iniciar la aplicación.

Ejemplo:
```java   
@Controller
public class CamillaMutationResolverMock {
   ...
}
```

➡️ Spring lo registrará internamente como un bean con el nombre:

>camillaMutationResolverMock


Y GraphQL lo reconocerá si y solo si las mutations que contiene no están duplicadas con otro resolver.
---
## 2️⃣ Si mantienes el nombre “Mock” y ambos resolvers existen (el nuevo real + el mock)

No pasa nada si el mock tiene otras operaciones (por ejemplo, datos en memoria).
>✅ Lo importante es que no definas el mismo método GraphQL (por ejemplo, dos @MutationMapping liberarCamilla() en distintas clases).

>⚠️ Si duplicas los nombres, Spring lanzará error:

Ambiguous handler methods mapped for GraphQL mutation 'liberarCamilla'
---
## 3️⃣ Recomendación práctica

🔹 Mantén ambos resolvers por ahora, pero:

Asegúrate que el “mock” use nombres de métodos diferentes,
o coméntalos mientras migras a Mongo.

O bien, elimina el @Controller del mock temporal:

```java  
//@Controller
public class CamillaMutationResolverMock { ... }
```

Así el bean no se registra, pero puedes mantenerlo en el código para referencia.
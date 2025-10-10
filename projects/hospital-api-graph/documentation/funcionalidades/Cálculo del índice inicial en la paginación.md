### 📘 Explicación de la fórmula: `int start = (page - 1) * size;`

Esta fórmula calcula **el índice inicial** (posición en la lista) desde donde debe comenzar la paginación.

#### 🧠 Lógica:

- `page` → número de página actual (comienza en 1).
- `size` → cantidad de elementos que se muestran por página.
- Restamos 1 a `page` porque los índices de las listas en Java **empiezan en 0**.

#### 📊 Ejemplo:
| page | size | Cálculo                  | Resultado | Significado                      |
|------|------|--------------------------|------------|-----------------------------------|
| 1    | 5    | (1 - 1) * 5 = 0          | 0          | Empieza desde el elemento 0      |
| 2    | 5    | (2 - 1) * 5 = 5          | 5          | Empieza desde el elemento 5      |
| 3    | 5    | (3 - 1) * 5 = 10         | 10         | Empieza desde el elemento 10     |

#### 💬 En resumen:
👉 Esta fórmula **traduce el número de página** en **el índice inicial dentro de la lista** 
desde donde se deben tomar los registros.

### ⚙️ Diagrama de la lógica de paginación


Tamaño de página (`size`) = 3  
Fórmula: `start = (page - 1) * size`

| Página (`page`) | Cálculo de `start` | Índices que se toman | Hospitales devueltos |
|------------------|--------------------|-----------------------|----------------------|
| 1 | (1 - 1) * 3 = 0 | [0 → 2] | [H1, H2, H3] |
| 2 | (2 - 1) * 3 = 3 | [3 → 5] | [H4, H5, H6] |
| 3 | (3 - 1) * 3 = 6 | [6 → 8] | [H7, H8, H9] |
| 4 | (4 - 1) * 3 = 9 | [9 → 9] | [H10] |

📊 **Flujo lógico**
Supongamos que tenemos una lista con 10 hospitales:

🧠 **Resumen:**
- Cada página "avanza" en múltiplos del tamaño definido.
- Si `page = 1` empieza en el índice `0`.
- Si `page = 2` salta los 3 primeros (`0,1,2`) y empieza en el índice `3`.
- Si la lista se acaba, retorna una lista vacía.

## Funcionalidades — Expense Tracker App
1) Visión

Aplicación móvil (Android, Kotlin) para registrar ingresos y gastos, consultar saldo en tiempo real y visualizar estadísticas por mes y categoría, con una interfaz simple y ligera.

2) Alcance de la versión 1 (MVP)
   
2.1 Módulos y funciones obligatorias

Movimientos (CRUD mínimo)

Crear Ingreso o Gasto con: fecha (auto por defecto), categoría, monto y nota opcional.

Listado cronológico de movimientos.

Eliminar un movimiento (confirmación).

Saldo actual

Cálculo en tiempo real: Saldo = sum(Ingresos) – sum(Gastos).

Filtros

Por mes (selector de mes 01–12).

Por categoría (todas / categoría específica).

Botón Limpiar filtros.

Estadísticas

Barras por mes: Ingresos vs Gastos (dos colores).

Torta por categoría (opcional) del mes filtrado.

Persistencia temporal

En memoria (repositorio singleton) para MVP.

UI

Pantalla principal (saldo + lista + filtros + acciones).

Pantalla agregar movimiento.

Pantalla estadísticas.

2.2 Fuera de alcance en V1 (Backlog inmediato)

Persistencia con Room.

Exportar CSV / compartir reporte.

Multimoneda.

Recordatorios (por ejemplo, “registrar gastos del día”).

Respaldo local / nube.

3) Historias de usuario (V1)

Registrar movimiento

Como usuario, quiero registrar un ingreso o gasto con categoría y monto, para llevar el control diario.

Criterios de aceptación

Si el monto no es válido (> 0), debo ver un mensaje de error.

La fecha se sugiere con hoy, pero puede editarse (en V2).

Al guardar, vuelvo al Home y veo el nuevo movimiento arriba.

Ver saldo

Como usuario, quiero ver mi saldo actual en la parte superior, para saber cómo voy.

Criterios: el saldo cambia al crear/eliminar movimientos.

Filtrar por mes/categoría

Como usuario, quiero filtrar la lista por mes y categoría, para enfocarme en un periodo y tipo de gasto.

Criterios: filtros combinables; botón “Limpiar” devuelve a vista general.

Ver estadísticas

Como usuario, quiero gráficos con colores distintos para ingresos y gastos, para comparar visualmente.

Criterios: barras con 12 meses; color A = ingresos, color B = gastos; leyenda visible; valores con formato moneda.

4) Reglas y validaciones

Monto: obligatorio, numérico y > 0.

Tipo: Ingreso o Gasto (radio buttons).

Categoría: obligatorio (lista predefinida, editable en V2).

Fecha: por defecto hoy (selector manual en V2).

Confirmaciones: al eliminar movimiento, diálogo “¿Eliminar este movimiento?”.

5) Modelo de datos (MVP)
   
data class Expense(
    val id: Int,
    val date: String,     // "yyyy-MM-dd"
    val category: String, // "Alimentación", "Transporte", etc.
    val amount: Double,
    val type: String      // "Ingreso" o "Gasto"
)


Repositorio temporal

addExpense(date, category, amount, type)

getAll(): List<Expense>

getBalance(): Double

deleteById(id: Int)

filter(month: Int?, category: String?): List<Expense>

6) Catálogo de categorías (sugerido)

Ingresos: Salario, Freelance, Ventas, Otros.

Gastos: Alimentación, Transporte, Servicios, Entretenimiento, Educación, Salud, Hogar, Otros.

En V2 se agregará gestión de categorías (crear/editar/eliminar, iconos).

7) UX / Flujo de pantallas
7.1 Home

Top: “Saldo actual: $X”.

Filtros: spinnerMonth, spinnerCategory, botón “Limpiar”.

Acción: Botón Agregar (+), Botón Estadísticas.

Lista: ListView con fecha – categoría – monto – tipo.

Gestos: long-press → eliminar (con confirmación).

7.2 Agregar movimiento

Inputs: Tipo (Ingreso/Gasto), Categoría, Monto, Nota (V2).

Guardar y Cancelar.

Validaciones en Guardar.

7.3 Estadísticas

Barras (12 meses):

Ingresos vs Gastos con dos colores distintos.

(Opcional) Torta del mes filtrado por categoría.

8) No funcionales (V1)

Rendimiento: listas < 2.000 elementos en V1 (fluido).

Accesibilidad: contraste suficiente, labels claros.

Soporte: Android minSdk 24, targetSdk 34.

UI: Material Components.

9) Métricas y analítica (V2)

Nº movimientos/mes.

% gasto por categoría.

Días sin registrar.

Exportación a CSV/PDF.

10) Roadmap

V1 (MVP): CRUD, saldo, filtros, estadísticas con dos colores (Ingresos/Gastos), in-memory repo.

V2: Room, edición de movimiento, selector de fecha, exportar CSV, gestión de categorías.

V3: recordatorios, multimoneda, temas oscuro/claro, respaldo en nube.

11) Criterios de calidad y pruebas

Unit: repositorio (getBalance, filter, deleteById).

UI: validaciones en AddExpenseActivity.

Instrumentadas: navegación entre actividades.

Smoke: abrir app, crear ingreso, crear gasto, filtrar, ver gráfico, eliminar.

12) CI/CD (GitHub Actions)

Workflow Android:

./gradlew assembleDebug

Cache de Gradle.

Permisos para gradlew (si hace falta en Windows → .gitattributes).

Regla: PR a main requiere build verde.

13) Tareas sugeridas (issues)

 Preparar catálogo de categorías.

 Implementar deleteById con diálogo (list long-press).

 Formateo de moneda en lista y gráficos.

 StatsActivity: barras Ingresos (color A) vs Gastos (color B).

 Filtro por mes/categoría + botón “Limpiar”.

 README con capturas (colocar en documentos/imagenes).

 Migrar a Room (V2).

14) Colores sugeridos (consistentes con gráficos)

Ingresos: #4CAF50 (verde).

Gastos: #F44336 (rojo).

Fondo/acentos compatibles con Material 3.

15) Enlaces internos del repo

documentos/ideas.md — backlog y mejoras.

documentos/ui.md — capturas y flujos de pantallas.

documentos/imagenes/ — imágenes vinculadas en los .md.

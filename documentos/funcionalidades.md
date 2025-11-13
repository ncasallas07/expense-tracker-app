## Funcionalidades — Expense Tracker App
1) Visión

Aplicación móvil (Android, Kotlin) para registrar ingresos y gastos, consultar saldo en tiempo real y visualizar estadísticas por mes y categoría, con una interfaz simple y ligera.

2) Alcance de la versión 1 (MVP)
   
2.1 Módulos y funciones obligatorias

Movimientos (CRUD mínimo)

Crear Ingreso o Gasto con: fecha (auto por defecto), categoría y monto.

Listado cronológico de movimientos.

Saldo actual

Cálculo en tiempo real: Saldo = sum(Ingresos) – sum(Gastos).

Filtros

Por mes (selector de mes 01–12).

Por categoría (todas / categoría específica).

Botón Limpiar filtros.

Estadísticas

Torta por categoría del mes filtrado.

Persistencia temporal

UI

Pantalla principal (saldo + lista + filtros + acciones).

Pantalla agregar movimiento.

Pantalla estadísticas.

2.2 Fuera de alcance en V1 (Backlog inmediato)

Exportar CSV / compartir reporte.

Multimoneda.

Recordatorios (por ejemplo, “registrar gastos del día”).

Respaldo local / nube.

3) Historias de usuario (V1)

Registrar movimiento

° Como usuario, quiero registrar un ingreso o gasto con categoría y monto, para llevar el control diario.

Criterios de aceptación

Si el monto no es válido (> 0), no lo deja digitar.

La fecha se sugiere con hoy, pero puede editarse (en V2).

Al guardar, vuelvo al Home y veo el nuevo movimiento arriba.

Ver saldo

° Como usuario, quiero ver mi saldo actual en la parte superior, para saber cómo voy.

Criterios: el saldo cambia al crear movimientos.

Filtrar por mes/categoría

° Como usuario, quiero filtrar la lista por mes y categoría, para enfocarme en un periodo y tipo de gasto.

Criterios: filtros combinables; botón “Limpiar” devuelve a vista general.

Ver estadísticas

° Como usuario, quiero gráficos con colores distintos para ingresos y gastos, para comparar visualmente.

Criterios: Torta por tipo de movimiento con dos colores para evidenciar a que corresponde, barras con 12 meses; color A = ingresos, color B = gastos (V2); leyenda visible; valores con formato moneda.

4) Reglas y validaciones

Monto: obligatorio, numérico y > 0.

Tipo: Ingreso o Gasto.

Categoría: obligatorio.

Fecha: por defecto hoy (selector manual en V2).

Confirmaciones: al eliminar movimiento, diálogo “¿Eliminar este movimiento?” (V2).

5) Modelo de datos (MVP)
   
<img width="499" height="166" alt="image" src="https://github.com/user-attachments/assets/d26fdeb2-f6ba-4781-9414-afd4ede535fd" />

**Repositorio temporal**

**addExpense(date, category, amount, type)**

**getAll(): List<Expense>**

**getBalance(): Double**

**deleteById(id: Int)**

**filter(month: Int?, category: String?): List<Expense>**

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

Torta (12 meses):

Ingresos vs Gastos con dos colores distintos.

(Opcional) del mes filtrado por categoría.

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

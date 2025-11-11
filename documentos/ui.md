# UI — Expense Tracker App (resumen)

## Pantallas

### 1) Home
- **Qué muestra:** Saldo actual, filtros y lista de movimientos.
- **Controles (ids):**
  - Saldo: `tvBalance`
  - Filtro Mes: `spinnerMonth`
  - Filtro Categoría: `spinnerCategory`
  - Limpiar filtros: `btnClearFilters`
  - Agregar: `btnAdd`
  - Estadísticas: `btnStats`
  - Lista: `listView`
  - Vacío: `tvEmpty`
- **Acciones:**
  - `btnAdd` → abre agregar movimiento.
  - `btnStats` → abre estadísticas.
  - Long-press en item → confirmar eliminar.

---

### 2) Agregar movimiento
- **Campos:** Tipo (Ingreso/Gasto), Categoría, Monto.
- **Ids:**
  - `rbIncome`, `rbExpense` (en un `RadioGroup`)
  - `etCategory`, `etAmount`
  - `btnSave`
- **Validación:** categoría no vacía, monto > 0.
- **Resultado:** guarda y vuelve a Home refrescando saldo/lista.

---

### 3) Estadísticas
- **Gráfico:** Barras comparando Ingresos vs Gastos por mes.
- **Id:** `barChart`
- **Colores:** Ingresos `#4CAF50` (verde), Gastos `#F44336` (rojo).
- **Detalles:** leyenda visible, eje Y en moneda.

---

### 4) Diálogo eliminar
- **Título:** “¿Eliminar este movimiento?”
- **Botones:** Eliminar / Cancelar.

---

## Estilo rápido
- **Tamaños:** títulos 20sp, texto 16sp, espaciado 8–16dp.
- **Toque mínimo:** 48dp.
- **Accesibilidad:** `contentDescription` en iconos, buen contraste.

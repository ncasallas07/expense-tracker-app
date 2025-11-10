package com.nelson.expensetracker.data

data class Expense(
    val id: Int,
    val date: String,      // yyyy-MM-dd
    val category: String,
    val amount: Double,
    val type: String       // "Ingreso" o "Gasto"
)

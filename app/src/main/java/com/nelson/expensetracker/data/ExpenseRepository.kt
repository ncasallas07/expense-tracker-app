package com.nelson.expensetracker.data

import java.util.concurrent.atomic.AtomicInteger

object ExpenseRepository {
    private val expenses = mutableListOf<Expense>()
    private val idGen = AtomicInteger(1)

    fun addExpense(date: String, category: String, amount: Double, type: String): Int {
        val id = idGen.getAndIncrement()
        expenses.add(Expense(id, date, category, amount, type))
        return id
    }

    fun getAll(): List<Expense> = expenses

    fun getById(id: Int): Expense? = expenses.find { it.id == id }

    fun updateExpense(id: Int, category: String, amount: Double, type: String) {
        val idx = expenses.indexOfFirst { it.id == id }
        if (idx >= 0) {
            val e = expenses[idx]
            expenses[idx] = e.copy(category = category, amount = amount, type = type)
        }
    }

    fun deleteById(id: Int) {
        expenses.removeAll { it.id == id }
    }

    fun getBalance(): Double =
        expenses.fold(0.0) { acc, e -> if (e.type == "Ingreso") acc + e.amount else acc - e.amount }

    fun filter(month: Int?, category: String?): List<Expense> =
        expenses.filter { e ->
            val monthOk = month == null || e.date.substring(5,7).toIntOrNull() == month
            val catOk = category.isNullOrBlank() || e.category.equals(category, ignoreCase = true)
            monthOk && catOk
        }
}

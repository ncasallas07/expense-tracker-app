package com.nelson.expensetracker.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nelson.expensetracker.data.ExpenseRepository
import com.nelson.expensetracker.databinding.ActivityAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Nuevo movimiento"

        binding.btnSave.setOnClickListener {
            val category = binding.etCategory.text.toString()
            val amountStr = binding.etAmount.text.toString()
            val type = if (binding.rbIncome.isChecked) "Ingreso" else "Gasto"

            if (category.isBlank() || amountStr.isBlank()) {
                Toast.makeText(this, "Complete categoría y monto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            ExpenseRepository.addExpense(date, category, amount, type)

            Toast.makeText(this, "$type guardado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

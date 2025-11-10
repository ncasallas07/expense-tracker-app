package com.nelson.expensetracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nelson.expensetracker.data.Expense
import com.nelson.expensetracker.data.ExpenseRepository
import com.nelson.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedMonth: Int? = null
    private var selectedCategory: String? = null
    private var currentList: List<Expense> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Expense Tracker"

        // Botones
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
        binding.btnStats.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }

        // Spinner de meses
        val months = listOf("", "01","02","03","04","05","06","07","08","09","10","11","12")
        binding.spinnerMonth.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            months
        )
        binding.spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val value = parent.getItemAtPosition(position) as String
                selectedMonth = value.toIntOrNull()
                refreshUI()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Spinner de categorías
        val categories = listOf("", "Alimentación", "Transporte", "Servicios", "Entretenimiento", "Otros")
        binding.spinnerCategory.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val value = parent.getItemAtPosition(position) as String
                selectedCategory = if (value.isBlank()) null else value
                refreshUI()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnClearFilters.setOnClickListener {
            binding.spinnerMonth.setSelection(0)
            binding.spinnerCategory.setSelection(0)
        }

        // Pulsación larga para Editar/Eliminar (opcional; puedes quitar este bloque si no lo estás usando)
        binding.listView.setOnItemLongClickListener { _, _, position, _ ->
            if (position < 0 || position >= currentList.size) return@setOnItemLongClickListener true
            val exp = currentList[position]
            val options = arrayOf("Editar", "Eliminar", "Cancelar")
            AlertDialog.Builder(this)
                .setTitle("Acción para el movimiento")
                .setItems(options) { dlg, which ->
                    when (which) {
                        0 -> {
                            val i = Intent(this, AddExpenseActivity::class.java)
                            i.putExtra("expenseId", exp.id)
                            startActivity(i)
                        }
                        1 -> {
                            AlertDialog.Builder(this)
                                .setMessage("¿Eliminar este movimiento?")
                                .setPositiveButton("Eliminar") { _, _ ->
                                    ExpenseRepository.deleteById(exp.id)
                                    refreshUI()
                                }
                                .setNegativeButton("Cancelar", null)
                                .show()
                        }
                        else -> dlg.dismiss()
                    }
                }.show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        refreshUI()
    }

    private fun refreshUI() {
        val balance = ExpenseRepository.getBalance()
        binding.tvBalance.text = "Saldo actual: $%.2f".format(balance)

        currentList = ExpenseRepository.filter(selectedMonth, selectedCategory)
        val list = currentList.map { e ->
            "${e.date} • ${e.category} • $%.2f • ${e.type}".format(e.amount)
        }
        binding.listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            list
        )
        binding.tvEmpty.text = if (list.isEmpty()) "Sin movimientos" else ""
    }
}

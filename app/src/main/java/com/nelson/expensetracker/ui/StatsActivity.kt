package com.nelson.expensetracker.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.nelson.expensetracker.data.ExpenseRepository
import com.nelson.expensetracker.databinding.ActivityStatsBinding
import kotlin.math.abs

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Estadísticas"
        drawPie()
    }

    private fun drawPie() {
        val all = ExpenseRepository.getAll()
        val ingresos = all.filter { it.type.equals("Ingreso", ignoreCase = true) }
            .sumOf { it.amount }
        val gastos = all.filter { it.type.equals("Gasto", ignoreCase = true) }
            .sumOf { it.amount }

        val totalIngresos = ingresos.coerceAtLeast(0.0)
        val totalGastos = abs(gastos).coerceAtLeast(0.0)

        val entries = mutableListOf<PieEntry>()
        if (totalIngresos > 0.0) entries += PieEntry(totalIngresos.toFloat(), "Ingresos")
        if (totalGastos > 0.0)   entries += PieEntry(totalGastos.toFloat(),   "Gastos")

        val chart = binding.chart

        if (entries.isEmpty()) {
            chart.clear()
            chart.centerText = "Sin datos"
            chart.invalidate()
            return
        }

        val dataSet = PieDataSet(entries, "Ingresos vs Gastos").apply {
            // Verde = Ingresos, Rojo = Gastos
            colors = listOf(Color.parseColor("#2E7D32"), Color.parseColor("#C62828"))
            sliceSpace = 2f
            valueTextSize = 14f
            valueTextColor = Color.BLACK
        }

        // Formato $X.XXX
        val moneyFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$" + String.format("%,.2f", value)
            }
        }

        val data = PieData(dataSet).apply {
            setValueFormatter(moneyFormatter)
        }

        chart.apply {
            setUsePercentValues(false)
            setDrawEntryLabels(true)
            setEntryLabelColor(Color.DKGRAY)
            setEntryLabelTextSize(12f)
            description.isEnabled = false
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            setHoleColor(Color.TRANSPARENT)
            holeRadius = 45f
            transparentCircleRadius = 50f
            centerText = "Resumen"
            setCenterTextSize(14f)
            this.data = data
            animateY(800)
            invalidate()
        }
    }
}

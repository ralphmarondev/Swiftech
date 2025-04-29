package com.ralphmarondev.swiftech.admin_features.reports.presentation.components

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun ReportBarChart() {
    val barEntries = listOf(
        BarEntry(0f, 120f),
        BarEntry(1f, 90f),
        BarEntry(2f, 60f),
        BarEntry(3f, 30f),
        BarEntry(4f, 10f)
    )
    val barDataSet = BarDataSet(barEntries, "Ratings")
    barDataSet.apply {
        color = Color.parseColor("#B388FF")
        valueTextColor = Color.BLACK
        valueTextSize = 12f
    }

    val barData = BarData(barDataSet)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Ratings",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    this.data = barData
                    this.description.isEnabled = false // disable description text
                    this.setDrawValueAboveBar(true)
                    this.xAxis.valueFormatter = IndexAxisValueFormatter(
                        listOf(
                            "Excellent",
                            "Very Good",
                            "Good",
                            "Fair",
                            "Poor"
                        )
                    )
                    this.invalidate() // refresh the chart
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}
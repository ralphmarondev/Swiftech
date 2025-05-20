package com.ralphmarondev.swiftech.admin_features.reports.presentation.components

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
//
//fun safeValue(count: Int, hasNonZero: Boolean) =
//    if (count == 0 && !hasNonZero) 0.01f else count.toFloat()

@Composable
fun StudentReportBarChart(
    ratingCounts: RatingCounts,
    modifier: Modifier = Modifier
) {
    val allCounts = listOf(
        ratingCounts.excellent,
        ratingCounts.veryGood,
        ratingCounts.good,
        ratingCounts.fair,
        ratingCounts.poor
    )
    val hasNonZero = allCounts.any { it > 0 }

    LaunchedEffect(Unit) {
        Log.d("App", "Reportbarchart data: $allCounts")
    }

    val barEntries = listOf(
        BarEntry(0f, safeValue(ratingCounts.excellent, hasNonZero)),
        BarEntry(1f, safeValue(ratingCounts.veryGood, hasNonZero)),
        BarEntry(2f, safeValue(ratingCounts.good, hasNonZero)),
        BarEntry(3f, safeValue(ratingCounts.fair, hasNonZero)),
        BarEntry(4f, safeValue(ratingCounts.poor, hasNonZero))
    )
    val barDataSet = BarDataSet(barEntries, "Rating Count")
    barDataSet.apply {
        color = MaterialTheme.colorScheme.primary.toArgb()
        valueTextColor = MaterialTheme.colorScheme.primary.toArgb()
        valueTextSize = 12f
    }

    val labels = listOf(
        "Excellent",
        "Very Good",
        "Good",
        "Fair",
        "Poor"
    )
    val barData = BarData(barDataSet)
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                this.data = barData
                this.description.isEnabled = false // disable description text
                this.setDrawValueAboveBar(true)

                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(labels)
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    position =
                        com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM

                    // coz some label are missing :)
//                        labelRotationAngle = -45f
                    granularity = 1f
                    isGranularityEnabled = true
                    setLabelCount(labels.size, false)
//                        setAvoidFirstLastClipping(true)
                    xAxis.axisMinimum = -0.5f
                    xAxis.axisMaximum = 4.5f
                }

                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                legend.isEnabled = false
                animateY(700)
                invalidate() // refresh the chart lol
            }
        },
        modifier = modifier
    )
}
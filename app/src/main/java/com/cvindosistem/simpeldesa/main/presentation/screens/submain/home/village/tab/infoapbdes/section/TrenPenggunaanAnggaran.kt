package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyLargeText

@Composable
internal fun TrenPenggunaanAnggaranSection(
    modifier: Modifier = Modifier
) {
    val trenData = getTrenAnggaranData()

    AppCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            BodyLargeText(
                text = "Tren Penggunaan Anggaran",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Legend
            ChartLegendRow(
                data = trenData,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Line Chart
            LineChartComponent(
                data = trenData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
    }
}

@Composable
private fun ChartLegendRow(
    data: List<TrenAnggaranData>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        data.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(color = item.color, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = item.category,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun LineChartComponent(
    data: List<TrenAnggaranData>,
    modifier: Modifier = Modifier
) {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "Mei")
    val maxValue = data.flatMap { it.values }.maxOrNull() ?: 60f
    val minValue = 0f
    val yAxisLabels = listOf(0, 15, 30, 45, 60)

    Canvas(modifier = modifier.padding(start = 24.dp, bottom = 24.dp, end = 16.dp, top = 16.dp)) {
        val chartWidth = size.width - 40.dp.toPx()
        val chartHeight = size.height - 40.dp.toPx()
        val startX = 40.dp.toPx()
        val startY = 20.dp.toPx()

        // Draw grid lines and Y-axis labels
        yAxisLabels.forEach { label ->
            val y = startY + chartHeight - (label.toFloat() / maxValue * chartHeight)

            // Grid line
            drawLine(
                color = Color.Gray.copy(alpha = 0.3f),
                start = Offset(startX, y),
                end = Offset(startX + chartWidth, y),
                strokeWidth = 1.dp.toPx()
            )

            // Y-axis label
            drawContext.canvas.nativeCanvas.drawText(
                label.toString(),
                startX - 30.dp.toPx(),
                y + 5.dp.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 12.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Draw X-axis labels
        months.forEachIndexed { index, month ->
            val x = startX + (index * (chartWidth / (months.size - 1)))
            drawContext.canvas.nativeCanvas.drawText(
                month,
                x,
                startY + chartHeight + 20.dp.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 12.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Draw lines for each category
        data.forEach { categoryData ->
            val points = categoryData.values.mapIndexed { index, value ->
                val x = startX + (index * (chartWidth / (categoryData.values.size - 1)))
                val y = startY + chartHeight - (value / maxValue * chartHeight)
                Offset(x, y)
            }

            // Draw line segments
            for (i in 0 until points.size - 1) {
                drawLine(
                    color = categoryData.color,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Draw points
            points.forEach { point ->
                drawCircle(
                    color = categoryData.color,
                    radius = 4.dp.toPx(),
                    center = point
                )
                drawCircle(
                    color = Color.White,
                    radius = 2.dp.toPx(),
                    center = point
                )
            }
        }
    }
}

// Data class untuk Tren Anggaran
data class TrenAnggaranData(
    val category: String,
    val values: List<Float>,
    val color: Color
)

// Sample data sesuai dengan gambar
private fun getTrenAnggaranData(): List<TrenAnggaranData> {
    return listOf(
        TrenAnggaranData(
            category = "Pendapatan",
            values = listOf(20f, 23f, 16f, 12f, 45f),
            color = Color(0xFF4CAF50) // Green
        ),
        TrenAnggaranData(
            category = "Belanja",
            values = listOf(32f, 38f, 47f, 18f, 15f),
            color = Color(0xFFF44336) // Red
        ),
        TrenAnggaranData(
            category = "Pembiayaan",
            values = listOf(38f, 47f, 25f, 45f, 32f),
            color = Color(0xFFFF9800) // Orange
        )
    )
}
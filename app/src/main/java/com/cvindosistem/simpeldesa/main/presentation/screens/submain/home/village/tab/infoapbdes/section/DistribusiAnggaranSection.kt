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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.APBDesItem

@Composable
internal fun DistribusiAnggaranSection(
    apbDesData: List<APBDesItem>,
    modifier: Modifier = Modifier
) {
    AppCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            BodyLargeText(
                text = "Distribusi Anggaran",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val totalAmount = apbDesData.sumOf { it.numericAmount }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Donut Chart
                DonutChartComponent(
                    data = apbDesData,
                    totalAmount = totalAmount,
                    modifier = Modifier.size(160.dp)
                )

                // Legend
                ChartLegend(
                    data = apbDesData,
                    totalAmount = totalAmount,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DonutChartComponent(
    data: List<APBDesItem>,
    totalAmount: Long,
    modifier: Modifier = Modifier,
    outerRadius: Dp = 80.dp,
    innerRadius: Dp = 35.dp,
    borderWidth: Dp = 2.dp
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        var currentStartAngle = -90f

        data.forEach { item ->
            val sweepAngle = (item.numericAmount.toFloat() / totalAmount) * 360f

            // Draw main thick segment
            drawArc(
                color = item.color.copy(alpha = 0.2f),
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(
                    center.x - outerRadius.toPx(),
                    center.y - outerRadius.toPx()
                ),
                size = Size(outerRadius.toPx() * 2, outerRadius.toPx() * 2)
            )

            currentStartAngle += sweepAngle
        }

        // Draw center hole
        drawCircle(
            color = Color.White,
            radius = innerRadius.toPx(),
            center = center
        )

        // Reset angle for borders
        currentStartAngle = -90f
        data.forEach { item ->
            val sweepAngle = (item.numericAmount.toFloat() / totalAmount) * 360f

            // Outer border - darker color
            drawArc(
                color = item.color,
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = borderWidth.toPx()),
                topLeft = Offset(
                    center.x - outerRadius.toPx(),
                    center.y - outerRadius.toPx()
                ),
                size = Size(outerRadius.toPx() * 2, outerRadius.toPx() * 2)
            )

            // Inner border - darker color
            drawArc(
                color = item.color,
                startAngle = currentStartAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = borderWidth.toPx()),
                topLeft = Offset(
                    center.x - innerRadius.toPx(),
                    center.y - innerRadius.toPx()
                ),
                size = Size(innerRadius.toPx() * 2, innerRadius.toPx() * 2)
            )

            currentStartAngle += sweepAngle
        }
    }
}

@Composable
private fun ChartLegend(
    data: List<APBDesItem>,
    totalAmount: Long,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        data.forEach { item ->
            val percentage = ((item.numericAmount.toFloat() / totalAmount) * 100).toInt()
            ChartLegendItem(
                color = item.color,
                title = item.title,
                percentage = percentage
            )
        }
    }
}

@Composable
private fun ChartLegendItem(
    color: Color,
    title: String,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            BodySmallText(text = title)
            BodySmallText(text = "($percentage%)")
        }
    }
}
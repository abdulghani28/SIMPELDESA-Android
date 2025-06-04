package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.APBDesItem
import kotlin.collections.forEach

@Composable
internal fun DistribusiPendapatanSection(
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
                text = "Distribusi Pendapatan",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            BarChartComponent(
                data = apbDesData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )
        }
    }
}

@Composable
fun BarChartComponent(
    data: List<APBDesItem>,
    modifier: Modifier = Modifier,
    barColor: Color = Color(0xFF9C7CE8)
) {
    val yAxisMax = 20

    Column(modifier = modifier) {
        // Chart area with proper layout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp, end = 16.dp, top = 16.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val leftPadding = 32.dp.toPx()
                val rightPadding = 16.dp.toPx()
                val topPadding = 16.dp.toPx()
                val bottomPadding = 16.dp.toPx()

                val chartWidth = canvasWidth - leftPadding - rightPadding
                val chartHeight = canvasHeight - topPadding - bottomPadding

                // Draw horizontal grid lines and Y-axis labels
                for (i in 0..4) {
                    val y = topPadding + (i * chartHeight / 4)
                    val value = yAxisMax - (i * yAxisMax / 4)

                    // Horizontal grid line
                    drawLine(
                        color = Color(0xFF9E9E9E).copy(alpha = 0.3f),
                        start = Offset(leftPadding, y),
                        end = Offset(canvasWidth - rightPadding, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    // Y-axis label
                    drawContext.canvas.nativeCanvas.drawText(
                        value.toString(),
                        leftPadding - 8.dp.toPx(),
                        y + 4.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = Color(0xFF757575).toArgb()
                            textSize = 12.sp.toPx()
                            textAlign = android.graphics.Paint.Align.RIGHT
                            isAntiAlias = true
                        }
                    )
                }

                // Draw vertical grid lines
                for (i in 0..data.size) {
                    val x = leftPadding + (i * chartWidth / data.size)
                    drawLine(
                        color = Color(0xFF9E9E9E).copy(alpha = 0.15f),
                        start = Offset(x, topPadding),
                        end = Offset(x, canvasHeight - bottomPadding),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw bars
                val barWidth = (chartWidth / data.size) * 0.5f
                data.forEachIndexed { index, item ->
                    val barHeight = (item.barChartValue / yAxisMax) * chartHeight
                    val barLeft = leftPadding + (index * chartWidth / data.size) +
                            (chartWidth / data.size - barWidth) / 2
                    val barTop = canvasHeight - bottomPadding - barHeight

                    // Bar fill
                    drawRoundRect(
                        color = barColor.copy(alpha = 0.4f),
                        topLeft = Offset(barLeft, barTop),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(2.dp.toPx())
                    )

                    // Bar border
                    drawRoundRect(
                        color = barColor,
                        topLeft = Offset(barLeft, barTop),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(2.dp.toPx()),
                        style = Stroke(width = 1.5.dp.toPx())
                    )
                }
            }
        }

        // X-axis labels with proper spacing
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.forEach { item ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = when (item.title) {
                            "Pendapatan" -> "PAD"
                            "Belanja" -> "Transfer"
                            "Pembiayaan" -> "Pendapatan"
                            else -> item.title
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF757575),
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if (item.title == "Pendapatan") {
                        Text(
                            text = "(Pendapatan",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Asli Desa)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp
                        )
                    } else if (item.title == "Pembiayaan") {
                        Text(
                            text = "Lainnya",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF757575),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
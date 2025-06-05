package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AlokasiAnggaranRadarChart(
    modifier: Modifier = Modifier,
    chartSize: Dp = 280.dp
) {
    val radarData = getRadarChartData()

    AppCard {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            BodyLargeText(
                text = "Alokasi Anggaran Bidang Belanja",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImprovedRadarChart(
                    data = radarData,
                    modifier = Modifier.size(chartSize)
                )
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ImprovedRadarChart(
    data: List<RadarChartItem>,
    modifier: Modifier = Modifier,
    maxValue: Float = 100f,
    gridLevels: Int = 5,
    gridColor: Color = Color.Gray.copy(alpha = 0.3f),
    dataColor: Color = Color(0xFF8B5CF6),
    dataAlpha: Float = 0.3f,
    strokeWidth: Dp = 2.dp
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val chartSize = minOf(maxWidth, maxHeight)
        val labelPadding = 80.dp
        val chartRadius = (chartSize - labelPadding) / 2

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Canvas for the chart
            Canvas(
                modifier = Modifier.size(chartSize)
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = chartRadius.toPx()
                val angleStep = 360f / data.size

                // Draw grid circles
                for (level in 1..gridLevels) {
                    val levelRadius = radius * (level.toFloat() / gridLevels)
                    drawCircle(
                        color = gridColor,
                        radius = levelRadius,
                        center = center,
                        style = Stroke(width = 1.dp.toPx())
                    )
                }

                // Draw grid lines and calculate data points
                val dataPoints = mutableListOf<Offset>()

                data.forEachIndexed { index, item ->
                    // Start from top (12 o'clock position)
                    val angle = Math.toRadians((angleStep * index - 90).toDouble())
                    val lineEndX = center.x + radius * cos(angle).toFloat()
                    val lineEndY = center.y + radius * sin(angle).toFloat()

                    // Draw grid line
                    drawLine(
                        color = gridColor,
                        start = center,
                        end = Offset(lineEndX, lineEndY),
                        strokeWidth = 1.dp.toPx()
                    )

                    // Calculate data point position
                    val dataRadius = radius * (item.value / maxValue)
                    val dataX = center.x + dataRadius * cos(angle).toFloat()
                    val dataY = center.y + dataRadius * sin(angle).toFloat()
                    dataPoints.add(Offset(dataX, dataY))
                }

                // Draw filled area
                if (dataPoints.isNotEmpty()) {
                    val path = Path()
                    path.moveTo(dataPoints[0].x, dataPoints[0].y)

                    for (i in 1 until dataPoints.size) {
                        path.lineTo(dataPoints[i].x, dataPoints[i].y)
                    }
                    path.close()

                    // Fill the area
                    drawPath(
                        path = path,
                        color = dataColor.copy(alpha = dataAlpha)
                    )

                    // Draw the outline
                    drawPath(
                        path = path,
                        color = dataColor,
                        style = Stroke(width = strokeWidth.toPx())
                    )
                }

                // Draw data points
                dataPoints.forEach { point ->
                    drawCircle(
                        color = dataColor,
                        radius = 4.dp.toPx(),
                        center = point
                    )
                }
            }

            // Improved Labels positioning
            data.forEachIndexed { index, item ->
                val angleStep = 360f / data.size
                val angle = Math.toRadians((angleStep * index - 90).toDouble())
                val labelDistance = chartRadius + 40.dp

                val offsetX = (labelDistance.value * cos(angle)).dp
                val offsetY = (labelDistance.value * sin(angle)).dp

                SmartRadarChartLabel(
                    text = item.label,
                    angle = angleStep * index - 90,
                    modifier = Modifier
                        .offset(
                            x = offsetX,
                            y = offsetY
                        )
                )
            }
        }
    }
}

@Composable
private fun SmartRadarChartLabel(
    text: String,
    angle: Float,
    modifier: Modifier = Modifier
) {
    // Determine text alignment based on position
    val normalizedAngle = ((angle % 360) + 360) % 360

    val textAlign = when {
        normalizedAngle < 45 || normalizedAngle > 315 -> TextAlign.Start // Right side
        normalizedAngle > 135 && normalizedAngle < 225 -> TextAlign.End // Left side
        else -> TextAlign.Center // Top and bottom
    }

    val maxWidth = when {
        normalizedAngle > 45 && normalizedAngle < 135 -> 80.dp // Top
        normalizedAngle > 225 && normalizedAngle < 315 -> 80.dp // Bottom
        else -> 100.dp // Left and right sides
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            textAlign = textAlign,
            lineHeight = 12.sp
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .width(maxWidth)
            .wrapContentHeight(),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

data class RadarChartItem(
    val label: String,
    val value: Float
)

private fun getRadarChartData(): List<RadarChartItem> {
    return listOf(
        RadarChartItem("Infrastruktur", 85f),
        RadarChartItem("Pendidikan", 75f),
        RadarChartItem("Kesehatan", 80f),
        RadarChartItem("Sosial", 65f),
        RadarChartItem("Ekonomi Desa", 70f),
        RadarChartItem("Penyelenggaraan Pemerintah Desa", 60f),
        RadarChartItem("Perencanaan & Pengembangan", 55f),
        RadarChartItem("Pemeliharaan Fasilitas Umum", 90f),
        RadarChartItem("Kebudayaan & Pariwisata", 45f),
        RadarChartItem("Lingkungan", 72f)
    )
}
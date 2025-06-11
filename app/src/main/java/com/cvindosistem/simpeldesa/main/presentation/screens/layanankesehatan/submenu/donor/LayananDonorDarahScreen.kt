package com.cvindosistem.simpeldesa.main.presentation.screens.layanankesehatan.submenu.donor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppDivider
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText

@Composable
fun LayananDonorDarahScreen(
    navController: NavController
) {
    val bloodStockData = listOf(
        BloodStockItem(
            bloodType = "O",
            percentage = 30,
            color = Color(0xFF6BADF7) // Blue
        ),
        BloodStockItem(
            bloodType = "A",
            percentage = 20,
            color = Color(0xFFFFC672) // Orange
        ),
        BloodStockItem(
            bloodType = "B",
            percentage = 40,
            color = Color(0xFF98E4D6) // Light Green
        ),
        BloodStockItem(
            bloodType = "AB",
            percentage = 10,
            color = Color(0xFFFF9B9B) // Light Red
        )
    )

    val upcomingDonor = DonorSchedule(
        type = "Pendonor",
        date = "08/12/2024",
        scheduledDate = "Kamis, 04 Desember 2025",
        scheduledTime = "09:00 - 16:00 WIB",
        location = "Posyandu Melati",
        address = "Jl. Kangkung, Dusun II",
        status = "Dijadwalkan"
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Layanan Donor Darah",
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                showNavigation = true,
                onNavigateClick = {}
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Blood Stock Chart Section
            item {
                BloodStockSection(bloodStockData = bloodStockData)
            }

            // Registration Buttons Section
            item {
                RegistrationButtonsSection()
            }

            // Upcoming Schedule Section
            item {
                UpcomingScheduleSection(schedule = upcomingDonor)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun BloodStockSection(
    bloodStockData: List<BloodStockItem>
) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Stok Darah di Desa",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AppDivider()

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart
                Box(
                    modifier = Modifier.size(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BloodStockDonutChart(
                        data = bloodStockData,
                        modifier = Modifier.size(150.dp)
                    )
                }

                // Legend
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    bloodStockData.forEach { item ->
                        BloodTypeLegendItem(
                            bloodType = item.bloodType,
                            percentage = item.percentage,
                            color = item.color
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BloodStockDonutChart(
    data: List<BloodStockItem>,
    modifier: Modifier = Modifier,
    outerRadius: Dp = 70.dp,
    innerRadius: Dp = 35.dp,
    borderWidth: Dp = 3.dp
) {
    val totalPercentage = data.sumOf { it.percentage }

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        var currentStartAngle = -90f

        data.forEach { item ->
            val sweepAngle = (item.percentage.toFloat() / totalPercentage) * 360f

            // Draw main thick segment
            drawArc(
                color = item.color.copy(alpha = 0.3f),
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
            val sweepAngle = (item.percentage.toFloat() / totalPercentage) * 360f

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
private fun BloodTypeLegendItem(
    bloodType: String,
    percentage: Int,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BodyMediumText("$bloodType ($percentage%)")
    }
}

@Composable
private fun RegistrationButtonsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Daftar Sebagai Pendonor Darah
        RegistrationCard(
            title = "Daftar Sebagai Pendonor Darah",
            icon = R.drawable.ic_give_blood,
            iconColor = Color.Unspecified,
            modifier = Modifier.weight(1f),
            onClick = { /* Handle donor registration */ }
        )

        // Daftar Sebagai Penerima Donor
        RegistrationCard(
            title = "Daftar Sebagai Penerima Donor",
            icon = R.drawable.ic_blood_bottle,
            iconColor = Color.Unspecified,
            modifier = Modifier.weight(1f),
            onClick = { /* Handle recipient registration */ }
        )
    }
}

@Composable
private fun RegistrationCard(
    title: String,
    icon: Int,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    AppCard(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = iconColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            BodyMediumText(title)
        }
    }
}

@Composable
private fun UpcomingScheduleSection(
    schedule: DonorSchedule
) {
    Column {
        TitleMediumText(
            "Jadwal Donor Selanjutnya",
            FontWeight.Medium
        )

        AppCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header with icon and status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    MaterialTheme.colorScheme.onSurface,
                                    CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = schedule.type,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFFFFF3CD),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = schedule.status,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color(0xFF856404)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                AppDivider()

                Spacer(modifier = Modifier.height(16.dp))

                // Schedule Date
                BodySmallText(schedule.date)

                Spacer(modifier = Modifier.height(12.dp))

                // Schedule Details
                ScheduleInfoRow(
                    label = "Jadwal Donor:",
                    value = schedule.scheduledDate
                )

                Spacer(modifier = Modifier.height(8.dp))

                ScheduleInfoRow(
                    label = "Tempat/Lokasi:",
                    value = schedule.location
                )

                Spacer(modifier = Modifier.height(8.dp))

                BodyMediumText(
                    text = schedule.address,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ScheduleInfoRow(
    label: String,
    value: String
) {
    Column {
        BodySmallText(label)
        BodyMediumText(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}

// Data Classes
data class BloodStockItem(
    val bloodType: String,
    val percentage: Int,
    val color: Color
)

data class DonorSchedule(
    val type: String,
    val date: String,
    val scheduledDate: String,
    val scheduledTime: String,
    val location: String,
    val address: String,
    val status: String
)
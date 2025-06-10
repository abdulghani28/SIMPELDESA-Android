package com.cvindosistem.simpeldesa.main.presentation.screens.layanankesehatan

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
fun LayananKesehatanScreen(
    navController: NavController
) {
    val healthServices = listOf(
        HealthService(
            id = 1,
            title = "Pemeriksaan WUS/PUS",
            icon = R.drawable.ic_wus,
            iconColor = Color.Unspecified,
            route = Screen.PemeriksaanWusPus.route
        ),
        HealthService(
            id = 2,
            title = "Pemeriksaan Ibu",
            icon = R.drawable.ic_ibu,
            iconColor = Color.Unspecified,
            route = Screen.PemeriksaanIbu.route
        ),
        HealthService(
            id = 3,
            title = "Pemeriksaan Balita",
            icon = R.drawable.ic_balita,
            iconColor = Color.Unspecified,
            route = Screen.PemeriksaanBalita.route
        ),
        HealthService(
            id = 4,
            title = "Layanan Donor Darah",
            icon = R.drawable.ic_donor,
            iconColor = Color.Unspecified,
            route = "Screen.PemeriksaanWusPus.route"
        )
    )

    val recentCheckup = CheckupRecord(
        id = 1,
        type = "Pemeriksaan Ibu",
        date = "08/12/2024",
        patientType = "Ibu Hamil",
        patientName = "Siti Nurbaya",
        pregnancyAge = "1 Bulan",
        motherCondition = "Sehat",
        fetalCondition = "Sehat",
        nextCheckupDate = ""
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Layanan Kesehatan",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
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
            // Health Services Grid
            items(healthServices.chunked(2)) { serviceRow ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    serviceRow.forEach { service ->
                        HealthServiceCard(
                            service = service,
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate(service.route) }
                        )
                    }
                    // Add empty space if odd number of items
                    if (serviceRow.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Recent Checkup Section
            item {
                Text(
                    text = "Riwayat Pemeriksaan Terakhir",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                CheckupRecordCard(
                    record = recentCheckup,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun HealthServiceCard(
    service: HealthService,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    AppCard(
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(service.icon),
                    contentDescription = service.title,
                    modifier = Modifier.size(48.dp),
                    tint = service.iconColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            BodyLargeText(service.title, FontWeight.Medium)
        }
    }
}

@Composable
private fun CheckupRecordCard(
    record: CheckupRecord,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with icon and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = record.type,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = record.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Patient Information
            CheckupInfoRow(
                label = "Jenis Pemeriksaan:",
                value = record.patientType
            )

            Spacer(modifier = Modifier.height(8.dp))

            CheckupInfoRow(
                label = "Nama Ibu:",
                value = record.patientName
            )

            Spacer(modifier = Modifier.height(8.dp))

            CheckupInfoRow(
                label = "Usia Kehamilan:",
                value = record.pregnancyAge
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Health Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Kondisi Ibu:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = record.motherCondition,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Kondisi Janin:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = record.fetalCondition,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CheckupInfoRow(
                label = "Tanggal Perkiraan Persalinan:",
                value = "30 Februari 2145"
            )
        }
    }
}

@Composable
private fun CheckupInfoRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

data class HealthService(
    val id: Int,
    val title: String,
    val icon: Int,
    val iconColor: Color,
    val route: String
)

data class CheckupRecord(
    val id: Int,
    val type: String,
    val date: String,
    val patientType: String,
    val patientName: String,
    val pregnancyAge: String,
    val motherCondition: String,
    val fetalCondition: String,
    val nextCheckupDate: String
)
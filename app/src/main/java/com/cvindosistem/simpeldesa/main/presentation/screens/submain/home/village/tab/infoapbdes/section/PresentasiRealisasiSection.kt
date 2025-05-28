package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.APBDesItem

@Composable
internal fun PresentasiRealisasiSection(
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
                text = "Presentasi Realisasi",
                fontWeight = FontWeight.SemiBold
            )


            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            apbDesData.forEachIndexed { index, item ->
                ProgressBarComponent(
                    title = item.title,
                    percentage = item.realisasiPercentage,
                    color = item.color
                )

                if (index < apbDesData.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun ProgressBarComponent(
    title: String,
    percentage: Int,
    color: Color,
    modifier: Modifier = Modifier,
    height: Dp = 32.dp, // Lebih tebal
    borderWidth: Dp = 2.dp,
    showLabels: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyMediumText(
                text = title,
                fontWeight = FontWeight.Medium
            )
            BodyMediumText(
                text = "$percentage%",
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Background container dengan border - bentuk kotak
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .border(
                    width = borderWidth,
                    color = color
                )
        ) {
            // Progress bar dengan warna fill - bentuk kotak
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage / 100f)
                    .fillMaxHeight()
                    .padding(borderWidth) // Padding agar tidak menutupi border
                    .background(color = color.copy(alpha = 0.2f))
            )
        }

        if (showLabels) {
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BodyMediumText(text = "Realisasi")
                BodyMediumText(text = "Anggaran")
            }
        }
    }
}
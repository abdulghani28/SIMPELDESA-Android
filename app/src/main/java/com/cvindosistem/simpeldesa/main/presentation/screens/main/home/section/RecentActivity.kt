package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.LabelSmallText
import com.cvindosistem.simpeldesa.core.components.NavigationSectionTitle

@Composable
internal fun RecentActivitySection(
    modifier: Modifier = Modifier,
    status: String,
    date: String,
    title: String,
    description: String,
    onSeeAllClick: () -> Unit
) {
    Column(modifier = modifier) {
        NavigationSectionTitle(
            titleSection = "Aktivitas Terakhir 🕓",
            onSeeAllClick = onSeeAllClick
        )

        AppCard(modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD6E4FF), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(status, fontSize = 12.sp, color = Color(0xFF3366FF))
                    }

                    LabelSmallText(date)
                }

                Spacer(modifier = Modifier.height(8.dp))

                BodyMediumText(
                    title,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                BodySmallText(description)
            }
        }
    }
}
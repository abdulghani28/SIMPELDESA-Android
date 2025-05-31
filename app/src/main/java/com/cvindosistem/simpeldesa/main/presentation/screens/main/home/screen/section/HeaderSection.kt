package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.LargeText
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel

@Composable
internal fun HeaderSection(
    homeViewModel: HomeViewModel,
    onNotifikasiClick: () -> Unit = {},
    onDesaClick: () -> Unit = {}
) {
    val uiState = homeViewModel.uiState.collectAsState()
    val villageInfo = homeViewModel.getVillageInfo()

    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyLargeText(uiState.value.greeting)

//            NotificationIcon(onNotifikasiClick)
        }

        LargeText(homeViewModel.getDisplayName())

        Spacer(modifier = Modifier.height(16.dp))

        DesaInformationCard(
            onDesaClick = { },
            dusun = villageInfo.dusun,
            rt = villageInfo.rt,
            rw = villageInfo.rw
        )
    }
}

@Composable
private fun DesaInformationCard(
    onDesaClick: () -> Unit,
    dusun: String,
    rt: String,
    rw: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onDesaClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(4.dp)
            ) {
                BodyLargeText(dusun, FontWeight.Bold)

                BodyMediumText(rt)

                BodyMediumText(rw)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Detail",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NotificationIcon(onNotifikasiClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(40.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        IconButton(
            onClick = onNotifikasiClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification), // pakai drawable custom
                contentDescription = "Notifikasi",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

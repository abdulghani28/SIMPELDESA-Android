package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText

@Composable
internal fun ProfilDesaTab(
    modifier: Modifier = Modifier
) {
    val villageProfile = getVillageProfile()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))

            VillageHeader(
                villageName = villageProfile.name,
                award = villageProfile.award
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        items(villageProfile.details) { detail ->
            VillageDetailItem(detail = detail)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VillageHeader(
    villageName: String,
    award: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Award badge/logo placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Award",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(
                    text = award,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TitleMediumText(
            text = villageName,
        )
    }
}

@Composable
private fun VillageDetailItem(
    detail: VillageDetail
) {
    Column {
        BodySmallText(
            text = detail.label
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (detail.isLink) {
            Text(
                text = detail.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    // Handle link click
                }
            )
        } else {
            BodyMediumText(
                text = detail.value,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Data classes for village information
data class VillageProfile(
    val name: String,
    val award: String,
    val details: List<VillageDetail>
)

data class VillageDetail(
    val label: String,
    val value: String,
    val isLink: Boolean = false
)

// Function to get village profile data
private fun getVillageProfile(): VillageProfile {
    return VillageProfile(
        name = "Desa Sukaramai Baru",
        award = "OUTSTANDING WINNER",
        details = listOf(
            VillageDetail(
                label = "Kecamatan",
                value = "Terong Belanda"
            ),
            VillageDetail(
                label = "Kabupaten",
                value = "Kebun Subur"
            ),
            VillageDetail(
                label = "Nama Jalan dan Nomor Blok",
                value = "Jl. Kangkung Lemes No. 69D"
            ),
            VillageDetail(
                label = "Kode Wilayah",
                value = "3201001002"
            ),
            VillageDetail(
                label = "Kode POS",
                value = "20217"
            ),
            VillageDetail(
                label = "Nomor Telepon",
                value = "021 - 41538971241"
            ),
            VillageDetail(
                label = "Alamat Email",
                value = "example@email.com"
            ),
            VillageDetail(
                label = "Alamat URL Website",
                value = "www.desasukaramai.co.id",
                isLink = true
            )
        )
    )
}
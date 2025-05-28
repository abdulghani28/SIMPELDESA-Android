package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText

@Composable
internal fun PegawaiDesaContent(
    modifier: Modifier = Modifier
) {
    val pegawaiList = getPegawaiDesa()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(horizontal = 24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        itemsIndexed(pegawaiList) { index, pegawai ->
            PegawaiItem(pegawai = pegawai)

            if (index < pegawaiList.size - 1) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun getPegawaiDesa(): List<PegawaiDesa> {
    return listOf(
        PegawaiDesa(
            id = "1",
            name = "Realy Samosir",
            position = "Kepala Desa",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "2",
            name = "Ade Lisan",
            position = "Sekretaris Desa",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "3",
            name = "Budiono Siregar",
            position = "Bendahara Desa",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "4",
            name = "Joko Widodo",
            position = "Kepala Dusun I",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "5",
            name = "Prabowo Subianto",
            position = "Kepala Dusun II",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "6",
            name = "Wahyu Kurniawan",
            position = "Kepala Dusun II",
            phoneNumber = "081234567890"
        ),
        PegawaiDesa(
            id = "7",
            name = "Deno Siregar",
            position = "Kasi Pelayanan",
            phoneNumber = "081234567890"
        )
    )
}

@Composable
private fun PegawaiItem(
    pegawai: PegawaiDesa,
    modifier: Modifier = Modifier
) {
    AppCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                BodyMediumText(
                    text = pegawai.name,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                BodySmallText(
                    text = pegawai.position,
                )

                Spacer(modifier = Modifier.height(4.dp))

                BodySmallText(
                    text = pegawai.phoneNumber,
                )
            }
        }
    }
}

data class PegawaiDesa(
    val id: String,
    val name: String,
    val position: String,
    val phoneNumber: String
)

package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppSearchBar
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.FilterButton
import com.cvindosistem.simpeldesa.core.components.TitleMediumText

@Composable
internal fun SuratSayaContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppSearchBar(
                value = "",
                onValueSearch = { },
                placeholder = {
                    Text(
                        text = "Cari Keyword",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                },
                modifier = Modifier.weight(1f)
            )

            FilterButton(
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SuratListSection()
    }
}

@Composable
private fun SuratListSection() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getSuratSayaList()) { suratType ->
            SuratSayaCard(surat = suratType)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SuratSayaCard(
    surat: SuratSaya
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                StatusChip(
                    status = surat.status,
                    modifier = Modifier
                )

                BodySmallText(
                    text = surat.tanggal
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(
                text = surat.judul
            )

            Spacer(modifier = Modifier.height(6.dp))

            BodySmallText(
                text = surat.deskripsi
            )
        }
    }
}

@Composable
private fun StatusChip(
    status: StatusSurat,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        StatusSurat.MENUNGGU -> Color(0xFFE3F2FD)
        StatusSurat.DIPROSES -> Color(0xFFFFF3E0)
        StatusSurat.SELESAI -> Color(0xFFE8F5E8)
        StatusSurat.DITOLAK -> Color(0xFFFFEBEE)
    }

    val textColor = when (status) {
        StatusSurat.MENUNGGU -> Color(0xFF1976D2)
        StatusSurat.DIPROSES -> Color(0xFFFF8F00)
        StatusSurat.SELESAI -> Color(0xFF388E3C)
        StatusSurat.DITOLAK -> Color(0xFFD32F2F)
    }

    val statusText = when (status) {
        StatusSurat.MENUNGGU -> "Menunggu"
        StatusSurat.DIPROSES -> "Diproses"
        StatusSurat.SELESAI -> "Selesai"
        StatusSurat.DITOLAK -> "Ditolak"
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

// Data class untuk surat saya
data class SuratSaya(
    val judul: String,
    val deskripsi: String,
    val tanggal: String,
    val status: StatusSurat
)

// Enum untuk status surat
enum class StatusSurat {
    MENUNGGU,
    DIPROSES,
    SELESAI,
    DITOLAK
}

// Function untuk mendapatkan data surat saya
private fun getSuratSayaList(): List<SuratSaya> {
    return listOf(
        SuratSaya(
            judul = "Surat Keterangan Status Perkawinan",
            deskripsi = "Surat ini berhasil diajukan ke pihak desa dan akan segera diproses.",
            tanggal = "25 September 2024",
            status = StatusSurat.MENUNGGU
        ),
        SuratSaya(
            judul = "Surat Pengantar Pernikahan",
            deskripsi = "Surat yang Anda ajukan saat ini sedang dalam proses oleh pihak desa.",
            tanggal = "25 September 2024",
            status = StatusSurat.DIPROSES
        ),
        SuratSaya(
            judul = "Surat Kuasa",
            deskripsi = "Surat yang Anda ajukan sudah selesai diproses dan siap untuk diambil di kantor desa.",
            tanggal = "25 September 2024",
            status = StatusSurat.SELESAI
        ),
        SuratSaya(
            judul = "Surat Keterangan Domisili",
            deskripsi = "Mohon maaf, surat yang Anda ajukan telah ditolak oleh pihak desa.",
            tanggal = "25 September 2024",
            status = StatusSurat.DITOLAK
        )
    )
}
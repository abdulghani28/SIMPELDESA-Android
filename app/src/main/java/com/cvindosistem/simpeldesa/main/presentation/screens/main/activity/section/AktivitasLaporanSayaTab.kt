package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppContainer
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText

@Composable
fun AktivitasLaporanSayaTab(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val laporanList = remember {
        listOf(
            LaporanItem(
                id = 1,
                judul = "Laporan Keterangan Status Perkawinan",
                deskripsi = "Laporan ini berhasil diajukan ke pihak desa dan akan segera diproses. 📧",
                status = LaporanStatus.MENUNGGU,
                tanggal = "25 September 2024"
            ),
            LaporanItem(
                id = 2,
                judul = "Laporan Pengantar Pernikahan",
                deskripsi = "Laporan yang Anda ajukan saat ini sedang dalam proses oleh pihak desa. 📧",
                status = LaporanStatus.DIPROSES,
                tanggal = "25 September 2024"
            ),
            LaporanItem(
                id = 3,
                judul = "Laporan Kuasa",
                deskripsi = "Laporan yang Anda ajukan sudah selesai diproses dan siap untuk diambil di kantor desa. ✅",
                status = LaporanStatus.SELESAI,
                tanggal = "25 September 2024"
            ),
            LaporanItem(
                id = 4,
                judul = "Laporan Keterangan Domisili",
                deskripsi = "Mohon maaf, laporan yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = LaporanStatus.DITOLAK,
                tanggal = "25 September 2024"
            ),
            LaporanItem(
                id = 5,
                judul = "Laporan Keterangan Domisili",
                deskripsi = "Mohon maaf, laporan yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = LaporanStatus.DITOLAK,
                tanggal = "25 September 2024"
            ),
            LaporanItem(
                id = 6,
                judul = "Laporan Keterangan Domisili",
                deskripsi = "Mohon maaf, laporan yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = LaporanStatus.DITOLAK,
                tanggal = "25 September 2024"
            )
        )
    }

    val filteredLaporanList = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            laporanList
        } else {
            laporanList.filter {
                it.judul.contains(searchQuery, ignoreCase = true) ||
                        it.deskripsi.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    AppContainer(
        background = MaterialTheme.colorScheme.surfaceBright,
        modifier = modifier
    ) {
        AppSearchBarAndFilter(
            placeholder = "Cari Keyword",
            value = searchQuery,
            onValueSearch = { searchQuery = it },
            onFilterClick = { },
            showFilter = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredLaporanList) { laporan ->
                LaporanCard(
                    laporan = laporan,
                    onClick = {
                        // Handle card click
                    }
                )
            }
        }
    }
}

// Data Classes
private data class LaporanItem(
    val id: Int,
    val judul: String,
    val deskripsi: String,
    val status: LaporanStatus,
    val tanggal: String
)

private enum class LaporanStatus(
    val displayName: String,
    val backgroundColor: Color,
    val textColor: Color
) {
    MENUNGGU(
        displayName = "Menunggu",
        backgroundColor = Color(0xFFE3F2FD),
        textColor = Color(0xFF1976D2)
    ),
    DIPROSES(
        displayName = "Diproses",
        backgroundColor = Color(0xFFFFF3E0),
        textColor = Color(0xFFF57C00)
    ),
    SELESAI(
        displayName = "Selesai",
        backgroundColor = Color(0xFFE8F5E8),
        textColor = Color(0xFF4CAF50)
    ),
    DITOLAK(
        displayName = "Ditolak",
        backgroundColor = Color(0xFFFFEBEE),
        textColor = Color(0xFFE53935)
    )
}

// Laporan Card Component
@Composable
private fun LaporanCard(
    laporan: LaporanItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier
            .clickable {
                onClick
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status = laporan.status)

                BodySmallText(laporan.tanggal)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(laporan.judul)

            Spacer(modifier = Modifier.height(8.dp))

            BodyMediumText(laporan.deskripsi)
        }
    }
}

@Composable
private fun StatusChip(
    status: LaporanStatus,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = status.backgroundColor
    ) {
        Text(
            text = status.displayName,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = status.textColor
        )
    }
}

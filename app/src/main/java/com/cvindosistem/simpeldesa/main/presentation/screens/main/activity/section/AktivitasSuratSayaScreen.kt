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
fun AktivitasSuratSaya(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val suratList = remember {
        listOf(
            SuratItem(
                id = 1,
                judul = "Surat Keterangan Status Perkawinan",
                deskripsi = "Surat ini berhasil diajukan ke pihak desa dan akan segera diproses. 📧",
                status = SuratStatus.MENUNGGU,
                tanggal = "25 September 2024"
            ),
            SuratItem(
                id = 2,
                judul = "Surat Pengantar Pernikahan",
                deskripsi = "Surat yang Anda ajukan saat ini sedang dalam proses oleh pihak desa. 📧",
                status = SuratStatus.DIPROSES,
                tanggal = "25 September 2024"
            ),
            SuratItem(
                id = 3,
                judul = "Surat Kuasa",
                deskripsi = "Surat yang Anda ajukan sudah selesai diproses dan siap untuk diambil di kantor desa. ✅",
                status = SuratStatus.SELESAI,
                tanggal = "25 September 2024"
            ),
            SuratItem(
                id = 4,
                judul = "Surat Keterangan Domisili",
                deskripsi = "Mohon maaf, surat yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = SuratStatus.DITOLAK,
                tanggal = "25 September 2024"
            ),
            SuratItem(
                id = 5,
                judul = "Surat Keterangan Domisili",
                deskripsi = "Mohon maaf, surat yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = SuratStatus.DITOLAK,
                tanggal = "25 September 2024"
            ),
            SuratItem(
                id = 6,
                judul = "Surat Keterangan Domisili",
                deskripsi = "Mohon maaf, surat yang Anda ajukan telah ditolak oleh pihak desa. 🚫",
                status = SuratStatus.DITOLAK,
                tanggal = "25 September 2024"
            )
        )
    }

    val filteredSuratList = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            suratList
        } else {
            suratList.filter {
                it.judul.contains(searchQuery, ignoreCase = true) ||
                        it.deskripsi.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    AppContainer(
        background = MaterialTheme.colorScheme.background,
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
            items(filteredSuratList) { surat ->
                SuratCard(
                    surat = surat,
                    onClick = {
                        // Handle card click
                    }
                )
            }
        }
    }
}

// Data Classes
data class SuratItem(
    val id: Int,
    val judul: String,
    val deskripsi: String,
    val status: SuratStatus,
    val tanggal: String
)

enum class SuratStatus(
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

// Surat Card Component
@Composable
fun SuratCard(
    surat: SuratItem,
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
                StatusChip(status = surat.status)

                BodySmallText(surat.tanggal)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(surat.judul)

            Spacer(modifier = Modifier.height(8.dp))

            BodyMediumText(surat.deskripsi)
        }
    }
}

// Status Chip Component
@Composable
fun StatusChip(
    status: SuratStatus,
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

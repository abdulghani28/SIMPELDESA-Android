package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.CardTitleText
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
fun SuratKeteranganScreen(
    onNavigateBack: () -> Unit = {},
    navController: NavController,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
    ) {
        AppTopBar(
            title = "Surat Keterangan",
            showBackButton = true,
            onBackClick = onNavigateBack
        )

        SuratKeteranganContent(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SuratKeteranganContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getSuratKeteranganList()) { suratItem ->
            SuratKeteranganItemCard(
                suratItem = suratItem,
                onItemClick = {
                    navController.navigate(suratItem.rute)
                }
            )
        }
    }
}

@Composable
private fun SuratKeteranganItemCard(
    suratItem: SuratKeteranganItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier
            .clickable {
                onItemClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SuratKeteranganIcon(
                emoji = suratItem.emoji,
                backgroundColor = suratItem.iconBackgroundColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            SuratKeteranganInfo(
                title = suratItem.title,
                description = suratItem.description,
            )
        }
    }
}

@Composable
private fun SuratKeteranganIcon(
    emoji: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun SuratKeteranganInfo(
    title: String,
    description: String,
) {
    Column {
        CardTitleText(title)

        Spacer(modifier = Modifier.height(6.dp))

        BodySmallText(description)
    }
}

// Data class untuk item surat keterangan
data class SuratKeteranganItem(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val iconBackgroundColor: Color,
    val rute: String
)

// Function untuk mendapatkan data surat keterangan
private fun getSuratKeteranganList(): List<SuratKeteranganItem> {
    return listOf(
        SuratKeteranganItem(
            id = "domisili",
            title = "Surat Keterangan Domisili",
            description = "Menyatakan tempat tinggal seseorang untuk keperluan administrasi.",
            emoji = "🌏",
            iconBackgroundColor = Color(0xFFF0F4FF),
            rute = Screen.SKDomisiliScreen.route
        ),
        SuratKeteranganItem(
            id = "tidak_mampu",
            title = "Surat Keterangan Tidak Mampu",
            description = "Menyatakan kondisi ekonomi kurang mampu untuk bantuan atau keringanan biaya.",
            emoji = "⚖️",
            iconBackgroundColor = Color(0xFFFFF4E6),
            rute = Screen.SKTidakMampu.route
        ),
        SuratKeteranganItem(
            id = "kelahiran",
            title = "Surat Keterangan Kelahiran",
            description = "Mengonfirmasi kelahiran anak untuk pengurusan akta kelahiran.",
            emoji = "👶",
            iconBackgroundColor = Color(0xFFF0FFF4),
            rute = Screen.SKKelahiran.route
        ),
        SuratKeteranganItem(
            id = "kematian",
            title = "Surat Keterangan Kematian",
            description = "Menyatakan kematian seseorang untuk urusan administrasi.",
            emoji = "❌",
            iconBackgroundColor = Color(0xFFFFF0F0),
            rute = Screen.SKKematian.route
        ),
        SuratKeteranganItem(
            id = "usaha",
            title = "Surat Keterangan Usaha",
            description = "Menyatakan kegiatan usaha yang dijalankan untuk keperluan perizinan.",
            emoji = "🏢",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKUsaha.route
        ),
        SuratKeteranganItem(
            id = "berpergian",
            title = "Surat Keterangan Bepergian",
            description = "Memberi izin bepergian keluar daerah dalam situasi tertentu.",
            emoji = "✈\uFE0F",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBerpergian.route
        ),
        SuratKeteranganItem(
            id = "tidak_masuk_kerja",
            title = "Surat Keterangan Izin Tidak Masuk Kerja",
            description = "Bukti izin tidak masuk kerja karena alasan tertentu.",
            emoji = "\uD83D\uDCBC",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKTidakMasukKerja.route
        ),
        SuratKeteranganItem(
            id = "penghasilan",
            title = "Surat Keterangan Penghasilan",
            description = "Menyatakan jumlah penghasilan untuk pengajuan pinjaman atau bantuan.",
            emoji = "\uD83D\uDCB5",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKPenghasilan.route
        ),
        SuratKeteranganItem(
            id = "perkawinan",
            title = "Surat Keterangan Status Perkawinan",
            description = "Menyatakan status perkawinan untuk keperluan administratif.",
            emoji = "\uD83D\uDC8D",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKStatusPerkawinan.route
        ),
        SuratKeteranganItem(
            id = "ktpsementara",
            title = "Surat Keterangan Resi KTP Sementara",
            description = "Pengganti sementara KTP yang belum selesai dicetak.",
            emoji = "\uD83E\uDEAA",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKResiKTPSementara.route
        ),
        SuratKeteranganItem(
            id = "domisili_perusahaan",
            title = "Surat Keterangan Domisili Perusahaan",
            description = "Menyatakan domisili perusahaan untuk perizinan.",
            emoji = "\uD83D\uDCCC",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKDomisiliPerusahaan.route
        ),
        SuratKeteranganItem(
            id = "jandaduda",
            title = "Surat Keterangan Janda/Duda",
            description = "Menyatakan status janda/duda untuk administrasi.",
            emoji = "\uD83D\uDC94",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKJandaDuda.route
        ),
        SuratKeteranganItem(
            id = "beda_identitas",
            title = "Surat Keterangan Beda Identitas",
            description = "Mengklarifikasi perbedaan identitas di dokumen resmi.",
            emoji = "\uD83D\uDCC3",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBedaIdentitas.route
        ),
        SuratKeteranganItem(
            id = "ghaib",
            title = "Surat Keterangan Ghaib",
            description = "Menyatakan seseorang hilang untuk proses hukum.",
            emoji = "\uD83D\uDC64",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKGhaib.route
        ),
        SuratKeteranganItem(
            id = "belummemilikipbb",
            title = "Surat Keterangan Belum Memiliki PBB",
            description = "Menyatakan seseorang belum memiliki PBB.",
            emoji = "\uD83C\uDFE1",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBelumMemilikiPBB.route
        ),
        SuratKeteranganItem(
            id = "jualbeli",
            title = "Surat Keterangan Jual Beli",
            description = "Menyatakan dua belah pihak yang melakukan jual beli.",
            emoji = "\uD83E\uDD1D",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKJualBeli.route
        ),
        SuratKeteranganItem(
            id = "ktpdalamproses",
            title = "Surat Keterangan KTP Dalam Proses",
            description = "Menyatakan KTP seseorang yang masih dalam proses.",
            emoji = "\uD83E\uDD1D",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKKTPDalamProses.route
        )
    )
}
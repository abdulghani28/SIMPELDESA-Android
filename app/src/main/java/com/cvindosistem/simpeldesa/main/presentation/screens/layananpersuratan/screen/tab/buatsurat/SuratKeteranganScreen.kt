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
            SuratItemCard(
                suratItem = suratItem,
                onItemClick = {
                    navController.navigate(suratItem.rute)
                }
            )
        }
    }
}

@Composable
private fun SuratItemCard(
    suratItem: SuratItem,
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

// Function untuk mendapatkan data surat keterangan
private fun getSuratKeteranganList(): List<SuratItem> {
    return listOf(
        SuratItem(
            id = "domisili",
            title = "Surat Keterangan Domisili",
            description = "Menyatakan tempat tinggal seseorang untuk keperluan administrasi.",
            emoji = "🌏",
            iconBackgroundColor = Color(0xFFF0F4FF),
            rute = Screen.SKDomisiliScreen.route
        ),
        SuratItem(
            id = "tidak_mampu",
            title = "Surat Keterangan Tidak Mampu",
            description = "Menyatakan kondisi ekonomi kurang mampu untuk bantuan atau keringanan biaya.",
            emoji = "⚖️",
            iconBackgroundColor = Color(0xFFFFF4E6),
            rute = Screen.SKTidakMampu.route
        ),
        SuratItem(
            id = "kelahiran",
            title = "Surat Keterangan Kelahiran",
            description = "Mengonfirmasi kelahiran anak untuk pengurusan akta kelahiran.",
            emoji = "👶",
            iconBackgroundColor = Color(0xFFF0FFF4),
            rute = Screen.SKKelahiran.route
        ),
        SuratItem(
            id = "kematian",
            title = "Surat Keterangan Kematian",
            description = "Menyatakan kematian seseorang untuk urusan administrasi.",
            emoji = "❌",
            iconBackgroundColor = Color(0xFFFFF0F0),
            rute = Screen.SKKematian.route
        ),
        SuratItem(
            id = "usaha",
            title = "Surat Keterangan Usaha",
            description = "Menyatakan kegiatan usaha yang dijalankan untuk keperluan perizinan.",
            emoji = "🏢",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKUsaha.route
        ),
        SuratItem(
            id = "berpergian",
            title = "Surat Keterangan Bepergian",
            description = "Memberi izin bepergian keluar daerah dalam situasi tertentu.",
            emoji = "✈\uFE0F",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBerpergian.route
        ),
        SuratItem(
            id = "tidak_masuk_kerja",
            title = "Surat Keterangan Izin Tidak Masuk Kerja",
            description = "Bukti izin tidak masuk kerja karena alasan tertentu.",
            emoji = "\uD83D\uDCBC",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKTidakMasukKerja.route
        ),
        SuratItem(
            id = "penghasilan",
            title = "Surat Keterangan Penghasilan",
            description = "Menyatakan jumlah penghasilan untuk pengajuan pinjaman atau bantuan.",
            emoji = "\uD83D\uDCB5",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKPenghasilan.route
        ),
        SuratItem(
            id = "perkawinan",
            title = "Surat Keterangan Status Perkawinan",
            description = "Menyatakan status perkawinan untuk keperluan administratif.",
            emoji = "\uD83D\uDC8D",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKStatusPerkawinan.route
        ),
        SuratItem(
            id = "ktpsementara",
            title = "Surat Keterangan Resi KTP Sementara",
            description = "Pengganti sementara KTP yang belum selesai dicetak.",
            emoji = "\uD83E\uDEAA",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKResiKTPSementara.route
        ),
        SuratItem(
            id = "domisili_perusahaan",
            title = "Surat Keterangan Domisili Perusahaan",
            description = "Menyatakan domisili perusahaan untuk perizinan.",
            emoji = "\uD83D\uDCCC",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKDomisiliPerusahaan.route
        ),
        SuratItem(
            id = "jandaduda",
            title = "Surat Keterangan Janda/Duda",
            description = "Menyatakan status janda/duda untuk administrasi.",
            emoji = "\uD83D\uDC94",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKJandaDuda.route
        ),
        SuratItem(
            id = "beda_identitas",
            title = "Surat Keterangan Beda Identitas",
            description = "Mengklarifikasi perbedaan identitas di dokumen resmi.",
            emoji = "\uD83D\uDCC3",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBedaIdentitas.route
        ),
        SuratItem(
            id = "ghaib",
            title = "Surat Keterangan Ghaib",
            description = "Menyatakan seseorang hilang untuk proses hukum.",
            emoji = "\uD83D\uDC64",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKGhaib.route
        ),
        SuratItem(
            id = "belummemilikipbb",
            title = "Surat Keterangan Belum Memiliki PBB",
            description = "Menyatakan seseorang belum memiliki PBB.",
            emoji = "\uD83C\uDFE1",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKBelumMemilikiPBB.route
        ),
        SuratItem(
            id = "jualbeli",
            title = "Surat Keterangan Jual Beli",
            description = "Menyatakan dua belah pihak yang melakukan jual beli.",
            emoji = "\uD83E\uDD1D",
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKJualBeli.route
        ),
        SuratItem(
            id = "ktpdalamproses",
            title = "Surat Keterangan KTP Dalam Proses",
            description = "Menyatakan KTP seseorang yang masih dalam proses.",
            emoji = "\uD83C\uDD94", // 🆔
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKKTPDalamProses.route
        ),
        SuratItem(
            id = "jamkesos",
            title = "Surat Keterangan Jamkesos",
            description = "Menyatakan seseorang mengajukan jaminan kesehatan sosial.",
            emoji = "\u2695\uFE0F", // ⚕️
            iconBackgroundColor = Color(0xFFF8F0FF),
            rute = Screen.SKJamkesos.route
        ),
        SuratItem(
            id = "lahirmati",
            title = "Surat Lahir Mati",
            description = "Pernyataan anak yang dilahirkan dalam keadaan tidak bernyawa.",
            emoji = "\uD83D\uDC76", // 👶
            iconBackgroundColor = Color(0xFFFFF0F0),
            rute = Screen.SKLahirMati.route
        ),
        SuratItem(
            id = "pergikawin",
            title = "Surat Pergi Kawin",
            description = "Digunakan sebagai bukti pindah domisili untuk menikah.",
            emoji = "\uD83D\uDC8D", // 💍
            iconBackgroundColor = Color(0xFFEFFBF1),
            rute = Screen.SKPergiKawin.route
        ),
        SuratItem(
            id = "kepemilikankendaraan",
            title = "Surat Kepemilikan Kendaraan",
            description = "Menyatakan kepemilikan kendaraan secara sah dan tertulis.",
            emoji = "\uD83D\uDE97", // 🚗
            iconBackgroundColor = Color(0xFFE8F4FF),
            rute = Screen.SKKepemilikanKendaraan.route
        ),
        SuratItem(
            id = "walihakim",
            title = "Surat Wali Hakim",
            description = "Surat keterangan penunjukan wali bagi calon pengantin yang tidak memiliki wali nasab.",
            emoji = "\uD83D\uDC75", // 👵 atau 🧓
            iconBackgroundColor = Color(0xFFFFF4E5),
            rute = Screen.SKWaliHakim.route
        ),
        SuratItem(
            id = "pengantarcerairujuk",
            title = "Surat Pengantar Cerai Rujuk",
            description = "Digunakan sebagai pengantar proses cerai rujuk sesuai ketentuan hukum.",
            emoji = "\uD83D\uDD10", // 🔐 simbol pernikahan kembali
            iconBackgroundColor = Color(0xFFE6F7FF),
            rute = Screen.SKPengantarCeraiRujuk.route
        ),
        SuratItem(
            id = "izinorangtua",
            title = "Surat Izin Orang Tua",
            description = "Surat persetujuan orang tua untuk keperluan pernikahan atau kegiatan penting lainnya.",
            emoji = "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66", // 👨‍👩‍👦 keluarga
            iconBackgroundColor = Color(0xFFFFF8E1),
            rute = Screen.SKIzinOrangTua.route
        ),
        SuratItem(
            id = "kepemilikantanah",
            title = "Surat Kepemilikan Tanah",
            description = "Menyatakan status kepemilikan atas bidang tanah secara sah.",
            emoji = "\uD83C\uDF33", // 🌳 sebagai simbol tanah/perkebunan
            iconBackgroundColor = Color(0xFFF1F8E9),
            rute = Screen.SKKepemilikanTanah.route
        ),
        SuratItem(
            id = "biodatawarga",
            title = "Surat Keterangan Biodata Warga",
            description = "Menjelaskan identitas resmi warga sesuai data kependudukan.",
            emoji = "\uD83D\uDCCB", // 📋 dokumen data
            iconBackgroundColor = Color(0xFFF3E5F5),
            rute = Screen.SKBiodataWarga.route
        ),
        SuratItem(
            id = "nikahnonmuslim",
            title = "Surat Keterangan Nikah Non-Muslim",
            description = "Keterangan resmi untuk pernikahan warga non-Muslim.",
            emoji = "\uD83D\uDC91", // 💑 simbol pasangan
            iconBackgroundColor = Color(0xFFFFEBEE),
            rute = Screen.SKNikahNonMuslim.route
        )
    )
}
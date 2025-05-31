package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.components.TitleSmallText
import com.cvindosistem.simpeldesa.main.presentation.components.StatusChip
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.PenerimaDetail
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetail
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetailViewModel

@Composable
fun SuratDetailScreen(
    navController: NavController,
    suratId: String,
    viewModel: SuratDetailViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    // Load data when screen is first composed
    LaunchedEffect(suratId) {
        viewModel.loadSuratDetail(suratId)
    }

    // Handle events
    LaunchedEffect(Unit) {
        viewModel.suratDetailEvent.collect { event ->
            when (event) {
                is SuratDetailViewModel.SuratDetailEvent.DataLoaded -> {
                    // Handle data loaded if needed
                }
                is SuratDetailViewModel.SuratDetailEvent.Error -> {
                    // Handle error if needed
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detail Surat",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Terjadi kesalahan",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.clearError()
                                viewModel.loadSuratDetail(suratId)
                            }
                        ) {
                            Text("Coba Lagi")
                        }
                    }
                }
                uiState.value.suratDetail != null -> {
                    SuratDetailContent(
                        suratDetail = uiState.value.suratDetail!!,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun SuratDetailContent(
    suratDetail: SuratDetail,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Information
        item {
            SuratHeaderCard(suratDetail)
        }

        // Data Pemohon
        if (hasPersonalData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pemohon",
                    content = {
                        PersonalDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Keluarga
        if (hasFamilyData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Keluarga",
                    content = {
                        FamilyDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pasangan
        if (hasSpouseData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pasangan",
                    content = {
                        SpouseDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Anak
        if (hasChildData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Anak",
                    content = {
                        ChildDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Usaha
        if (hasBusinessData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Usaha",
                    content = {
                        BusinessDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Acara/Event
        if (hasEventData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Acara/Event",
                    content = {
                        EventDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pindah/Perjalanan
        if (hasMoveData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pindah/Perjalanan",
                    content = {
                        MoveDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Kematian
        if (hasDeathData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Kematian",
                    content = {
                        DeathDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Kehilangan
        if (hasLossData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Kehilangan",
                    content = {
                        LossDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Properti
        if (hasPropertyData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Properti",
                    content = {
                        PropertyDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Lainnya
        if (hasOtherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Lainnya",
                    content = {
                        OtherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Penerima
        if (suratDetail.penerima.isNotEmpty()) {
            item {
                SuratSectionCard(
                    title = "Data Penerima",
                    content = {
                        ReceiverDataSection(suratDetail.penerima)
                    }
                )
            }
        }
    }
}

@Composable
private fun SuratHeaderCard(suratDetail: SuratDetail) {
    AppCard {
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
                StatusChip(status = suratDetail.status)

                if (suratDetail.tanggalPengajuan != null) {
                    BodySmallText(text = suratDetail.tanggalPengajuan)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(text = suratDetail.jenisSurat)

            if (suratDetail.nomorSurat != null) {
                Spacer(modifier = Modifier.height(8.dp))
                BodyMediumText(text = "No. Surat: ${suratDetail.nomorSurat}")
            }

            if (suratDetail.nomorPengajuan != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodyMediumText(text = "No. Pengajuan: ${suratDetail.nomorPengajuan}")
            }

            if (suratDetail.tanggalSelesai != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Selesai: ${suratDetail.tanggalSelesai}")
            }

            if (suratDetail.diprosesOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Diproses oleh: ${suratDetail.diprosesOleh}")
            }

            if (suratDetail.disahkanOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Disahkan oleh: ${suratDetail.disahkanOleh}")
            }
        }
    }
}

@Composable
private fun SuratSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TitleMediumText(
                text = title,
            )
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun PersonalDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama", it)
        }
        suratDetail.nik?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK", it)
        }
        suratDetail.alamat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        suratDetail.tempatLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        suratDetail.tanggalLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", it)
        }
        suratDetail.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        suratDetail.agama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama", it)
        }
        suratDetail.statusKawin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin", it)
        }
        suratDetail.pekerjaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan", it)
        }
        suratDetail.kewarganegaraan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan", it)
        }
        suratDetail.kontak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kontak", it)
        }
    }
}

@Composable
private fun FamilyDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Data Ayah
        if (hasParentData(suratDetail)) {
            Spacer(Modifier.height(4.dp))
            TitleSmallText("Data Orang Tua")
            Spacer(Modifier.height(4.dp))

            suratDetail.namaAyah?.takeIf { it.isNotBlank() }?.let {
                DataRow("Nama Ayah", it)
            }
            suratDetail.nikAyah?.takeIf { it.isNotBlank() }?.let {
                DataRow("NIK Ayah", it)
            }
            suratDetail.tempatLahirAyah?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tempat Lahir Ayah", it)
            }
            suratDetail.tanggalLahirAyah?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tanggal Lahir Ayah", it)
            }
            suratDetail.pekerjaanOrtu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Pekerjaan Orang Tua", it)
            }
            suratDetail.penghasilanOrtu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Penghasilan Orang Tua", it)
            }
            suratDetail.tanggunganOrtu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tanggungan Orang Tua", it)
            }
            suratDetail.alamatOrtu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Alamat Orang Tua", it)
            }
        }

        // Data Ibu
        if (hasMotherData(suratDetail)) {
            if (hasParentData(suratDetail)) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            suratDetail.namaIbu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Nama Ibu", it)
            }
            suratDetail.nikIbu?.takeIf { it.isNotBlank() }?.let {
                DataRow("NIK Ibu", it)
            }
            suratDetail.tempatLahirIbu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tempat Lahir Ibu", it)
            }
            suratDetail.tanggalLahirIbu?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tanggal Lahir Ibu", it)
            }
        }
    }
}

@Composable
private fun SpouseDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Data Suami
        if (hasHusbandData(suratDetail)) {
            Spacer(Modifier.height(4.dp))
            TitleSmallText("Data Suami")
            Spacer(Modifier.height(4.dp))


            suratDetail.namaSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Nama Suami", it)
            }
            suratDetail.nikSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("NIK Suami", it)
            }
            suratDetail.alamatSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Alamat Suami", it)
            }
            suratDetail.tempatLahirSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tempat Lahir Suami", it)
            }
            suratDetail.tanggalLahirSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tanggal Lahir Suami", it)
            }
            suratDetail.pekerjaanSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Pekerjaan Suami", it)
            }
            suratDetail.agamaSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Agama Suami", it)
            }
            suratDetail.statusKawinSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Status Kawin Suami", it)
            }
            suratDetail.kewarganegaraanSuami?.takeIf { it.isNotBlank() }?.let {
                DataRow("Kewarganegaraan Suami", it)
            }
        }

        // Data Istri
        if (hasWifeData(suratDetail)) {
            if (hasHusbandData(suratDetail)) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(Modifier.height(4.dp))
            TitleSmallText("Data Istri")
            Spacer(Modifier.height(4.dp))


            suratDetail.namaIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Nama Istri", it)
            }
            suratDetail.nikIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("NIK Istri", it)
            }
            suratDetail.alamatIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Alamat Istri", it)
            }
            suratDetail.tempatLahirIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tempat Lahir Istri", it)
            }
            suratDetail.tanggalLahirIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Tanggal Lahir Istri", it)
            }
            suratDetail.pekerjaanIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Pekerjaan Istri", it)
            }
            suratDetail.agamaIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Agama Istri", it)
            }
            suratDetail.statusKawinIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Status Kawin Istri", it)
            }
            suratDetail.kewarganegaraanIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Kewarganegaraan Istri", it)
            }
            suratDetail.jumlahIstri?.takeIf { it.isNotBlank() }?.let {
                DataRow("Jumlah Istri", it)
            }
        }
    }
}

@Composable
private fun ChildDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.namaAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Anak", it)
        }
        suratDetail.nikAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Anak", it)
        }
        suratDetail.tempatLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Anak", it)
        }
        suratDetail.tanggalLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Anak", it)
        }
        suratDetail.jenisKelaminAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin Anak", it)
        }
        suratDetail.anakKe?.let {
            DataRow("Anak Ke", it.toString())
        }
        suratDetail.kelasAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kelas", it)
        }
        suratDetail.namaSekolahAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Sekolah", it)
        }
    }
}

@Composable
private fun BusinessDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.namaUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Usaha", it)
        }
        suratDetail.jenisUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Usaha", it)
        }
        suratDetail.bidangUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Bidang Usaha", it)
        }
        suratDetail.alamatUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Usaha", it)
        }
        suratDetail.namaPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Perusahaan", it)
        }
        suratDetail.alamatPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Perusahaan", it)
        }
        suratDetail.jumlahKaryawan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jumlah Karyawan", it)
        }
        suratDetail.nib?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIB", it)
        }
        suratDetail.npwp?.takeIf { it.isNotBlank() }?.let {
            DataRow("NPWP", it)
        }
        suratDetail.nomorAktaPendirian?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor Akta Pendirian", it)
        }
    }
}

@Composable
private fun EventDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.namaAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Acara", it)
        }
        suratDetail.tempatAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Acara", it)
        }
        suratDetail.tanggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal", it)
        }
        suratDetail.hari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari", it)
        }
        suratDetail.jam?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jam", it)
        }
        suratDetail.lama?.let { lama ->
            val durasiText = if (suratDetail.satuanLama?.isNotBlank() == true) {
                "$lama ${suratDetail.satuanLama}"
            } else {
                lama.toString()
            }
            DataRow("Lama", durasiText)
        }
        suratDetail.jumlahAnggota?.let {
            DataRow("Jumlah Anggota", it.toString())
        }
        suratDetail.penanggungJawab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penanggung Jawab", it)
        }
    }
}

@Composable
private fun MoveDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.alamatPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Pindah", it)
        }
        suratDetail.alasanPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Pindah", it)
        }
        suratDetail.tempatTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Tujuan", it)
        }
        suratDetail.tanggalKeberangkatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Keberangkatan", it)
        }
        suratDetail.jumlahPengikut?.let {
            DataRow("Jumlah Pengikut", it.toString())
        }
        suratDetail.maksudTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Maksud Tujuan", it)
        }
        suratDetail.keperluan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Keperluan", it)
        }
    }
}

@Composable
private fun DeathDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.namaMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Mendiang", it)
        }
        suratDetail.nikMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Mendiang", it)
        }
        suratDetail.tempatLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Mendiang", it)
        }
        suratDetail.tanggalLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Mendiang", it)
        }
        suratDetail.jenisKelaminMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin Mendiang", it)
        }
        suratDetail.alamatMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Mendiang", it)
        }
        suratDetail.tanggalMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Meninggal", it)
        }
        suratDetail.hariMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari Meninggal", it)
        }
        suratDetail.tempatMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Meninggal", it)
        }
        suratDetail.sebabMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Sebab Meninggal", it)
        }
        suratDetail.usia?.let {
            DataRow("Usia", "$it tahun")
        }
    }
}

@Composable
private fun LossDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.namaOrangHilang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Orang Hilang", it)
        }
        suratDetail.jenisBarang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Barang", it)
        }
        suratDetail.tempatKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Kehilangan", it)
        }
        suratDetail.waktuKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Waktu Kehilangan", it)
        }
        suratDetail.hilangSejak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hilang Sejak", it)
        }
        suratDetail.ciri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Ciri-ciri", it)
        }
        suratDetail.penyebab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penyebab", it)
        }
    }
}

@Composable
private fun PropertyDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.luasTanah?.let {
            DataRow("Luas Tanah", "$it m²")
        }
        suratDetail.luasBangunan?.let {
            DataRow("Luas Bangunan", "$it m²")
        }
        suratDetail.peruntukanBangunan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Peruntukan Bangunan", it)
        }
        suratDetail.statusKepemilikanBangunan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kepemilikan", it)
        }
    }
}

@Composable
private fun OtherDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        suratDetail.hubungan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hubungan", it)
        }
        suratDetail.alasanIzin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Izin", it)
        }
        suratDetail.deskripsi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Deskripsi", it)
        }
        suratDetail.dasarPengajuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dasar Pengajuan", it)
        }
        suratDetail.dimulai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dimulai", it)
        }
        suratDetail.terhitungDari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Terhitung Dari", it)
        }
        suratDetail.jabatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan", it)
        }
        suratDetail.perbedaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Perbedaan", it)
        }
        suratDetail.tercantum?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tercantum", it)
        }
        suratDetail.wargaDesa?.let {
            DataRow("Warga Desa", if (it) "Ya" else "Tidak")
        }
    }
}

@Composable
private fun ReceiverDataSection(penerimaList: List<PenerimaDetail>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        penerimaList.forEachIndexed { index, penerima ->
            if (penerimaList.size > 1) {
                Spacer(Modifier.height(4.dp))
                TitleSmallText("Penerima ${index + 1}")
                Spacer(Modifier.height(4.dp))
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (penerima.nama.isNotBlank()) {
                    DataRow("Nama", penerima.nama)
                }
                if (penerima.nik.isNotBlank()) {
                    DataRow("NIK", penerima.nik)
                }
                if (penerima.jabatan.isNotBlank()) {
                    DataRow("Jabatan", penerima.jabatan)
                }
            }

            if (index < penerimaList.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun DataRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(0.4f)) {
            BodyMediumText(
                text = "$label:"
            )
        }
        Column(modifier = Modifier.weight(0.4f)) {
            BodyMediumText(
                text = value,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Helper functions to check if data sections have content
private fun hasPersonalData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.nama,
        suratDetail.nik,
        suratDetail.alamat,
        suratDetail.tempatLahir,
        suratDetail.tanggalLahir,
        suratDetail.jenisKelamin,
        suratDetail.agama,
        suratDetail.statusKawin,
        suratDetail.pekerjaan,
        suratDetail.kewarganegaraan,
        suratDetail.kontak
    ).any { !it.isNullOrBlank() }
}

private fun hasFamilyData(suratDetail: SuratDetail): Boolean {
    return hasParentData(suratDetail) || hasMotherData(suratDetail)
}

private fun hasParentData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaAyah,
        suratDetail.nikAyah,
        suratDetail.tempatLahirAyah,
        suratDetail.tanggalLahirAyah,
        suratDetail.pekerjaanOrtu,
        suratDetail.penghasilanOrtu,
        suratDetail.tanggunganOrtu,
        suratDetail.alamatOrtu
    ).any { !it.isNullOrBlank() }
}

private fun hasMotherData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaIbu,
        suratDetail.nikIbu,
        suratDetail.tempatLahirIbu,
        suratDetail.tanggalLahirIbu
    ).any { !it.isNullOrBlank() }
}

private fun hasSpouseData(suratDetail: SuratDetail): Boolean {
    return hasHusbandData(suratDetail) || hasWifeData(suratDetail)
}

private fun hasHusbandData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaSuami,
        suratDetail.nikSuami,
        suratDetail.alamatSuami,
        suratDetail.tempatLahirSuami,
        suratDetail.tanggalLahirSuami,
        suratDetail.pekerjaanSuami,
        suratDetail.agamaSuami,
        suratDetail.statusKawinSuami,
        suratDetail.kewarganegaraanSuami
    ).any { !it.isNullOrBlank() }
}

private fun hasWifeData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaIstri,
        suratDetail.nikIstri,
        suratDetail.alamatIstri,
        suratDetail.tempatLahirIstri,
        suratDetail.tanggalLahirIstri,
        suratDetail.pekerjaanIstri,
        suratDetail.agamaIstri,
        suratDetail.statusKawinIstri,
        suratDetail.kewarganegaraanIstri,
        suratDetail.jumlahIstri
    ).any { !it.isNullOrBlank() }
}

private fun hasChildData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaAnak,
        suratDetail.nikAnak,
        suratDetail.tempatLahirAnak,
        suratDetail.tanggalLahirAnak,
        suratDetail.jenisKelaminAnak,
        suratDetail.kelasAnak,
        suratDetail.namaSekolahAnak
    ).any { !it.isNullOrBlank() } || suratDetail.anakKe != null
}

private fun hasBusinessData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaUsaha,
        suratDetail.jenisUsaha,
        suratDetail.bidangUsaha,
        suratDetail.alamatUsaha,
        suratDetail.namaPerusahaan,
        suratDetail.alamatPerusahaan,
        suratDetail.jumlahKaryawan,
        suratDetail.nib,
        suratDetail.npwp,
        suratDetail.nomorAktaPendirian
    ).any { !it.isNullOrBlank() }
}

private fun hasEventData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaAcara,
        suratDetail.tempatAcara,
        suratDetail.tanggal,
        suratDetail.hari,
        suratDetail.jam,
        suratDetail.satuanLama,
        suratDetail.penanggungJawab
    ).any { !it.isNullOrBlank() } ||
            suratDetail.lama != null ||
            suratDetail.jumlahAnggota != null
}

private fun hasMoveData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.alamatPindah,
        suratDetail.alasanPindah,
        suratDetail.tempatTujuan,
        suratDetail.tanggalKeberangkatan,
        suratDetail.maksudTujuan,
        suratDetail.keperluan
    ).any { !it.isNullOrBlank() } || suratDetail.jumlahPengikut != null
}

private fun hasDeathData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaMendiang,
        suratDetail.nikMendiang,
        suratDetail.tempatLahirMendiang,
        suratDetail.tanggalLahirMendiang,
        suratDetail.jenisKelaminMendiang,
        suratDetail.alamatMendiang,
        suratDetail.tanggalMeninggal,
        suratDetail.hariMeninggal,
        suratDetail.tempatMeninggal,
        suratDetail.sebabMeninggal
    ).any { !it.isNullOrBlank() } || suratDetail.usia != null
}

private fun hasLossData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.namaOrangHilang,
        suratDetail.jenisBarang,
        suratDetail.tempatKehilangan,
        suratDetail.waktuKehilangan,
        suratDetail.hilangSejak,
        suratDetail.ciri,
        suratDetail.penyebab
    ).any { !it.isNullOrBlank() }
}

private fun hasPropertyData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.peruntukanBangunan,
        suratDetail.statusKepemilikanBangunan
    ).any { !it.isNullOrBlank() } ||
            suratDetail.luasTanah != null ||
            suratDetail.luasBangunan != null
}

private fun hasOtherData(suratDetail: SuratDetail): Boolean {
    return listOf(
        suratDetail.hubungan,
        suratDetail.alasanIzin,
        suratDetail.deskripsi,
        suratDetail.dasarPengajuan,
        suratDetail.dimulai,
        suratDetail.terhitungDari,
        suratDetail.jabatan,
        suratDetail.perbedaan,
        suratDetail.tercantum
    ).any { !it.isNullOrBlank() } || suratDetail.wargaDesa != null
}
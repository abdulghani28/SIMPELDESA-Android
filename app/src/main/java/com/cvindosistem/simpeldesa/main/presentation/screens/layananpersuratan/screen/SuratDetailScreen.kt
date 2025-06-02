package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.core.helpers.formatDateTime
import com.cvindosistem.simpeldesa.main.presentation.components.StatusChip
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
                .background(MaterialTheme.colorScheme.surfaceBright)
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

        // Data Pemohon/Personal Data
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

        // Data Acara (SR Keramaian)
        if (hasEventData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Acara",
                    content = {
                        EventDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Barang Hilang (SP Kehilangan)
        if (hasLostItemData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Barang Hilang",
                    content = {
                        LostItemDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pernikahan (SP Pernikahan)
        if (hasMarriageData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pernikahan",
                    content = {
                        MarriageDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Suami (SP Pernikahan)
        if (hasHusbandData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Suami",
                    content = {
                        HusbandDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Istri (SP Pernikahan)
        if (hasWifeData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Istri",
                    content = {
                        WifeDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ayah Suami (SP Pernikahan)
        if (hasHusbandFatherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ayah Suami",
                    content = {
                        HusbandFatherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ibu Suami (SP Pernikahan)
        if (hasHusbandMotherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ibu Suami",
                    content = {
                        HusbandMotherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ayah Istri (SP Pernikahan)
        if (hasWifeFatherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ayah Istri",
                    content = {
                        WifeFatherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ibu Istri (SP Pernikahan)
        if (hasWifeMotherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ibu Istri",
                    content = {
                        WifeMotherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pindah (SP Pindah Domisili)
        if (hasRelocationData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pindah Domisili",
                    content = {
                        RelocationDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pemberi Kuasa (Surat Kuasa)
        if (hasGrantorData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pemberi Kuasa",
                    content = {
                        GrantorDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Penerima Kuasa (Surat Kuasa)
        if (hasReceiverData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Penerima Kuasa",
                    content = {
                        ReceiverDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Kuasa (Surat Kuasa)
        if (hasAuthorityData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Kuasa",
                    content = {
                        AuthorityDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pemberi Tugas (Surat Tugas)
        if (hasTaskAssignerData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pemberi Tugas",
                    content = {
                        TaskAssignerDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Tugas (Surat Tugas)
        if (hasTaskData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Tugas",
                    content = {
                        TaskDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Penerima Tugas (Surat Tugas)
        if (hasTaskReceiverData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Penerima Tugas",
                    content = {
                        TaskReceiverDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pemohon (SK Beda Identitas)
        if (hasApplicantData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pemohon",
                    content = {
                        ApplicantDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Identitas 1 (SK Beda Identitas)
        if (hasIdentity1Data(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Identitas 1",
                    content = {
                        Identity1DataSection(suratDetail)
                    }
                )
            }
        }

        // Data Identitas 2 (SK Beda Identitas)
        if (hasIdentity2Data(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Identitas 2",
                    content = {
                        Identity2DataSection(suratDetail)
                    }
                )
            }
        }

        // Data Perbedaan (SK Beda Identitas)
        if (hasDifferenceData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Perbedaan",
                    content = {
                        DifferenceDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Perjalanan (SK Bepergian)
        if (hasTravelData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Perjalanan",
                    content = {
                        TravelDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Orang Hilang (SK Ghaib)
        if (hasMissingPersonData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Orang Hilang",
                    content = {
                        MissingPersonDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Pekerjaan (SK Izin Tidak Masuk Kerja)
        if (hasJobData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Pekerjaan",
                    content = {
                        JobDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Izin (SK Izin Tidak Masuk Kerja)
        if (hasPermitData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Izin",
                    content = {
                        PermitDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Status (SK Janda/Duda)
        if (hasStatusData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Status",
                    content = {
                        StatusDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Bayi (SK Kelahiran)
        if (hasBabyData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Bayi",
                    content = {
                        BabyDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ayah (SK Kelahiran)
        if (hasFatherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ayah",
                    content = {
                        FatherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Ibu (SK Kelahiran)
        if (hasMotherData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Ibu",
                    content = {
                        MotherDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Almarhum (SK Kematian)
        if (hasDeceasedData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Almarhum",
                    content = {
                        DeceasedDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Orang Tua (SK Penghasilan)
        if (hasParentData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Orang Tua",
                    content = {
                        ParentDataSection(suratDetail)
                    }
                )
            }
        }

        // Data Anak (SK Penghasilan)
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

        // Data Usaha (SK Usaha)
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

        // Data Perusahaan (SK Perusahaan)
        if (hasCompanyData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Data Perusahaan",
                    content = {
                        CompanyDataSection(suratDetail)
                    }
                )
            }
        }

        // Keperluan
        if (hasPurposeData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Keperluan",
                    content = {
                        PurposeDataSection(suratDetail)
                    }
                )
            }
        }

        // Hubungan (SK Ghaib dan SK Kehilangan)
        if (hasRelationshipData(suratDetail)) {
            item {
                SuratSectionCard(
                    title = "Hubungan",
                    content = {
                        RelationshipDataSection(suratDetail)
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
                StatusChip(status = suratDetail.dataUmum.status)

                if (suratDetail.dataUmum.tanggalPengajuan != null) {
                    BodySmallText(text = formatDateTime(suratDetail.dataUmum.tanggalPengajuan))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TitleMediumText(text = suratDetail.dataUmum.jenisSurat)

            if (suratDetail.dataUmum.nomorSurat != null) {
                Spacer(modifier = Modifier.height(8.dp))
                BodyMediumText(text = "No. Surat: ${suratDetail.dataUmum.nomorSurat}")
            }

            if (suratDetail.dataUmum.nomorPengajuan != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodyMediumText(text = "No. Pengajuan: ${suratDetail.dataUmum.nomorPengajuan}")
            }

            if (suratDetail.dataUmum.tanggalSelesai != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Selesai: ${suratDetail.dataUmum.tanggalSelesai}")
            }

            if (suratDetail.dataUmum.diprosesOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Diproses oleh: ${suratDetail.dataUmum.diprosesOleh}")
            }

            if (suratDetail.dataUmum.disahkanOleh != null) {
                Spacer(modifier = Modifier.height(4.dp))
                BodySmallText(text = "Disahkan oleh: ${suratDetail.dataUmum.disahkanOleh}")
            }
        }
    }
}

@Composable
private fun PersonalDataSection(suratDetail: SuratDetail) {
    val dataPersonal = suratDetail.dataPribadi

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPersonal.nik?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK", it)
        }
        dataPersonal.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Lengkap", it)
        }
        dataPersonal.jabatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan", it)
        }
        dataPersonal.pekerjaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan", it)
        }
        dataPersonal.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataPersonal.tempatLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataPersonal.tanggalLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataPersonal.kewarganegaraan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan", it)
        }
        dataPersonal.alamatIdentitas?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Sesuai Identitas", it)
        }
        dataPersonal.alamat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Sekarang", it)
        }
        dataPersonal.agama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama", it)
        }
        dataPersonal.statusKawin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin", it)
        }
        dataPersonal.jumlahPengikut?.takeIf { it != 0 }?.let {
            DataRow("Jumlah Pengikut", it.toString())
        }
        dataPersonal.kontak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kontak", it)
        }
        dataPersonal.wargaDesa?.let {
            DataRow("Status", if(it) "Warga Desa" else "Pendatang")
        }
    }
}

@Composable
private fun EventDataSection(suratDetail: SuratDetail) {
    val dataAcara = suratDetail.dataAcara ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAcara.namaAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Acara", it)
        }
        dataAcara.tempatAcara?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Acara", it)
        }
        dataAcara.hari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari", it)
        }
        dataAcara.tanggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal", dateFormatterToApiFormat(it))
        }
        dataAcara.dimulai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dimulai", it)
        }
        dataAcara.selesai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Selesai", it)
        }
        dataAcara.penanggungJawab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penanggung Jawab", it)
        }
    }
}

@Composable
private fun LostItemDataSection(suratDetail: SuratDetail) {
    val dataBarangHilang = suratDetail.dataBarangHilang ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataBarangHilang.jenisBarang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Barang", it)
        }
        dataBarangHilang.ciri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Ciri-ciri", it)
        }
        dataBarangHilang.tempatKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Kehilangan", it)
        }
        dataBarangHilang.waktuKehilangan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Waktu Kehilangan", formatDateTime(it))
        }
    }
}

@Composable
private fun MarriageDataSection(suratDetail: SuratDetail) {
    val dataPernikahan = suratDetail.dataPernikahan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPernikahan.hari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari", it)
        }
        dataPernikahan.tanggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal", dateFormatterToApiFormat(it))
        }
        dataPernikahan.jam?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jam", it)
        }
        dataPernikahan.tempat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat", it)
        }
        dataPernikahan.jumlahIstri?.let {
            DataRow("Jumlah Istri", it.toString())
        }
    }
}

@Composable
private fun HusbandDataSection(suratDetail: SuratDetail) {
    val dataSuami = suratDetail.dataSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataSuami.nikSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Suami", it)
        }
        dataSuami.namaSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Suami", it)
        }
        dataSuami.namaSuamiSebelumnya?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Suami Sebelumnya", it)
        }
        dataSuami.tanggalLahirSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Suami", dateFormatterToApiFormat(it))
        }
        dataSuami.tempatLahirSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Suami", it)
        }
        dataSuami.alamatSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Suami", it)
        }
        dataSuami.agamaSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Suami", it)
        }
        dataSuami.kewarganegaraanSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Suami", it)
        }
        dataSuami.pekerjaanSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Suami", it)
        }
        dataSuami.statusKawinSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin Suami", it)
        }
    }
}

@Composable
private fun WifeDataSection(suratDetail: SuratDetail) {
    val dataIstri = suratDetail.dataIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIstri.nikIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Istri", it)
        }
        dataIstri.namaIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Istri", it)
        }
        dataIstri.namaIstriSebelumnya?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Istri Sebelumnya", it)
        }
        dataIstri.tanggalLahirIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Istri", dateFormatterToApiFormat(it))
        }
        dataIstri.tempatLahirIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Istri", it)
        }
        dataIstri.alamatIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Istri", it)
        }
        dataIstri.agamaIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Istri", it)
        }
        dataIstri.kewarganegaraanIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Istri", it)
        }
        dataIstri.pekerjaanIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Istri", it)
        }
        dataIstri.statusKawinIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kawin Istri", it)
        }
    }
}

@Composable
private fun TaskReceiverDataSection(suratDetail: SuratDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        suratDetail.dataPenerimaTugas.forEachIndexed { index, penerima ->
            if (listOf(penerima.nama, penerima.nik, penerima.jabatan).any { !it.isNullOrBlank() }) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (suratDetail.dataPenerimaTugas.size > 1) {
                        TitleSmallText("Penerima Tugas ${index + 1}")
                        Spacer(Modifier.height(4.dp))
                    }

                    penerima.nama?.takeIf { it.isNotBlank() }?.let {
                        DataRow("Nama", it)
                    }
                    penerima.nik?.takeIf { it.isNotBlank() }?.let {
                        DataRow("NIK", it)
                    }
                    penerima.jabatan?.takeIf { it.isNotBlank() }?.let {
                        DataRow("Jabatan", it)
                    }
                }
            }
        }
    }
}

@Composable
private fun PurposeDataSection(suratDetail: SuratDetail) {
    suratDetail.keperluan?.takeIf { it.isNotBlank() }?.let {
        DataRow("Keperluan", it)
    }
}

@Composable
private fun RelationshipDataSection(suratDetail: SuratDetail) {
    suratDetail.hubunganId?.takeIf { it.isNotBlank() }?.let {
        DataRow("Hubungan", it)
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

@Composable
private fun HusbandFatherDataSection(suratDetail: SuratDetail) {
    val dataAyahSuami = suratDetail.dataAyahSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyahSuami.nikAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah Suami", it)
        }
        dataAyahSuami.namaAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah Suami", it)
        }
        dataAyahSuami.tanggalLahirAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah Suami", dateFormatterToApiFormat(it))
        }
        dataAyahSuami.tempatLahirAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah Suami", it)
        }
        dataAyahSuami.alamatAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah Suami", it)
        }
        dataAyahSuami.agamaAyahSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ayah Suami", it)
        }
        dataAyahSuami.kewarganegaraanAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ayah Suami", it)
        }
        dataAyahSuami.pekerjaanAyahSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ayah Suami", it)
        }
    }
}

@Composable
private fun HusbandMotherDataSection(suratDetail: SuratDetail) {
    val dataIbuSuami = suratDetail.dataIbuSuami ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbuSuami.nikIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu Suami", it)
        }
        dataIbuSuami.namaIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu Suami", it)
        }
        dataIbuSuami.tanggalLahirIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu Suami", dateFormatterToApiFormat(it))
        }
        dataIbuSuami.tempatLahirIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu Suami", it)
        }
        dataIbuSuami.alamatIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu Suami", it)
        }
        dataIbuSuami.agamaIbuSuamiId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ibu Suami", it)
        }
        dataIbuSuami.kewarganegaraanIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ibu Suami", it)
        }
        dataIbuSuami.pekerjaanIbuSuami?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ibu Suami", it)
        }
    }
}

@Composable
private fun WifeFatherDataSection(suratDetail: SuratDetail) {
    val dataAyahIstri = suratDetail.dataAyahIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyahIstri.nikAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah Istri", it)
        }
        dataAyahIstri.namaAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah Istri", it)
        }
        dataAyahIstri.tanggalLahirAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah Istri", dateFormatterToApiFormat(it))
        }
        dataAyahIstri.tempatLahirAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah Istri", it)
        }
        dataAyahIstri.alamatAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah Istri", it)
        }
        dataAyahIstri.agamaAyahIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ayah Istri", it)
        }
        dataAyahIstri.kewarganegaraanAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ayah Istri", it)
        }
        dataAyahIstri.pekerjaanAyahIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ayah Istri", it)
        }
    }
}

@Composable
private fun WifeMotherDataSection(suratDetail: SuratDetail) {
    val dataIbuIstri = suratDetail.dataIbuIstri ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbuIstri.nikIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu Istri", it)
        }
        dataIbuIstri.namaIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu Istri", it)
        }
        dataIbuIstri.tanggalLahirIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu Istri", dateFormatterToApiFormat(it))
        }
        dataIbuIstri.tempatLahirIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu Istri", it)
        }
        dataIbuIstri.alamatIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu Istri", it)
        }
        dataIbuIstri.agamaIbuIstriId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Agama Ibu Istri", it)
        }
        dataIbuIstri.kewarganegaraanIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kewarganegaraan Ibu Istri", it)
        }
        dataIbuIstri.pekerjaanIbuIstri?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan Ibu Istri", it)
        }
    }
}

@Composable
private fun RelocationDataSection(suratDetail: SuratDetail) {
    val dataPindah = suratDetail.dataPindah ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPindah.alamat?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Asal", it)
        }
        dataPindah.alamatPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Pindah", it)
        }
        dataPindah.alasanPindah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Pindah", it)
        }
        dataPindah.jumlahAnggota?.let {
            DataRow("Jumlah Anggota", it.toString())
        }
    }
}

@Composable
private fun GrantorDataSection(suratDetail: SuratDetail) {
    val dataPemberiKuasa = suratDetail.dataPemberiKuasa ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemberiKuasa.nikPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Pemberi Kuasa", it)
        }
        dataPemberiKuasa.namaPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Pemberi Kuasa", it)
        }
        dataPemberiKuasa.jabatanPemberi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan Pemberi Kuasa", it)
        }
    }
}

@Composable
private fun ReceiverDataSection(suratDetail: SuratDetail) {
    val dataPenerimaKuasa = suratDetail.dataPenerimaKuasa ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPenerimaKuasa.nikPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Penerima Kuasa", it)
        }
        dataPenerimaKuasa.namaPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Penerima Kuasa", it)
        }
        dataPenerimaKuasa.jabatanPenerima?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan Penerima Kuasa", it)
        }
    }
}

@Composable
private fun AuthorityDataSection(suratDetail: SuratDetail) {
    val dataKuasa = suratDetail.dataKuasa ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataKuasa.disposisiKuasaSebagai?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kuasa Sebagai", it)
        }
        dataKuasa.disposisiKuasaUntuk?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kuasa Untuk", it)
        }
    }
}

@Composable
private fun TaskAssignerDataSection(suratDetail: SuratDetail) {
    val dataPemberiTugas = suratDetail.dataPemberiTugas ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemberiTugas.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Pemberi Tugas", it)
        }
        dataPemberiTugas.nik?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Pemberi Tugas", it)
        }
    }
}

@Composable
private fun TaskDataSection(suratDetail: SuratDetail) {
    val dataTugas = suratDetail.dataTugas ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataTugas.deskripsi?.takeIf { it.isNotBlank() }?.let {
            DataRow("Deskripsi Tugas", it)
        }
        dataTugas.ditugaskanUntuk?.takeIf { it.isNotBlank() }?.let {
            DataRow("Ditugaskan Untuk", it)
        }
    }
}

@Composable
private fun ApplicantDataSection(suratDetail: SuratDetail) {
    val dataPemohon = suratDetail.dataPemohon ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPemohon.diajukanOlehNik?.takeIf { it.isNotBlank() }?.let {
            DataRow("Diajukan Oleh (NIK)", it)
        }
    }
}

@Composable
private fun Identity1DataSection(suratDetail: SuratDetail) {
    val dataIdentitas1 = suratDetail.dataIdentitas1 ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIdentitas1.nama1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama", it)
        }
        dataIdentitas1.nomor1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor", it)
        }
        dataIdentitas1.tanggalLahir1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataIdentitas1.tempatLahir1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataIdentitas1.alamat1?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataIdentitas1.tercantumId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tercantum Dalam", it)
        }
    }
}

@Composable
private fun Identity2DataSection(suratDetail: SuratDetail) {
    val dataIdentitas2 = suratDetail.dataIdentitas2 ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIdentitas2.nama2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama", it)
        }
        dataIdentitas2.nomor2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor", it)
        }
        dataIdentitas2.tanggalLahir2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataIdentitas2.tempatLahir2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataIdentitas2.alamat2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataIdentitas2.tercantumId2?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tercantum Dalam", it)
        }
    }
}

@Composable
private fun DifferenceDataSection(suratDetail: SuratDetail) {
    val dataPerbedaan = suratDetail.dataPerbedaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPerbedaan.perbedaanId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Perbedaan", it)
        }
    }
}

@Composable
private fun TravelDataSection(suratDetail: SuratDetail) {
    val dataPerjalanan = suratDetail.dataPerjalanan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPerjalanan.tempatTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Tujuan", it)
        }
        dataPerjalanan.tanggalKeberangkatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Keberangkatan", dateFormatterToApiFormat(it))
        }
        dataPerjalanan.lama?.let {
            DataRow("Lama Perjalanan", it.toString())
        }
        dataPerjalanan.satuanLama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Satuan Lama", it)
        }
        dataPerjalanan.maksudTujuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Maksud Tujuan", it)
        }
        dataPerjalanan.jumlahPengikut?.let {
            DataRow("Jumlah Pengikut", it.toString())
        }
    }
}

@Composable
private fun MissingPersonDataSection(suratDetail: SuratDetail) {
    val dataOrangHilang = suratDetail.dataOrangHilang ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataOrangHilang.namaOrangHilang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Orang Hilang", it)
        }
        dataOrangHilang.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataOrangHilang.usia?.let {
            DataRow("Usia", it.toString())
        }
        dataOrangHilang.hilangSejak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hilang Sejak", it)
        }
    }
}

@Composable
private fun JobDataSection(suratDetail: SuratDetail) {
    val dataPekerjaan = suratDetail.dataPekerjaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataPekerjaan.namaPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Perusahaan", it)
        }
        dataPekerjaan.jabatan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jabatan", it)
        }
    }
}

@Composable
private fun PermitDataSection(suratDetail: SuratDetail) {
    val dataIzin = suratDetail.dataIzin ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIzin.alasanIzin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alasan Izin", it)
        }
        dataIzin.terhitungDari?.takeIf { it.isNotBlank() }?.let {
            DataRow("Terhitung Dari", dateFormatterToApiFormat(it))
        }
        dataIzin.lama?.let {
            DataRow("Lama Izin", "${it} Hari")
        }
    }
}

@Composable
private fun StatusDataSection(suratDetail: SuratDetail) {
    val dataStatus = suratDetail.dataStatus ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataStatus.dasarPengajuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Dasar Pengajuan", it)
        }
        dataStatus.penyebab?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penyebab", it)
        }
        dataStatus.nomorPengajuan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor Pengajuan", it)
        }
    }
}

@Composable
private fun BabyDataSection(suratDetail: SuratDetail) {
    val dataBayi = suratDetail.dataBayi ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataBayi.nama?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Bayi", it)
        }
        dataBayi.jenisKelamin?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataBayi.tanggalLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataBayi.jamLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jam Lahir", it)
        }
        dataBayi.tempatLahir?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataBayi.anakKe?.let {
            DataRow("Anak Ke", it.toString())
        }
    }
}

@Composable
private fun FatherDataSection(suratDetail: SuratDetail) {
    val dataAyah = suratDetail.dataAyah ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAyah.nikAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ayah", it)
        }
        dataAyah.namaAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ayah", it)
        }
        dataAyah.tanggalLahirAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ayah", dateFormatterToApiFormat(it))
        }
        dataAyah.tempatLahirAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ayah", it)
        }
        dataAyah.alamatAyah?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ayah", it)
        }
    }
}

@Composable
private fun MotherDataSection(suratDetail: SuratDetail) {
    val dataIbu = suratDetail.dataIbu ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataIbu.nikIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Ibu", it)
        }
        dataIbu.namaIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Ibu", it)
        }
        dataIbu.tanggalLahirIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir Ibu", dateFormatterToApiFormat(it))
        }
        dataIbu.tempatLahirIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir Ibu", it)
        }
        dataIbu.alamatIbu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Ibu", it)
        }
    }
}

@Composable
private fun DeceasedDataSection(suratDetail: SuratDetail) {
    val dataAlmarhum = suratDetail.dataAlmarhum ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAlmarhum.namaMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Almarhum", it)
        }
        dataAlmarhum.nikMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Almarhum", it)
        }
        dataAlmarhum.jenisKelaminMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataAlmarhum.tanggalLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataAlmarhum.tempatLahirMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataAlmarhum.alamatMendiang?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataAlmarhum.tanggalMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Meninggal", dateFormatterToApiFormat(it))
        }
        dataAlmarhum.hariMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Hari Meninggal", it)
        }
        dataAlmarhum.tempatMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Meninggal", it)
        }
        dataAlmarhum.sebabMeninggal?.takeIf { it.isNotBlank() }?.let {
            DataRow("Sebab Meninggal", it)
        }
    }
}

@Composable
private fun ParentDataSection(suratDetail: SuratDetail) {
    val dataOrangTua = suratDetail.dataOrangTua ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataOrangTua.nikOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Orang Tua", it)
        }
        dataOrangTua.namaOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Orang Tua", it)
        }
        dataOrangTua.jenisKelaminOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataOrangTua.tanggalLahirOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataOrangTua.tempatLahirOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataOrangTua.alamatOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat", it)
        }
        dataOrangTua.pekerjaanOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Pekerjaan", it)
        }
        dataOrangTua.penghasilanOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Penghasilan", it)
        }
        dataOrangTua.tanggunganOrtu?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggungan", it)
        }
    }
}

@Composable
private fun ChildDataSection(suratDetail: SuratDetail) {
    val dataAnak = suratDetail.dataAnak ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataAnak.nikAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("NIK Anak", it)
        }
        dataAnak.namaAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Anak", it)
        }
        dataAnak.jenisKelaminAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Kelamin", it)
        }
        dataAnak.tanggalLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tanggal Lahir", dateFormatterToApiFormat(it))
        }
        dataAnak.tempatLahirAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Tempat Lahir", it)
        }
        dataAnak.namaSekolahAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Sekolah", it)
        }
        dataAnak.kelasAnak?.takeIf { it.isNotBlank() }?.let {
            DataRow("Kelas", it)
        }
    }
}

@Composable
private fun CompanyDataSection(suratDetail: SuratDetail) {
    val dataUsaha = suratDetail.dataPerusahaan ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataUsaha.namaPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Perusahaan", it)
        }
        dataUsaha.jenisUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Perusahaan", it)
        }
        dataUsaha.bidangUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Bidang Usaha", it)
        }
        dataUsaha.noAkta?.takeIf { it.isNotBlank() }?.let {
            DataRow("No Notaris/Akta Pendirian", it)
        }
        dataUsaha.nib?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nomor Induk Berusaha (NIB)", it)
        }
        dataUsaha.peruntukanBangunan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Peruntukan Bangunan", it)
        }
        dataUsaha.luasTanah?.takeIf { it != 0 }?.let {
            DataRow("Luas Tanah", it.toString())
        }
        dataUsaha.luasBangunan?.takeIf { it != 0 }?.let {
            DataRow("Luas Bangunan", it.toString())
        }
        dataUsaha.statusKepemilikan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Status Kepemilikan", it)
        }
        dataUsaha.alamatPerusahaan?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Usaha", it)
        }
        dataUsaha.npwp?.takeIf { it.isNotBlank() }?.let {
            DataRow("NPWP", it)
        }
    }
}

@Composable
private fun BusinessDataSection(suratDetail: SuratDetail) {
    val dataUsaha = suratDetail.dataUsaha ?: return

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dataUsaha.namaUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Nama Usaha", it)
        }
        dataUsaha.jenisUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Jenis Usaha", it)
        }
        dataUsaha.bidangUsahaId?.takeIf { it.isNotBlank() }?.let {
            DataRow("Bidang Usaha", it)
        }
        dataUsaha.npwp?.takeIf { it.isNotBlank() }?.let {
            DataRow("NPWP", it)
        }
        dataUsaha.alamatUsaha?.takeIf { it.isNotBlank() }?.let {
            DataRow("Alamat Usaha", it)
        }
        dataUsaha.wargaDesa?.let {
            DataRow("Status", if(it) "Warga Desa" else "Pendatang")
        }
    }
}

// Helper functions untuk mengecek ketersediaan data pada SuratDetail
// Data Pribadi/Pemohon
private fun hasPersonalData(suratDetail: SuratDetail): Boolean {
    val dataPribadi = suratDetail.dataPribadi
    return listOf(
        dataPribadi.nama,
        dataPribadi.nik,
        dataPribadi.alamat,
        dataPribadi.alamatIdentitas,
        dataPribadi.tempatLahir,
        dataPribadi.tanggalLahir,
        dataPribadi.jenisKelamin,
        dataPribadi.agama,
        dataPribadi.agamaId,
        dataPribadi.statusKawin,
        dataPribadi.statusKawinId,
        dataPribadi.pekerjaan,
        dataPribadi.kewarganegaraan,
        dataPribadi.kontak,
        dataPribadi.jabatan
    ).any { !it.isNullOrBlank() } || dataPribadi.wargaDesa == true || dataPribadi.jumlahPengikut != 0
}

// Data Acara (SR Keramaian)
private fun hasEventData(suratDetail: SuratDetail): Boolean {
    val dataAcara = suratDetail.dataAcara ?: return false
    return listOf(
        dataAcara.namaAcara,
        dataAcara.tempatAcara,
        dataAcara.hari,
        dataAcara.tanggal,
        dataAcara.dimulai,
        dataAcara.selesai,
        dataAcara.penanggungJawab
    ).any { !it.isNullOrBlank() }
}

// Data Barang Hilang (SP Kehilangan)
private fun hasLostItemData(suratDetail: SuratDetail): Boolean {
    val dataBarangHilang = suratDetail.dataBarangHilang ?: return false
    return listOf(
        dataBarangHilang.jenisBarang,
        dataBarangHilang.ciri,
        dataBarangHilang.tempatKehilangan,
        dataBarangHilang.waktuKehilangan
    ).any { !it.isNullOrBlank() }
}

// Data Suami (SP Pernikahan)
private fun hasHusbandData(suratDetail: SuratDetail): Boolean {
    val dataSuami = suratDetail.dataSuami ?: return false
    return listOf(
        dataSuami.nikSuami,
        dataSuami.namaSuami,
        dataSuami.namaSuamiSebelumnya,
        dataSuami.tanggalLahirSuami,
        dataSuami.tempatLahirSuami,
        dataSuami.alamatSuami,
        dataSuami.agamaSuamiId,
        dataSuami.kewarganegaraanSuami,
        dataSuami.pekerjaanSuami,
        dataSuami.statusKawinSuamiId
    ).any { !it.isNullOrBlank() }
}

// Data Istri (SP Pernikahan)
private fun hasWifeData(suratDetail: SuratDetail): Boolean {
    val dataIstri = suratDetail.dataIstri ?: return false
    return listOf(
        dataIstri.nikIstri,
        dataIstri.namaIstri,
        dataIstri.namaIstriSebelumnya,
        dataIstri.tanggalLahirIstri,
        dataIstri.tempatLahirIstri,
        dataIstri.alamatIstri,
        dataIstri.agamaIstriId,
        dataIstri.kewarganegaraanIstri,
        dataIstri.pekerjaanIstri,
        dataIstri.statusKawinIstriId
    ).any { !it.isNullOrBlank() }
}

// Data Ayah Suami (SP Pernikahan)
private fun hasHusbandFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyahSuami = suratDetail.dataAyahSuami ?: return false
    return listOf(
        dataAyahSuami.nikAyahSuami,
        dataAyahSuami.namaAyahSuami,
        dataAyahSuami.tanggalLahirAyahSuami,
        dataAyahSuami.tempatLahirAyahSuami,
        dataAyahSuami.alamatAyahSuami,
        dataAyahSuami.agamaAyahSuamiId,
        dataAyahSuami.kewarganegaraanAyahSuami,
        dataAyahSuami.pekerjaanAyahSuami
    ).any { !it.isNullOrBlank() }
}

// Data Ibu Suami (SP Pernikahan)
private fun hasHusbandMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbuSuami = suratDetail.dataIbuSuami ?: return false
    return listOf(
        dataIbuSuami.nikIbuSuami,
        dataIbuSuami.namaIbuSuami,
        dataIbuSuami.tanggalLahirIbuSuami,
        dataIbuSuami.tempatLahirIbuSuami,
        dataIbuSuami.alamatIbuSuami,
        dataIbuSuami.agamaIbuSuamiId,
        dataIbuSuami.kewarganegaraanIbuSuami,
        dataIbuSuami.pekerjaanIbuSuami
    ).any { !it.isNullOrBlank() }
}

// Data Ayah Istri (SP Pernikahan)
private fun hasWifeFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyahIstri = suratDetail.dataAyahIstri ?: return false
    return listOf(
        dataAyahIstri.nikAyahIstri,
        dataAyahIstri.namaAyahIstri,
        dataAyahIstri.tanggalLahirAyahIstri,
        dataAyahIstri.tempatLahirAyahIstri,
        dataAyahIstri.alamatAyahIstri,
        dataAyahIstri.agamaAyahIstriId,
        dataAyahIstri.kewarganegaraanAyahIstri,
        dataAyahIstri.pekerjaanAyahIstri
    ).any { !it.isNullOrBlank() }
}

// Data Ibu Istri (SP Pernikahan)
private fun hasWifeMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbuIstri = suratDetail.dataIbuIstri ?: return false
    return listOf(
        dataIbuIstri.nikIbuIstri,
        dataIbuIstri.namaIbuIstri,
        dataIbuIstri.tanggalLahirIbuIstri,
        dataIbuIstri.tempatLahirIbuIstri,
        dataIbuIstri.alamatIbuIstri,
        dataIbuIstri.agamaIbuIstriId,
        dataIbuIstri.kewarganegaraanIbuIstri,
        dataIbuIstri.pekerjaanIbuIstri
    ).any { !it.isNullOrBlank() }
}

// Data Pernikahan (SP Pernikahan)
private fun hasMarriageData(suratDetail: SuratDetail): Boolean {
    val dataPernikahan = suratDetail.dataPernikahan ?: return false
    return listOf(
        dataPernikahan.hari,
        dataPernikahan.tanggal,
        dataPernikahan.jam,
        dataPernikahan.tempat
    ).any { dataPernikahan.jumlahIstri != null }
}

// Data Pindah (SP Pindah Domisili)
private fun hasRelocationData(suratDetail: SuratDetail): Boolean {
    val dataPindah = suratDetail.dataPindah ?: return false
    return listOf(
        dataPindah.alamat,
        dataPindah.alamatPindah,
        dataPindah.alasanPindah
    ).any { !dataPindah.alamatPindah.isNullOrBlank() } || dataPindah.jumlahAnggota != null
}

// Data Pemberi Kuasa (Surat Kuasa)
private fun hasGrantorData(suratDetail: SuratDetail): Boolean {
    val dataPemberiKuasa = suratDetail.dataPemberiKuasa ?: return false
    return listOf(
        dataPemberiKuasa.nikPemberi,
        dataPemberiKuasa.namaPemberi,
        dataPemberiKuasa.jabatanPemberi
    ).any { !it.isNullOrBlank() }
}

// Data Penerima Kuasa (Surat Kuasa)
private fun hasReceiverData(suratDetail: SuratDetail): Boolean {
    val dataPenerimaKuasa = suratDetail.dataPenerimaKuasa ?: return false
    return listOf(
        dataPenerimaKuasa.nikPenerima,
        dataPenerimaKuasa.namaPenerima,
        dataPenerimaKuasa.jabatanPenerima
    ).any { !it.isNullOrBlank() }
}

// Data Kuasa (Surat Kuasa)
private fun hasAuthorityData(suratDetail: SuratDetail): Boolean {
    val dataKuasa = suratDetail.dataKuasa ?: return false
    return listOf(
        dataKuasa.disposisiKuasaSebagai,
        dataKuasa.disposisiKuasaUntuk
    ).any { !it.isNullOrBlank() }
}

// Data Pemberi Tugas (Surat Tugas)
private fun hasTaskAssignerData(suratDetail: SuratDetail): Boolean {
    val dataPemberiTugas = suratDetail.dataPemberiTugas ?: return false
    return listOf(
        dataPemberiTugas.jabatanPemberi,
        dataPemberiTugas.nama,
        dataPemberiTugas.nik,
        dataPemberiTugas.ditugaskanUntuk
    ).any { !dataPemberiTugas.ditugaskanUntuk.isNullOrBlank() }
}

// Data Tugas (Surat Tugas)
private fun hasTaskData(suratDetail: SuratDetail): Boolean {
    val dataTugas = suratDetail.dataTugas ?: return false
    return listOf(
        dataTugas.deskripsi,
        dataTugas.ditugaskanUntuk
    ).any { !it.isNullOrBlank() }
}

// Data Penerima Tugas (Surat Tugas)
private fun hasTaskReceiverData(suratDetail: SuratDetail): Boolean {
    return suratDetail.dataPenerimaTugas.isNotEmpty() &&
            suratDetail.dataPenerimaTugas.any { penerima ->
                listOf(penerima.nama, penerima.nik, penerima.jabatan).any { !it.isNullOrBlank() }
            }
}

// Data Pemohon (SK Beda Identitas)
private fun hasApplicantData(suratDetail: SuratDetail): Boolean {
    val dataPemohon = suratDetail.dataPemohon ?: return false
    return !dataPemohon.diajukanOlehNik.isNullOrBlank()
}

// Data Identitas 1 (SK Beda Identitas)
private fun hasIdentity1Data(suratDetail: SuratDetail): Boolean {
    val dataIdentitas1 = suratDetail.dataIdentitas1 ?: return false
    return listOf(
        dataIdentitas1.nama1,
        dataIdentitas1.nomor1,
        dataIdentitas1.tanggalLahir1,
        dataIdentitas1.tempatLahir1,
        dataIdentitas1.alamat1,
        dataIdentitas1.tercantumId
    ).any { !it.isNullOrBlank() }
}

// Data Identitas 2 (SK Beda Identitas)
private fun hasIdentity2Data(suratDetail: SuratDetail): Boolean {
    val dataIdentitas2 = suratDetail.dataIdentitas2 ?: return false
    return listOf(
        dataIdentitas2.nama2,
        dataIdentitas2.nomor2,
        dataIdentitas2.tanggalLahir2,
        dataIdentitas2.tempatLahir2,
        dataIdentitas2.alamat2,
        dataIdentitas2.tercantumId2
    ).any { !it.isNullOrBlank() }
}

// Data Perbedaan (SK Beda Identitas)
private fun hasDifferenceData(suratDetail: SuratDetail): Boolean {
    val dataPerbedaan = suratDetail.dataPerbedaan ?: return false
    return !dataPerbedaan.perbedaanId.isNullOrBlank()
}

// Data Perjalanan (SK Bepergian)
private fun hasTravelData(suratDetail: SuratDetail): Boolean {
    val dataPerjalanan = suratDetail.dataPerjalanan ?: return false
    return listOf(
        dataPerjalanan.tempatTujuan,
        dataPerjalanan.tanggalKeberangkatan,
        dataPerjalanan.satuanLama,
        dataPerjalanan.maksudTujuan
    ).any { !dataPerjalanan.maksudTujuan.isNullOrBlank() }
}

// Data Orang Hilang (SK Ghaib)
private fun hasMissingPersonData(suratDetail: SuratDetail): Boolean {
    val dataOrangHilang = suratDetail.dataOrangHilang ?: return false
    return listOf(
        dataOrangHilang.namaOrangHilang,
        dataOrangHilang.jenisKelamin,
        dataOrangHilang.hilangSejak
    ).any { !dataOrangHilang.hilangSejak.isNullOrBlank() } || dataOrangHilang.usia != null
}

// Data Pekerjaan (SK Izin Tidak Masuk Kerja)
private fun hasJobData(suratDetail: SuratDetail): Boolean {
    val dataPekerjaan = suratDetail.dataPekerjaan ?: return false
    return listOf(
        dataPekerjaan.namaPerusahaan,
        dataPekerjaan.jabatan,
        dataPekerjaan.alasanIzin
    ).any { !dataPekerjaan.alasanIzin.isNullOrBlank() }
}

// Data Izin (SK Izin Tidak Masuk Kerja)
private fun hasPermitData(suratDetail: SuratDetail): Boolean {
    val dataIzin = suratDetail.dataIzin ?: return false
    return listOf(
        dataIzin.alasanIzin,
        dataIzin.terhitungDari,
        dataIzin.maksudTujuan
    ).any { !dataIzin.alasanIzin.isNullOrBlank() }
}

// Data Status (SK Janda/Duda)
private fun hasStatusData(suratDetail: SuratDetail): Boolean {
    val dataStatus = suratDetail.dataStatus ?: return false
    return listOf(
        dataStatus.dasarPengajuan,
        dataStatus.penyebab,
        dataStatus.nomorPengajuan
    ).any { !it.isNullOrBlank() }
}

// Data Bayi (SK Kelahiran)
private fun hasBabyData(suratDetail: SuratDetail): Boolean {
    val dataBayi = suratDetail.dataBayi ?: return false
    return listOf(
        dataBayi.nama,
        dataBayi.jenisKelamin,
        dataBayi.tanggalLahir,
        dataBayi.jamLahir,
        dataBayi.tempatLahir
    ).any { dataBayi.anakKe != null }
}

// Data Ayah (SK Kelahiran)
private fun hasFatherData(suratDetail: SuratDetail): Boolean {
    val dataAyah = suratDetail.dataAyah ?: return false
    return listOf(
        dataAyah.nikAyah,
        dataAyah.namaAyah,
        dataAyah.tanggalLahirAyah,
        dataAyah.tempatLahirAyah,
        dataAyah.alamatAyah
    ).any { !it.isNullOrBlank() }
}

// Data Ibu (SK Kelahiran)
private fun hasMotherData(suratDetail: SuratDetail): Boolean {
    val dataIbu = suratDetail.dataIbu ?: return false
    return listOf(
        dataIbu.nikIbu,
        dataIbu.namaIbu,
        dataIbu.tanggalLahirIbu,
        dataIbu.tempatLahirIbu,
        dataIbu.alamatIbu
    ).any { !it.isNullOrBlank() }
}

// Data Almarhum (SK Kematian)
private fun hasDeceasedData(suratDetail: SuratDetail): Boolean {
    val dataAlmarhum = suratDetail.dataAlmarhum ?: return false
    return listOf(
        dataAlmarhum.namaMendiang,
        dataAlmarhum.nikMendiang,
        dataAlmarhum.jenisKelaminMendiang,
        dataAlmarhum.tanggalLahirMendiang,
        dataAlmarhum.tempatLahirMendiang,
        dataAlmarhum.alamatMendiang,
        dataAlmarhum.tanggalMeninggal,
        dataAlmarhum.hariMeninggal,
        dataAlmarhum.tempatMeninggal,
        dataAlmarhum.sebabMeninggal
    ).any { !it.isNullOrBlank() }
}

// Data Orang Tua (SK Penghasilan)
private fun hasParentData(suratDetail: SuratDetail): Boolean {
    val dataOrangTua = suratDetail.dataOrangTua ?: return false
    return listOf(
        dataOrangTua.nikOrtu,
        dataOrangTua.namaOrtu,
        dataOrangTua.jenisKelaminOrtu,
        dataOrangTua.tanggalLahirOrtu,
        dataOrangTua.tempatLahirOrtu,
        dataOrangTua.alamatOrtu,
        dataOrangTua.pekerjaanOrtu,
        dataOrangTua.penghasilanOrtu,
        dataOrangTua.tanggunganOrtu
    ).any { !it.isNullOrBlank() }
}

// Data Anak (SK Penghasilan)
private fun hasChildData(suratDetail: SuratDetail): Boolean {
    val dataAnak = suratDetail.dataAnak ?: return false
    return listOf(
        dataAnak.nikAnak,
        dataAnak.namaAnak,
        dataAnak.jenisKelaminAnak,
        dataAnak.tanggalLahirAnak,
        dataAnak.tempatLahirAnak,
        dataAnak.namaSekolahAnak,
        dataAnak.kelasAnak
    ).any { !it.isNullOrBlank() }
}

// Data Usaha (SK Usaha)
private fun hasBusinessData(suratDetail: SuratDetail): Boolean {
    val dataUsaha = suratDetail.dataUsaha ?: return false
    return listOf(
        dataUsaha.namaUsaha,
        dataUsaha.alamatUsaha,
        dataUsaha.bidangUsahaId,
        dataUsaha.jenisUsahaId,
        dataUsaha.npwp,
    ).any { !dataUsaha.namaUsaha.isNullOrBlank() }
}

// Data Perusahaan (SK Perusahaan)
private fun hasCompanyData(suratDetail: SuratDetail): Boolean {
    val dataUsaha = suratDetail.dataPerusahaan ?: return false
    return listOf(
        dataUsaha.namaPerusahaan,
        dataUsaha.alamatPerusahaan,
        dataUsaha.bidangUsahaId,
        dataUsaha.jenisUsahaId,
        dataUsaha.noAkta,
        dataUsaha.npwp,
        dataUsaha.jumlahKaryawan,
        dataUsaha.luasBangunan,
        dataUsaha.luasTanah,
        dataUsaha.peruntukanBangunan,
        dataUsaha.statusKepemilikan,
        dataUsaha.nib
    ).any { !dataUsaha.npwp.isNullOrBlank() } ||
            dataUsaha.luasTanah != 0 || dataUsaha.luasBangunan != 0
}

// Helper untuk mengecek keperluan
private fun hasPurposeData(suratDetail: SuratDetail): Boolean {
    return !suratDetail.keperluan.isNullOrBlank()
}

// Helper untuk mengecek hubungan (SK Ghaib dan SK Kehilangan)
private fun hasRelationshipData(suratDetail: SuratDetail): Boolean {
    return !suratDetail.hubunganId.isNullOrBlank()
}
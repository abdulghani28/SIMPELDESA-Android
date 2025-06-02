package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.main.presentation.components.SuratHeaderCard
import com.cvindosistem.simpeldesa.main.presentation.components.SuratSectionCard
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasApplicantData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasAuthorityData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasBabyData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasBusinessData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasChildData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasCompanyData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasDeceasedData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasDifferenceData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasEventData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasFatherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasGrantorData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasHusbandData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasHusbandFatherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasHusbandMotherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasIdentity1Data
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasIdentity2Data
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasJobData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasLostItemData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasMarriageData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasMissingPersonData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasMotherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasParentData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasPermitData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasPersonalData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasPurposeData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasReceiverData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasRelationshipData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasRelocationData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasStatusData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasTaskAssignerData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasTaskData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasTaskReceiverData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasTravelData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasWifeData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasWifeFatherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.helper.hasWifeMotherData
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.ApplicantDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.AuthorityDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.BabyDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.BusinessDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.ChildDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.CompanyDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.DeceasedDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.DifferenceDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.EventDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.FatherDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.GrantorDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.HusbandDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.HusbandFatherDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.HusbandMotherDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.Identity1DataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.Identity2DataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.JobDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.LostItemDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.MarriageDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.MissingPersonDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.MotherDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.ParentDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.PermitDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.PersonalDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.PurposeDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.ReceiverDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.RelationshipDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.RelocationDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.StatusDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.TaskAssignerDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.TaskDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.TaskReceiverDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.TravelDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.WifeDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.WifeFatherDataSection
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.detail.section.WifeMotherDataSection
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
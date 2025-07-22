package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppermohonanpenerbitanbukupaslintasbatas

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppStepAnimatedContent
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.ErrorDialog
import com.cvindosistem.simpeldesa.core.components.LoadingScreen
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.permohonanpenerbitanbuku.SPPermohonanPenerbitanBukuPasLintasBatasViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPPermohonanPenerbitanBukuPasLintasBatasScreen(
    spPermohonanPenerbitanBukuPasLintasBatasViewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spPermohonanPenerbitanBukuPasLintasBatasViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spPermohonanPenerbitanBukuPasLintasBatasViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spPermohonanPenerbitanBukuPasLintasBatasViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spPermohonanPenerbitanBukuPasLintasBatasViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spPermohonanPenerbitanBukuPasLintasBatasViewModel.hasFormData() } }
    val totalSteps = 2

    var showSuccessDialog by remember { mutableStateOf(false) }
    var successDialogTitle by remember { mutableStateOf("") }
    var successDialogMessage by remember { mutableStateOf("") }

    // State untuk dialog dan snackbar
    var showBackWarningDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorDialogTitle by remember { mutableStateOf("") }
    var errorDialogMessage by remember { mutableStateOf("") }

    // State untuk snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back button press dari sistem
    BackHandler(enabled = hasFormData) {
        if (hasFormData) {
            showBackWarningDialog = true
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(spPermohonanPenerbitanBukuPasLintasBatasViewModel.spPermohonanPenerbitanBukuPasLintasBatasEvent) {
        spPermohonanPenerbitanBukuPasLintasBatasViewModel.spPermohonanPenerbitanBukuPasLintasBatasEvent.collect { event ->
            when (event) {
                is SPPermohonanPenerbitanBukuPasLintasBatasViewModel.SPPermohonanPenerbitanBukuPasLintasBatasEvent.SubmitSuccess -> {

                    successDialogTitle = "Berhasil"
                    successDialogMessage = "Surat berhasil diajukan"
                    showSuccessDialog = true
                    delay(1500)
                    showSuccessDialog = false

                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = false
                        }
                    }
                }
                is SPPermohonanPenerbitanBukuPasLintasBatasViewModel.SPPermohonanPenerbitanBukuPasLintasBatasEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPPermohonanPenerbitanBukuPasLintasBatasViewModel.SPPermohonanPenerbitanBukuPasLintasBatasEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPPermohonanPenerbitanBukuPasLintasBatasViewModel.SPPermohonanPenerbitanBukuPasLintasBatasEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPPermohonanPenerbitanBukuPasLintasBatasViewModel.SPPermohonanPenerbitanBukuPasLintasBatasEvent.StepChanged -> {
                    // Optional: Show step change feedback
                    snackbarHostState.showSnackbar(
                        message = "Beralih ke langkah ${event.step}",
                        duration = SnackbarDuration.Short
                    )
                }
                else -> { /* Handle other events */ }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                AppTopBar(
                    title = "Surat Keterangan Wali Hakim",
                    showBackButton = true,
                    onBackClick = {
                        if (hasFormData) {
                            showBackWarningDialog = true
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = {
                    spPermohonanPenerbitanBukuPasLintasBatasViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spPermohonanPenerbitanBukuPasLintasBatasViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spPermohonanPenerbitanBukuPasLintasBatasViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spPermohonanPenerbitanBukuPasLintasBatasViewModel.showConfirmationDialog() }
                } else null
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AppStepAnimatedContent(
                currentStep = currentStep,
                modifier = Modifier.fillMaxSize()
            ) { step ->
                when (step) {
                    1 -> SPPermohonanPenerbitanBukuPasLintasBatas1Content(
                        viewModel = spPermohonanPenerbitanBukuPasLintasBatasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SPPermohonanPenerbitanBukuPasLintasBatas2Content(
                        viewModel = spPermohonanPenerbitanBukuPasLintasBatasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SPPermohonanPenerbitanBukuPasLintasBatas3Content(
                        viewModel = spPermohonanPenerbitanBukuPasLintasBatasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SPPermohonanPenerbitanBukuPasLintasBatas4Content(
                        viewModel = spPermohonanPenerbitanBukuPasLintasBatasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spPermohonanPenerbitanBukuPasLintasBatasViewModel,
                    onDismiss = {
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.dismissPreview()
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.dismissConfirmationDialog()
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.showPreview()
                    }
                )
            }

            // Back Warning Dialog
            if (showBackWarningDialog) {
                BackWarningDialog(
                    onConfirm = {
                        showBackWarningDialog = false
                        navController.popBackStack()
                    },
                    onDismiss = {
                        showBackWarningDialog = false
                    }
                )
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text(text = successDialogTitle) },
                    text = { Text(text = successDialogMessage) },
                    confirmButton = {}
                )
            }

            // Error Dialog
            if (showErrorDialog) {
                ErrorDialog(
                    title = errorDialogTitle,
                    message = errorDialogMessage,
                    onDismiss = {
                        showErrorDialog = false
                        spPermohonanPenerbitanBukuPasLintasBatasViewModel.clearError()
                    }
                )
            }

            // Loading Overlay
            if (isLoading) {
                LoadingScreen()
            }
        }
    }
}

@Composable
private fun PreviewDialog(
    viewModel: SPPermohonanPenerbitanBukuPasLintasBatasViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    BaseDialog(
        title = "Preview Data",
        onDismiss = onDismiss,
        onSubmit = onSubmit,
        submitText = "Ajukan Sekarang",
        dismissText = "Tutup"
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Step 1 - Informasi Pemohon
            item {
                PreviewSection(
                    title = "Informasi Pemohon",
                    content = {
                        PreviewItem("NIK", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminValue)
                        PreviewItem("Agama", viewModel.agamaIdValue)
                        PreviewItem("Status Perkawinan", viewModel.statusKawinIdValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanValue)
                    }
                )
            }

            // Step 2 - Alamat & Keluarga
            item {
                PreviewSection(
                    title = "Alamat & Data Keluarga",
                    content = {
                        PreviewItem("Alamat", viewModel.alamatValue)
                        PreviewItem("Nomor KK", viewModel.noKkValue)
                        PreviewItem("Kepala Keluarga", viewModel.kepalaKeluargaValue)
                    }
                )
            }

            // Step 3 - Anggota Keluarga yang Ikut
            if (viewModel.anggotaKeluargaValue.isNotEmpty()) {
                item {
                    PreviewSection(
                        title = "Anggota Keluarga yang Ikut",
                        content = {
                            viewModel.anggotaKeluargaValue.forEachIndexed { index, anggota ->
                                Column(modifier = Modifier.padding(bottom = 12.dp)) {
                                    Text("Anggota ${index + 1}", style = MaterialTheme.typography.bodySmall)
                                    PreviewItem("NIK", anggota.nik)
                                    PreviewItem("Keterangan", anggota.keterangan)
                                }
                            }
                        }
                    )
                }
            }

            // Step 4 - Keperluan
            item {
                PreviewSection(
                    title = "Informasi Pengajuan",
                    content = {
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkepemilikankendaraan

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.kepemilikankendaraan.SKKepemilikanKendaraanViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKKepemilikanKendaraanScreen(
    skKepemilikanKendaraanViewModel: SKKepemilikanKendaraanViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skKepemilikanKendaraanViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skKepemilikanKendaraanViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skKepemilikanKendaraanViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skKepemilikanKendaraanViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skKepemilikanKendaraanViewModel.hasFormData() } }
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

    LaunchedEffect(skKepemilikanKendaraanViewModel.skKepemilikanKendaraanEvent) {
        skKepemilikanKendaraanViewModel.skKepemilikanKendaraanEvent.collect { event ->
            when (event) {
                is SKKepemilikanKendaraanViewModel.SKKepemilikanKendaraanEvent.SubmitSuccess -> {

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
                is SKKepemilikanKendaraanViewModel.SKKepemilikanKendaraanEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKKepemilikanKendaraanViewModel.SKKepemilikanKendaraanEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKKepemilikanKendaraanViewModel.SKKepemilikanKendaraanEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKKepemilikanKendaraanViewModel.SKKepemilikanKendaraanEvent.StepChanged -> {
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
                    title = "SK Pergi Kawin",
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
                    skKepemilikanKendaraanViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skKepemilikanKendaraanViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skKepemilikanKendaraanViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skKepemilikanKendaraanViewModel.showConfirmationDialog() }
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
                    1 -> SKKepemilikanKendaraan1Content(
                        viewModel = skKepemilikanKendaraanViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKKepemilikanKendaraan2Content(
                        viewModel = skKepemilikanKendaraanViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skKepemilikanKendaraanViewModel,
                    onDismiss = {
                        skKepemilikanKendaraanViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skKepemilikanKendaraanViewModel.dismissPreview()
                        skKepemilikanKendaraanViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skKepemilikanKendaraanViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skKepemilikanKendaraanViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skKepemilikanKendaraanViewModel.dismissConfirmationDialog()
                        skKepemilikanKendaraanViewModel.showPreview()
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
                        skKepemilikanKendaraanViewModel.clearError()
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
    viewModel: SKKepemilikanKendaraanViewModel,
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
            item {
                PreviewSection(
                    title = "Informasi Pemilik",
                    content = {
                        PreviewItem("Nomor Induk Kependudukan (NIK)", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Kendaraan",
                    content = {
                        PreviewItem("Merk", viewModel.merkValue)
                        PreviewItem("Tahun Pembuatan", viewModel.tahunPembuatanValue)
                        PreviewItem("Warna", viewModel.warnaValue)
                        PreviewItem("Nomor Polisi", viewModel.nomorPolisiValue)
                        PreviewItem("Nomor Mesin", viewModel.nomorMesinValue)
                        PreviewItem("Nomor Rangka", viewModel.nomorRangkaValue)
                        PreviewItem("Nomor BPKB", viewModel.nomorBpkbValue)
                        PreviewItem("Bahan Bakar", viewModel.bahanBakarValue)
                        PreviewItem("Isi Silinder", viewModel.isiSilinderValue)
                        PreviewItem("Atas Nama", viewModel.atasNamaValue)
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

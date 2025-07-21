package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmaktalahir

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.aktalahir.SPMAktaLahirViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPMAktaLahirScreen(
    spmAktaLahirViewModel: SPMAktaLahirViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spmAktaLahirViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spmAktaLahirViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spmAktaLahirViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spmAktaLahirViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spmAktaLahirViewModel.hasFormData() } }
    val totalSteps = 4

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

    LaunchedEffect(spmAktaLahirViewModel.spmAktaLahirEvent) {
        spmAktaLahirViewModel.spmAktaLahirEvent.collect { event ->
            when (event) {
                is SPMAktaLahirViewModel.SPMAktaLahirEvent.SubmitSuccess -> {

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
                is SPMAktaLahirViewModel.SPMAktaLahirEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMAktaLahirViewModel.SPMAktaLahirEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMAktaLahirViewModel.SPMAktaLahirEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPMAktaLahirViewModel.SPMAktaLahirEvent.StepChanged -> {
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
                    title = "Surat Permohonan Akta Lahir",
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
                    spmAktaLahirViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spmAktaLahirViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spmAktaLahirViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spmAktaLahirViewModel.showConfirmationDialog() }
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
                    1 -> SPMAktaLahir1Content(
                        viewModel = spmAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SPMAktaLahir2Content(
                        viewModel = spmAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SPMAktaLahir3Content(
                        viewModel = spmAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SPMAktaLahir4Content(
                        viewModel = spmAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spmAktaLahirViewModel,
                    onDismiss = {
                        spmAktaLahirViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spmAktaLahirViewModel.dismissPreview()
                        spmAktaLahirViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spmAktaLahirViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spmAktaLahirViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spmAktaLahirViewModel.dismissConfirmationDialog()
                        spmAktaLahirViewModel.showPreview()
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
                        spmAktaLahirViewModel.clearError()
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
    viewModel: SPMAktaLahirViewModel,
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
            // Step 1: Informasi Pelapor
            item {
                PreviewSection(
                    title = "Informasi Pelapor",
                    content = {
                        PreviewItem("Nomor Induk Kependudukan (NIK)", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                    }
                )
            }

            // Step 2: Informasi Anak
            item {
                PreviewSection(
                    title = "Informasi Anak",
                    content = {
                        PreviewItem("Nama Anak", viewModel.namaAnakValue)
                        PreviewItem("Tempat Lahir Anak", viewModel.tempatLahirAnakValue)
                        PreviewItem("Tanggal Lahir Anak", viewModel.tanggalLahirAnakValue)
                        PreviewItem("Alamat Anak", viewModel.alamatAnakValue)
                    }
                )
            }

            // Step 3: Informasi Orang Tua
            item {
                PreviewSection(
                    title = "Informasi Orang Tua",
                    content = {
                        PreviewItem("Nama Ayah", viewModel.namaAyahValue)
                        PreviewItem("NIK Ayah", viewModel.nikAyahValue)
                        PreviewItem("Nama Ibu", viewModel.namaIbuValue)
                        PreviewItem("NIK Ibu", viewModel.nikIbuValue)
                        PreviewItem("Alamat Orang Tua", viewModel.alamatOrangTuaValue)
                    }
                )
            }

            // Step 4: Keperluan
            item {
                PreviewSection(
                    title = "Keperluan Pengajuan",
                    content = {
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

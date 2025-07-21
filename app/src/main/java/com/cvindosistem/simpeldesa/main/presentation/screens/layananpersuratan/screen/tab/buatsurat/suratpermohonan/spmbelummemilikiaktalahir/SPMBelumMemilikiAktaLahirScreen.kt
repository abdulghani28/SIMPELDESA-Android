package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmbelummemilikiaktalahir

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.belummemilikiaktalahir.SPMBelumMemilikiAktaLahirViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPMBelumMemilikiAktaLahirScreen(
    spmBelumMemilikiAktaLahirViewModel: SPMBelumMemilikiAktaLahirViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spmBelumMemilikiAktaLahirViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spmBelumMemilikiAktaLahirViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spmBelumMemilikiAktaLahirViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spmBelumMemilikiAktaLahirViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spmBelumMemilikiAktaLahirViewModel.hasFormData() } }
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

    LaunchedEffect(spmBelumMemilikiAktaLahirViewModel.spmBelumMemilikiAktaLahirEvent) {
        spmBelumMemilikiAktaLahirViewModel.spmBelumMemilikiAktaLahirEvent.collect { event ->
            when (event) {
                is SPMBelumMemilikiAktaLahirViewModel.SPMBelumMemilikiAktaLahirEvent.SubmitSuccess -> {

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
                is SPMBelumMemilikiAktaLahirViewModel.SPMBelumMemilikiAktaLahirEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMBelumMemilikiAktaLahirViewModel.SPMBelumMemilikiAktaLahirEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMBelumMemilikiAktaLahirViewModel.SPMBelumMemilikiAktaLahirEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPMBelumMemilikiAktaLahirViewModel.SPMBelumMemilikiAktaLahirEvent.StepChanged -> {
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
                    spmBelumMemilikiAktaLahirViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spmBelumMemilikiAktaLahirViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spmBelumMemilikiAktaLahirViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spmBelumMemilikiAktaLahirViewModel.showConfirmationDialog() }
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
                    1 -> SPMBelumMemilikiAktaLahir1Content(
                        viewModel = spmBelumMemilikiAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SPMBelumMemilikiAktaLahir2Content(
                        viewModel = spmBelumMemilikiAktaLahirViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spmBelumMemilikiAktaLahirViewModel,
                    onDismiss = {
                        spmBelumMemilikiAktaLahirViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spmBelumMemilikiAktaLahirViewModel.dismissPreview()
                        spmBelumMemilikiAktaLahirViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spmBelumMemilikiAktaLahirViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spmBelumMemilikiAktaLahirViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spmBelumMemilikiAktaLahirViewModel.dismissConfirmationDialog()
                        spmBelumMemilikiAktaLahirViewModel.showPreview()
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
                        spmBelumMemilikiAktaLahirViewModel.clearError()
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
    viewModel: SPMBelumMemilikiAktaLahirViewModel,
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
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                    }
                )
            }

            // Step 2: Informasi Orang Tua
            item {
                PreviewSection(
                    title = "Informasi Orang Tua",
                    content = {
                        PreviewItem("Nama Ayah", viewModel.namaAyahValue)
                        PreviewItem("NIK Ayah", viewModel.nikAyahValue)
                        PreviewItem("Nama Ibu", viewModel.namaIbuValue)
                        PreviewItem("NIK Ibu", viewModel.nikIbuValue)
                    }
                )
            }
        }
    }
}
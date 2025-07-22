package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpermohonan.spmcerai

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpermohonan.cerai.SPMCeraiViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPMCeraiScreen(
    spmCeraiViewModel: SPMCeraiViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spmCeraiViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spmCeraiViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spmCeraiViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spmCeraiViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spmCeraiViewModel.hasFormData() } }
    val totalSteps = 3

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

    LaunchedEffect(spmCeraiViewModel.spmCeraiEvent) {
        spmCeraiViewModel.spmCeraiEvent.collect { event ->
            when (event) {
                is SPMCeraiViewModel.SPMCeraiEvent.SubmitSuccess -> {

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
                is SPMCeraiViewModel.SPMCeraiEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMCeraiViewModel.SPMCeraiEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPMCeraiViewModel.SPMCeraiEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPMCeraiViewModel.SPMCeraiEvent.StepChanged -> {
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
                    title = "Surat Permohonan Cerai",
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
                    spmCeraiViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spmCeraiViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spmCeraiViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spmCeraiViewModel.showConfirmationDialog() }
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
                    1 -> SPMCerai1Content(
                        viewModel = spmCeraiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SPMCerai2Content(
                        viewModel = spmCeraiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SPMCerai3Content(
                        viewModel = spmCeraiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spmCeraiViewModel,
                    onDismiss = {
                        spmCeraiViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spmCeraiViewModel.dismissPreview()
                        spmCeraiViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spmCeraiViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spmCeraiViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spmCeraiViewModel.dismissConfirmationDialog()
                        spmCeraiViewModel.showPreview()
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
                        spmCeraiViewModel.clearError()
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
    viewModel: SPMCeraiViewModel,
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

            // Step 1 - Data Suami
            item {
                PreviewSection(
                    title = "Data Suami",
                    content = {
                        PreviewItem("NIK Suami", viewModel.nikSuamiValue)
                        PreviewItem("Nama Suami", viewModel.namaSuamiValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirSuamiValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirSuamiValue)
                        PreviewItem("Agama", viewModel.agamaIdSuamiValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanSuamiValue)
                        PreviewItem("Alamat", viewModel.alamatSuamiValue)
                    }
                )
            }

            // Step 2 - Data Istri
            item {
                PreviewSection(
                    title = "Data Istri",
                    content = {
                        PreviewItem("NIK Istri", viewModel.nikIstriValue)
                        PreviewItem("Nama Istri", viewModel.namaIstriValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirIstriValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirIstriValue)
                        PreviewItem("Agama", viewModel.agamaIdIstriValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanIstriValue)
                        PreviewItem("Alamat", viewModel.alamatIstriValue)
                    }
                )
            }

            // Step 3 - Informasi Pelengkap
            item {
                PreviewSection(
                    title = "Informasi Pelengkap",
                    content = {
                        PreviewItem("Sebab Cerai", viewModel.sebabCeraiValue)
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

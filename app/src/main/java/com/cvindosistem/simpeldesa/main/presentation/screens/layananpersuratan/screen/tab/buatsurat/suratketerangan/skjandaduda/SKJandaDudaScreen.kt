package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjandaduda

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jandaduda.SKJandaDudaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKJandaDudaScreen(
    skJandaDudaViewModel: SKJandaDudaViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skJandaDudaViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skJandaDudaViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skJandaDudaViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skJandaDudaViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skJandaDudaViewModel.hasFormData() } }
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

    LaunchedEffect(skJandaDudaViewModel.skJandaDudaEvent) {
        skJandaDudaViewModel.skJandaDudaEvent.collect { event ->
            when (event) {
                is SKJandaDudaViewModel.SKJandaDudaEvent.SubmitSuccess -> {

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
                is SKJandaDudaViewModel.SKJandaDudaEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKJandaDudaViewModel.SKJandaDudaEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKJandaDudaViewModel.SKJandaDudaEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKJandaDudaViewModel.SKJandaDudaEvent.StepChanged -> {
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
                    title = "Surat Keterangan Janda Duda",
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
                    skJandaDudaViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skJandaDudaViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skJandaDudaViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skJandaDudaViewModel.showConfirmationDialog() }
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
                    1 -> SKJandaDuda1Content(
                        viewModel = skJandaDudaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKJandaDuda2Content(
                        viewModel = skJandaDudaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKJandaDuda3Content(
                        viewModel = skJandaDudaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skJandaDudaViewModel,
                    onDismiss = {
                        skJandaDudaViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skJandaDudaViewModel.dismissPreview()
                        skJandaDudaViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skJandaDudaViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skJandaDudaViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skJandaDudaViewModel.dismissConfirmationDialog()
                        skJandaDudaViewModel.showPreview()
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
                        skJandaDudaViewModel.clearError()
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
    viewModel: SKJandaDudaViewModel,
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
                    title = "Informasi Pelapor",
                    content = {
                        PreviewItem("NIK", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminValue)
                        PreviewItem("Agama", viewModel.agamaIdValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat Lengkap", viewModel.alamatValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Janda/Duda",
                    content = {
                        PreviewItem("Dasar Pengajuan", viewModel.dasarPengajuanValue)
                        PreviewItem("Nomor Pengajuan", viewModel.nomorPengajuanValue)
                        PreviewItem("Penyebab", viewModel.penyebabValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Pelengkap",
                    content = {
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

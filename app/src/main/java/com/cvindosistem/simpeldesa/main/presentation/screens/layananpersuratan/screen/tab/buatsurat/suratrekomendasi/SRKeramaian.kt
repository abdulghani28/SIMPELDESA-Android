package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratrekomendasi

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.SRKeramaianViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SRKeramaianScreen(
    srKeramaianViewModel: SRKeramaianViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { srKeramaianViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { srKeramaianViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { srKeramaianViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { srKeramaianViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { srKeramaianViewModel.hasFormData() } }
    val totalSteps = 2

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

    LaunchedEffect(Unit) {
        srKeramaianViewModel.keramaianEvent.collect { event ->
            when (event) {
                is SRKeramaianViewModel.SRKeramaianEvent.SubmitSuccess -> {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = false
                        }
                    }
                }
                is SRKeramaianViewModel.SRKeramaianEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SRKeramaianViewModel.SRKeramaianEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SRKeramaianViewModel.SRKeramaianEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SRKeramaianViewModel.SRKeramaianEvent.StepChanged -> {
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
                    title = "SK BedaIdentitas",
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
                    srKeramaianViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { srKeramaianViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { srKeramaianViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { srKeramaianViewModel.showConfirmationDialog() }
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
                    1 -> SRKeramaian1Content(
                        viewModel = srKeramaianViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SRKeramaian2Content(
                        viewModel = srKeramaianViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = srKeramaianViewModel,
                    onDismiss = {
                        srKeramaianViewModel.dismissPreview()
                    },
                    onSubmit = {
                        srKeramaianViewModel.dismissPreview()
                        srKeramaianViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        srKeramaianViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        srKeramaianViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        srKeramaianViewModel.dismissConfirmationDialog()
                        srKeramaianViewModel.showPreview()
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

            // Error Dialog
            if (showErrorDialog) {
                ErrorDialog(
                    title = errorDialogTitle,
                    message = errorDialogMessage,
                    onDismiss = {
                        showErrorDialog = false
                        srKeramaianViewModel.clearError()
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
    viewModel: SRKeramaianViewModel,
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
                        PreviewItem("Tanggal Lahir", dateFormatterToApiFormat(viewModel.tanggalLahirValue))
                        PreviewItem("Jenis Kelamin", viewModel.selectedGender)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                    }
                )
            }
            item {
                PreviewSection(
                    title = "Informasi Kegiatan",
                    content = {
                        PreviewItem("Nama Acara", viewModel.namaAcaraValue)
                        PreviewItem("Tempat Acara", viewModel.tempatAcaraValue)
                        PreviewItem("Hari", viewModel.hariValue)
                        PreviewItem("Tanggal", dateFormatterToApiFormat(viewModel.tanggalValue))
                        PreviewItem("Jam Mulai", viewModel.jamMulaiValue)
                        PreviewItem("Jam Selesai", viewModel.jamSelesaiValue)
                        PreviewItem("Penanggung Jawab", viewModel.penanggungJawabValue)
                        PreviewItem("Kontak", viewModel.kontakValue)
                    }
                )
            }
        }
    }
}

package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbedaidentitas

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.bedaidentitas.SKBedaIdentitasViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKBedaIdentitasScreen(
    skBedaIdentitasViewModel: SKBedaIdentitasViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skBedaIdentitasViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skBedaIdentitasViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skBedaIdentitasViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skBedaIdentitasViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skBedaIdentitasViewModel.hasFormData() } }
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

    LaunchedEffect(skBedaIdentitasViewModel.skBedaIdentitasEvent) {
        skBedaIdentitasViewModel.skBedaIdentitasEvent.collect { event ->
            when (event) {
                is SKBedaIdentitasViewModel.SKBedaIdentitasEvent.SubmitSuccess -> {

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
                is SKBedaIdentitasViewModel.SKBedaIdentitasEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKBedaIdentitasViewModel.SKBedaIdentitasEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKBedaIdentitasViewModel.SKBedaIdentitasEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKBedaIdentitasViewModel.SKBedaIdentitasEvent.StepChanged -> {
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
                    title = "Surat Keterangan Beda Identitas",
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
                    skBedaIdentitasViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skBedaIdentitasViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skBedaIdentitasViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skBedaIdentitasViewModel.showConfirmationDialog() }
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
                    1 -> SKBedaIdentitas1Content(
                        viewModel = skBedaIdentitasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKBedaIdentitas2Content(
                        viewModel = skBedaIdentitasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKBedaIdentitas3Content(
                        viewModel = skBedaIdentitasViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skBedaIdentitasViewModel,
                    onDismiss = {
                        skBedaIdentitasViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skBedaIdentitasViewModel.dismissPreview()
                        skBedaIdentitasViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skBedaIdentitasViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skBedaIdentitasViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skBedaIdentitasViewModel.dismissConfirmationDialog()
                        skBedaIdentitasViewModel.showPreview()
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
                        skBedaIdentitasViewModel.clearError()
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
    viewModel: SKBedaIdentitasViewModel,
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
            // Identitas Pertama
            item {
                PreviewSection(
                    title = "Identitas Pertama",
                    content = {
                        PreviewItem("Nama", viewModel.nama1Value)
                        PreviewItem("Nomor Identitas", viewModel.nomor1Value)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahir1Value)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahir1Value)
                        PreviewItem("Alamat", viewModel.alamat1Value)
                        PreviewItem("Tercantum dalam", viewModel.tercantumId1Value)
                    }
                )
            }

            // Identitas Kedua
            item {
                PreviewSection(
                    title = "Identitas Kedua",
                    content = {
                        PreviewItem("Nama", viewModel.nama2Value)
                        PreviewItem("Nomor Identitas", viewModel.nomor2Value)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahir2Value)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahir2Value)
                        PreviewItem("Alamat", viewModel.alamat2Value)
                        PreviewItem("Tercantum dalam", viewModel.tercantumId2Value)
                    }
                )
            }

            // Perbedaan & Keperluan
            item {
                PreviewSection(
                    title = "Perbedaan dan Keperluan",
                    content = {
                        PreviewItem("Perbedaan Identitas", viewModel.perbedaanIdValue)
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}
package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkelahiran

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKelahiranViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKKelahiranScreen(
    skKelahiranViewModel: SKKelahiranViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skKelahiranViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skKelahiranViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skKelahiranViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skKelahiranViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skKelahiranViewModel.hasFormData() } }
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

    LaunchedEffect(skKelahiranViewModel.skKelahiranEvent) {
        skKelahiranViewModel.skKelahiranEvent.collect { event ->
            when (event) {
                is SKKelahiranViewModel.SKKelahiranEvent.SubmitSuccess -> {

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
                is SKKelahiranViewModel.SKKelahiranEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKKelahiranViewModel.SKKelahiranEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKKelahiranViewModel.SKKelahiranEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKKelahiranViewModel.SKKelahiranEvent.StepChanged -> {
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
                    title = "SK Tidak Masuk Kerja",
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
                    skKelahiranViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skKelahiranViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skKelahiranViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skKelahiranViewModel.showConfirmationDialog() }
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
                    1 -> SKKelahiran1Content(
                        viewModel = skKelahiranViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKKelahiran2Content(
                        viewModel = skKelahiranViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKKelahiran3Content(
                        viewModel = skKelahiranViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SKKelahiran4Content(
                        viewModel = skKelahiranViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skKelahiranViewModel,
                    onDismiss = {
                        skKelahiranViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skKelahiranViewModel.dismissPreview()
                        skKelahiranViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skKelahiranViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skKelahiranViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skKelahiranViewModel.dismissConfirmationDialog()
                        skKelahiranViewModel.showPreview()
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
                        skKelahiranViewModel.clearError()
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
    viewModel: SKKelahiranViewModel,
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
                    title = "Informasi Anak",
                    content = {
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                        PreviewItem("Jam Lahir", viewModel.jamLahirValue)
                        PreviewItem("Anak Ke", viewModel.anakKeValue.toString())
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Ayah",
                    content = {
                        PreviewItem("Nama Lengkap", viewModel.namaAyahValue)
                        PreviewItem("NIK", viewModel.nikAyahValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirAyahValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirAyahValue)
                        PreviewItem("Alamat", viewModel.alamatAyahValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Ibu",
                    content = {
                        PreviewItem("Nama Lengkap", viewModel.namaIbuValue)
                        PreviewItem("NIK", viewModel.nikIbuValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirIbuValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirIbuValue)
                        PreviewItem("Alamat", viewModel.alamatIbuValue)
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

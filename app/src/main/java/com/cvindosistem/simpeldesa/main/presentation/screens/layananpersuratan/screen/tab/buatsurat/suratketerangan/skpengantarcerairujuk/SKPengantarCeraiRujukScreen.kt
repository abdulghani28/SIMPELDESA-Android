package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpengantarcerairujuk

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.pengantarcerairujuk.SKPengantarCeraiRujukViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKPengantarCeraiRujukScreen(
    skPengantarCeraiRujukViewModel: SKPengantarCeraiRujukViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skPengantarCeraiRujukViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skPengantarCeraiRujukViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skPengantarCeraiRujukViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skPengantarCeraiRujukViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skPengantarCeraiRujukViewModel.hasFormData() } }
    val totalSteps = 5

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

    LaunchedEffect(skPengantarCeraiRujukViewModel.skPengantarCeraiRujukEvent) {
        skPengantarCeraiRujukViewModel.skPengantarCeraiRujukEvent.collect { event ->
            when (event) {
                is SKPengantarCeraiRujukViewModel.SKPengantarCeraiRujukEvent.SubmitSuccess -> {

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
                is SKPengantarCeraiRujukViewModel.SKPengantarCeraiRujukEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKPengantarCeraiRujukViewModel.SKPengantarCeraiRujukEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKPengantarCeraiRujukViewModel.SKPengantarCeraiRujukEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKPengantarCeraiRujukViewModel.SKPengantarCeraiRujukEvent.StepChanged -> {
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
                    title = "Surat Keterangan Biodata Warga",
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
                    skPengantarCeraiRujukViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skPengantarCeraiRujukViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skPengantarCeraiRujukViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skPengantarCeraiRujukViewModel.showConfirmationDialog() }
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
                    1 -> SKPengantarCeraiRujuk1Content(
                        viewModel = skPengantarCeraiRujukViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKPengantarCeraiRujuk2Content(
                        viewModel = skPengantarCeraiRujukViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKPengantarCeraiRujuk3Content(
                        viewModel = skPengantarCeraiRujukViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skPengantarCeraiRujukViewModel,
                    onDismiss = {
                        skPengantarCeraiRujukViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skPengantarCeraiRujukViewModel.dismissPreview()
                        skPengantarCeraiRujukViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skPengantarCeraiRujukViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skPengantarCeraiRujukViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skPengantarCeraiRujukViewModel.dismissConfirmationDialog()
                        skPengantarCeraiRujukViewModel.showPreview()
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
                        skPengantarCeraiRujukViewModel.clearError()
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
    viewModel: SKPengantarCeraiRujukViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    BaseDialog(
        title = "Preview Biodata Warga",
        onDismiss = onDismiss,
        onSubmit = onSubmit,
        submitText = "Ajukan Sekarang",
        dismissText = "Tutup"
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Step 1: Data Diri Pemohon
            item {
                PreviewSection(title = "Data Diri Pemohon") {
                    PreviewItem("NIK", viewModel.nikValue)
                    PreviewItem("Nama Lengkap", viewModel.namaValue)
                    PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                    PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                    PreviewItem("Agama", viewModel.agamaIdValue)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanValue)
                }
            }

            // Step 2: Data Orang Tua Pemohon
            item {
                PreviewSection(title = "Data Ayah Pemohon") {
                    PreviewItem("Nama Ayah", viewModel.namaAyahValue)
                    PreviewItem("NIK Ayah", viewModel.nikAyahValue)
                }
            }

            // Step 3: Data Pasangan
            item {
                PreviewSection(title = "Data Pasangan") {
                    PreviewItem("NIK Pasangan", viewModel.nikPasanganValue)
                    PreviewItem("Nama Pasangan", viewModel.namaPasanganValue)
                    PreviewItem("Tempat Lahir", viewModel.tempatLahirPasanganValue)
                    PreviewItem("Tanggal Lahir", viewModel.tanggalLahirPasanganValue)
                    PreviewItem("Agama", viewModel.agamaIdPasanganValue)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanPasanganValue)
                    PreviewItem("Alamat", viewModel.alamatPasanganValue)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanPasanganValue)
                }
            }

            // Step 4: Data Orang Tua Pasangan
            item {
                PreviewSection(title = "Data Ayah Pasangan") {
                    PreviewItem("Nama Ayah Pasangan", viewModel.namaAyahPasanganValue)
                    PreviewItem("NIK Ayah Pasangan", viewModel.nikAyahPasanganValue)
                }
            }

            // Step 5: Keperluan
            item {
                PreviewSection(title = "Keperluan") {
                    PreviewItem("Keperluan", viewModel.keperluanValue)
                }
            }
        }
    }
}
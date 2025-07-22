package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skizinorangtua

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.izinorangtua.SKIzinOrangTuaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKIzinOrangTuaScreen(
    skIzinOrangTuaViewModel: SKIzinOrangTuaViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skIzinOrangTuaViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skIzinOrangTuaViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skIzinOrangTuaViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skIzinOrangTuaViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skIzinOrangTuaViewModel.hasFormData() } }
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

    LaunchedEffect(skIzinOrangTuaViewModel.skIzinOrangTuaEvent) {
        skIzinOrangTuaViewModel.skIzinOrangTuaEvent.collect { event ->
            when (event) {
                is SKIzinOrangTuaViewModel.SKIzinOrangTuaEvent.SubmitSuccess -> {

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
                is SKIzinOrangTuaViewModel.SKIzinOrangTuaEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKIzinOrangTuaViewModel.SKIzinOrangTuaEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKIzinOrangTuaViewModel.SKIzinOrangTuaEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKIzinOrangTuaViewModel.SKIzinOrangTuaEvent.StepChanged -> {
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
                    title = "Surat Keterangan Izin Orang Tua",
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
                    skIzinOrangTuaViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skIzinOrangTuaViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skIzinOrangTuaViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skIzinOrangTuaViewModel.showConfirmationDialog() }
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
                    1 -> SKIzinOrangTua1Content(
                        viewModel = skIzinOrangTuaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKIzinOrangTua2Content(
                        viewModel = skIzinOrangTuaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKIzinOrangTua3Content(
                        viewModel = skIzinOrangTuaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skIzinOrangTuaViewModel,
                    onDismiss = {
                        skIzinOrangTuaViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skIzinOrangTuaViewModel.dismissPreview()
                        skIzinOrangTuaViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skIzinOrangTuaViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skIzinOrangTuaViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skIzinOrangTuaViewModel.dismissConfirmationDialog()
                        skIzinOrangTuaViewModel.showPreview()
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
                        skIzinOrangTuaViewModel.clearError()
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
    viewModel: SKIzinOrangTuaViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    BaseDialog(
        title = "Preview Izin Orang Tua",
        onDismiss = onDismiss,
        onSubmit = onSubmit,
        submitText = "Ajukan Sekarang",
        dismissText = "Tutup"
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Step 1: Informasi Pemberi Izin
            item {
                PreviewSection(title = "Informasi Pemberi Izin") {
                    PreviewItem("Yang Memberi Izin", viewModel.memberiIzinValue)
                    PreviewItem("NIK", viewModel.nikValue)
                    PreviewItem("Nama Lengkap", viewModel.namaValue)
                    PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                    PreviewItem("Tanggal Lahir", viewModel.tanggalLahirValue)
                    PreviewItem("Agama", viewModel.agamaIdValue)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                    PreviewItem("Alamat", viewModel.alamatValue)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanValue)
                }
            }

            // Step 2: Informasi yang Diberi Izin
            item {
                PreviewSection(title = "Informasi yang Diberi Izin") {
                    PreviewItem("Yang Diberi Izin", viewModel.diberiIzinValue)
                    PreviewItem("NIK", viewModel.nik2Value)
                    PreviewItem("Nama Lengkap", viewModel.nama2Value)
                    PreviewItem("Tempat Lahir", viewModel.tempatLahir2Value)
                    PreviewItem("Tanggal Lahir", viewModel.tanggalLahir2Value)
                    PreviewItem("Agama", viewModel.agama2IdValue)
                    PreviewItem("Pekerjaan", viewModel.pekerjaan2Value)
                    PreviewItem("Status Pekerjaan", viewModel.statusPekerjaanValue)
                    PreviewItem("Alamat", viewModel.alamat2Value)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraan2Value)
                }
            }

            // Step 3: Informasi Pelengkap
            item {
                PreviewSection(title = "Informasi Pelengkap") {
                    PreviewItem("Nama Perusahaan", viewModel.namaPerusahaanValue)
                    PreviewItem("Negara Tujuan", viewModel.negaraTujuanValue)
                    PreviewItem("Masa Kontrak", viewModel.masaKontrakValue)
                    PreviewItem("Keperluan", viewModel.keperluanValue)
                }
            }
        }
    }
}

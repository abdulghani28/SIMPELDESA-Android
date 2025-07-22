package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbiodatawarga

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.biodata.SKBiodataWargaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKBiodataWargaScreen(
    skBiodataWargaViewModel: SKBiodataWargaViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skBiodataWargaViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skBiodataWargaViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skBiodataWargaViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skBiodataWargaViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skBiodataWargaViewModel.hasFormData() } }
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

    LaunchedEffect(skBiodataWargaViewModel.skBiodataWargaEvent) {
        skBiodataWargaViewModel.skBiodataWargaEvent.collect { event ->
            when (event) {
                is SKBiodataWargaViewModel.SKBiodataWargaEvent.SubmitSuccess -> {

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
                is SKBiodataWargaViewModel.SKBiodataWargaEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKBiodataWargaViewModel.SKBiodataWargaEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKBiodataWargaViewModel.SKBiodataWargaEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKBiodataWargaViewModel.SKBiodataWargaEvent.StepChanged -> {
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
                    skBiodataWargaViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skBiodataWargaViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skBiodataWargaViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skBiodataWargaViewModel.showConfirmationDialog() }
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
                    1 -> SKBiodataWarga1Content(
                        viewModel = skBiodataWargaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKBiodataWarga2Content(
                        viewModel = skBiodataWargaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKBiodataWarga3Content(
                        viewModel = skBiodataWargaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SKBiodataWarga4Content(
                        viewModel = skBiodataWargaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    5 -> SKBiodataWarga5Content(
                        viewModel = skBiodataWargaViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skBiodataWargaViewModel,
                    onDismiss = {
                        skBiodataWargaViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skBiodataWargaViewModel.dismissPreview()
                        skBiodataWargaViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skBiodataWargaViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skBiodataWargaViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skBiodataWargaViewModel.dismissConfirmationDialog()
                        skBiodataWargaViewModel.showPreview()
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
                        skBiodataWargaViewModel.clearError()
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
    viewModel: SKBiodataWargaViewModel,
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
                    PreviewItem("Golongan Darah", viewModel.golonganDarahValue)
                    PreviewItem("Agama", viewModel.agamaIdValue)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                    PreviewItem("Pendidikan", viewModel.pendidikanIdValue)
                    PreviewItem("Disabilitas", viewModel.disabilitasIdValue)
                }
            }

            // Step 2: Alamat & Status
            item {
                PreviewSection(title = "Alamat & Status") {
                    PreviewItem("Alamat", viewModel.alamatValue)
                    PreviewItem("Status Dalam Keluarga", viewModel.statusValue)
                    PreviewItem("Hubungan Dengan Kepala Keluarga", viewModel.hubunganValue)
                }
            }

            // Step 3: Informasi Perkawinan
            item {
                PreviewSection(title = "Informasi Perkawinan") {
                    PreviewItem("Nomor Akta Kawin", viewModel.aktaKawinValue)
                    PreviewItem("Tanggal Kawin", viewModel.tanggalKawinValue)
                    PreviewItem("Nomor Akta Cerai", viewModel.aktaCeraiValue)
                    PreviewItem("Tanggal Cerai", viewModel.tanggalCeraiValue)
                }
            }

            // Step 4: Data Orang Tua
            item {
                PreviewSection(title = "Data Orang Tua") {
                    PreviewItem("Nama Ayah", viewModel.namaAyahValue)
                    PreviewItem("NIK Ayah", viewModel.nikAyahValue)
                    PreviewItem("Nama Ibu", viewModel.namaIbuValue)
                    PreviewItem("NIK Ibu", viewModel.nikIbuValue)
                    PreviewItem("Nomor Akta Lahir", viewModel.aktaLahirValue)
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

package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpernyataan.spnpenguasaanfisikbidangtanah

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpernyataan.penguasaanfisikbidangtanah.SPNPenguasaanFisikBidangTanahViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPNPenguasaanFisikBidangTanahScreen(
    spnPenguasaanFisikBidangTanahViewModel: SPNPenguasaanFisikBidangTanahViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spnPenguasaanFisikBidangTanahViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spnPenguasaanFisikBidangTanahViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spnPenguasaanFisikBidangTanahViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spnPenguasaanFisikBidangTanahViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spnPenguasaanFisikBidangTanahViewModel.hasFormData() } }
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

    LaunchedEffect(spnPenguasaanFisikBidangTanahViewModel.spnPenguasaanFisikEvent) {
        spnPenguasaanFisikBidangTanahViewModel.spnPenguasaanFisikEvent.collect { event ->
            when (event) {
                is SPNPenguasaanFisikBidangTanahViewModel.SPNPenguasaanFisikEvent.SubmitSuccess -> {

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
                is SPNPenguasaanFisikBidangTanahViewModel.SPNPenguasaanFisikEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPNPenguasaanFisikBidangTanahViewModel.SPNPenguasaanFisikEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPNPenguasaanFisikBidangTanahViewModel.SPNPenguasaanFisikEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPNPenguasaanFisikBidangTanahViewModel.SPNPenguasaanFisikEvent.StepChanged -> {
                    // Optional: Show step change feedback
                    snackbarHostState.showSnackbar(
                        message = "Beralih ke langkah ${event.step}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                AppTopBar(
                    title = "Surat Pernyataan Penguasaan Fisik Bidang Tanah",
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
                    spnPenguasaanFisikBidangTanahViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spnPenguasaanFisikBidangTanahViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spnPenguasaanFisikBidangTanahViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spnPenguasaanFisikBidangTanahViewModel.showConfirmationDialog() }
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
                    1 -> SPNPenguasaanFisikBidangTanah1Content(
                        viewModel = spnPenguasaanFisikBidangTanahViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SPNPenguasaanFisikBidangTanah2Content(
                        viewModel = spnPenguasaanFisikBidangTanahViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SPNPenguasaanFisikBidangTanah3Content(
                        viewModel = spnPenguasaanFisikBidangTanahViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SPNPenguasaanFisikBidangTanah4Content(
                        viewModel = spnPenguasaanFisikBidangTanahViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    5 -> SPNPenguasaanFisikBidangTanah5Content(
                        viewModel = spnPenguasaanFisikBidangTanahViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spnPenguasaanFisikBidangTanahViewModel,
                    onDismiss = {
                        spnPenguasaanFisikBidangTanahViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spnPenguasaanFisikBidangTanahViewModel.dismissPreview()
                        spnPenguasaanFisikBidangTanahViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spnPenguasaanFisikBidangTanahViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spnPenguasaanFisikBidangTanahViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spnPenguasaanFisikBidangTanahViewModel.dismissConfirmationDialog()
                        spnPenguasaanFisikBidangTanahViewModel.showPreview()
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
                        spnPenguasaanFisikBidangTanahViewModel.clearError()
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
    viewModel: SPNPenguasaanFisikBidangTanahViewModel,
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

            // Step 1 – Data Pemohon
            item {
                PreviewSection(
                    title = "Data Pemohon",
                    content = {
                        PreviewItem("NIK", viewModel.nikPemohonValue)
                        PreviewItem("Nama", viewModel.namaPemohonValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirPemohonValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirPemohonValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                    }
                )
            }

            // Step 2 – Lokasi & Identitas Tanah
            item {
                PreviewSection(
                    title = "Lokasi & Identitas Tanah",
                    content = {
                        PreviewItem("Alamat Lengkap", viewModel.alamatValue)
                        PreviewItem("Alamat (1)", viewModel.alamat1Value)
                        PreviewItem("Alamat (2)", viewModel.alamat2Value)
                        PreviewItem("RT / RW", viewModel.rtRwValue)
                        PreviewItem("Jalan", viewModel.jalanValue)
                        PreviewItem("Desa", viewModel.desaValue)
                        PreviewItem("Kecamatan", viewModel.kecamatanValue)
                        PreviewItem("Kabupaten", viewModel.kabupatenValue)
                        PreviewItem("NIB", viewModel.nibValue)
                        PreviewItem("Luas Tanah", viewModel.luasTanahValue)
                        PreviewItem("Status Tanah", viewModel.statusTanahValue)
                        PreviewItem("Dipergunakan Untuk", viewModel.diperggunakanValue)
                    }
                )
            }

            // Step 3 – Perolehan & Batas Tanah
            item {
                PreviewSection(
                    title = "Perolehan & Batas Tanah",
                    content = {
                        PreviewItem("Diperoleh Dari", viewModel.diperolehDariValue)
                        PreviewItem("Dengan Cara", viewModel.diperolehDenganValue)
                        PreviewItem("Sejak", viewModel.diperolehSejakValue)
                        PreviewItem("Batas Utara", viewModel.batasUtaraValue)
                        PreviewItem("Batas Timur", viewModel.batasTimurValue)
                        PreviewItem("Batas Selatan", viewModel.batasSelatanValue)
                        PreviewItem("Batas Barat", viewModel.batasBaratValue)
                    }
                )
            }

            // Step 4 – Data Saksi
            item {
                PreviewSection(
                    title = "Data Saksi 1",
                    content = {
                        PreviewItem("NIK", viewModel.nik1Value)
                        PreviewItem("Nama", viewModel.nama1Value)
                        PreviewItem("Pekerjaan", viewModel.pekerjaan1Value)
                        PreviewItem("Warga Desa Ini", if (viewModel.isSaksi1WargaDesaValue) "Ya" else "Bukan")
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Data Saksi 2",
                    content = {
                        PreviewItem("NIK", viewModel.nik2Value)
                        PreviewItem("Nama", viewModel.nama2Value)
                        PreviewItem("Pekerjaan", viewModel.pekerjaan2Value)
                        PreviewItem("Warga Desa Ini", if (viewModel.isSaksi2WargaDesaValue) "Ya" else "Bukan")
                    }
                )
            }

            // Step 5 – Keperluan
            item {
                PreviewSection(
                    title = "Informasi Pengajuan",
                    content = {
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }
        }
    }
}

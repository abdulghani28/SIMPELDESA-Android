package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppernikahan

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
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPPernikahanViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SPPernikahanScreen(
    spPernikahanViewModel: SPPernikahanViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { spPernikahanViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { spPernikahanViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spPernikahanViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spPernikahanViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spPernikahanViewModel.hasFormData() } }
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

    BackHandler(enabled = hasFormData) {
        if (hasFormData) {
            showBackWarningDialog = true
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(spPernikahanViewModel.pernikahanEvent) {
        spPernikahanViewModel.pernikahanEvent.collect { event ->
            when (event) {
                is SPPernikahanViewModel.SPPernikahanEvent.SubmitSuccess -> {
                    // Tampilkan dialog sukses
                    successDialogTitle = "Berhasil"
                    successDialogMessage = "Surat berhasil diajukan"
                    showSuccessDialog = true

                    // Delay 2 detik kemudian tutup dialog dan navigasi
                    delay(1500)
                    showSuccessDialog = false

                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = false
                        }
                    }
                }
                is SPPernikahanViewModel.SPPernikahanEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPPernikahanViewModel.SPPernikahanEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPPernikahanViewModel.SPPernikahanEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPPernikahanViewModel.SPPernikahanEvent.StepChanged -> {
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
                    title = "SP Pernikahan",
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
                    spPernikahanViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { spPernikahanViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { spPernikahanViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { spPernikahanViewModel.showConfirmationDialog() }
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
                    1 -> SPPernikahan1Content(spPernikahanViewModel)
                    2 -> SPPernikahan2Content(spPernikahanViewModel)
                    3 -> SPPernikahan3Content(spPernikahanViewModel)
                    4 -> SPPernikahan4Content(spPernikahanViewModel)
                    5 -> SPPernikahan5Content(spPernikahanViewModel)
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = spPernikahanViewModel,
                    onDismiss = {
                        spPernikahanViewModel.dismissPreview()
                    },
                    onSubmit = {
                        spPernikahanViewModel.dismissPreview()
                        spPernikahanViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        spPernikahanViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        spPernikahanViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        spPernikahanViewModel.dismissConfirmationDialog()
                        spPernikahanViewModel.showPreview()
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
                        spPernikahanViewModel.clearError()
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
    viewModel: SPPernikahanViewModel,
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
// Step 1 - Calon Suami
            item {
                PreviewSection(
                    title = "Informasi Calon Suami",
                    content = {
                        PreviewItem("NIK", viewModel.nikSuamiValue)
                        PreviewItem("Nama Lengkap", viewModel.namaSuamiValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirSuamiValue)
                        PreviewItem("Tanggal Lahir", dateFormatterToApiFormat(viewModel.tanggalLahirSuamiValue))
                        PreviewItem("Pekerjaan", viewModel.pekerjaanSuamiValue)
                        PreviewItem("Alamat", viewModel.alamatSuamiValue)
                        PreviewItem("Agama", viewModel.agamaSuamiIdValue)
                        PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanSuamiValue)
                        PreviewItem("Jumlah Istri", viewModel.jumlahIstriValue.toString())
                        PreviewItem("Status Perkawinan", viewModel.statusKawinSuamiIdValue)
                        PreviewItem("Nama Istri Sebelumnya", viewModel.namaIstriSebelumnyaValue)
                    }
                )
            }

// Step 2 - Orang Tua Suami
            item {
                PreviewSection(
                    title = "Informasi Orang Tua Calon Suami",
                    content = {
                        PreviewItem("Nama Ayah", viewModel.namaAyahSuamiValue)
                        PreviewItem("NIK Ayah", viewModel.nikAyahSuamiValue)
                        PreviewItem("Tempat Lahir Ayah", viewModel.tempatLahirAyahSuamiValue)
                        PreviewItem("Tanggal Lahir Ayah", dateFormatterToApiFormat(viewModel.tanggalLahirAyahSuamiValue))
                        PreviewItem("Pekerjaan Ayah", viewModel.pekerjaanAyahSuamiValue)
                        PreviewItem("Alamat Ayah", viewModel.alamatAyahSuamiValue)
                        PreviewItem("Agama Ayah", viewModel.agamaAyahSuamiIdValue)
                        PreviewItem("Kewarganegaraan Ayah", viewModel.kewarganegaraanAyahSuamiValue)

                        PreviewItem("Nama Ibu", viewModel.namaIbuSuamiValue)
                        PreviewItem("NIK Ibu", viewModel.nikIbuSuamiValue)
                        PreviewItem("Tempat Lahir Ibu", viewModel.tempatLahirIbuSuamiValue)
                        PreviewItem("Tanggal Lahir Ibu", dateFormatterToApiFormat(viewModel.tanggalLahirIbuSuamiValue))
                        PreviewItem("Pekerjaan Ibu", viewModel.pekerjaanIbuSuamiValue)
                        PreviewItem("Alamat Ibu", viewModel.alamatIbuSuamiValue)
                        PreviewItem("Agama Ibu", viewModel.agamaIbuSuamiIdValue)
                        PreviewItem("Kewarganegaraan Ibu", viewModel.kewarganegaraanIbuSuamiValue)
                    }
                )
            }

// Step 3 - Calon Istri
            item {
                PreviewSection(
                    title = "Informasi Calon Istri",
                    content = {
                        PreviewItem("NIK", viewModel.nikIstriValue)
                        PreviewItem("Nama Lengkap", viewModel.namaIstriValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirIstriValue)
                        PreviewItem("Tanggal Lahir", dateFormatterToApiFormat(viewModel.tanggalLahirIstriValue))
                        PreviewItem("Pekerjaan", viewModel.pekerjaanIstriValue)
                        PreviewItem("Alamat", viewModel.alamatIstriValue)
                        PreviewItem("Agama", viewModel.agamaIstriIdValue)
                        PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanIstriValue)
                        PreviewItem("Status Perkawinan", viewModel.statusKawinIstriIdValue)
                        PreviewItem("Nama Suami Sebelumnya", viewModel.namaSuamiSebelumnyaValue)
                    }
                )
            }

// Step 4 - Orang Tua Istri
            item {
                PreviewSection(
                    title = "Informasi Orang Tua Calon Istri",
                    content = {
                        PreviewItem("Nama Ayah", viewModel.namaAyahIstriValue)
                        PreviewItem("NIK Ayah", viewModel.nikAyahIstriValue)
                        PreviewItem("Tempat Lahir Ayah", viewModel.tempatLahirAyahIstriValue)
                        PreviewItem("Tanggal Lahir Ayah", dateFormatterToApiFormat(viewModel.tanggalLahirAyahIstriValue))
                        PreviewItem("Pekerjaan Ayah", viewModel.pekerjaanAyahIstriValue)
                        PreviewItem("Alamat Ayah", viewModel.alamatAyahIstriValue)
                        PreviewItem("Agama Ayah", viewModel.agamaAyahIstriIdValue)
                        PreviewItem("Kewarganegaraan Ayah", viewModel.kewarganegaraanAyahIstriValue)

                        PreviewItem("Nama Ibu", viewModel.namaIbuIstriValue)
                        PreviewItem("NIK Ibu", viewModel.nikIbuIstriValue)
                        PreviewItem("Tempat Lahir Ibu", viewModel.tempatLahirIbuIstriValue)
                        PreviewItem("Tanggal Lahir Ibu", dateFormatterToApiFormat(viewModel.tanggalLahirIbuIstriValue))
                        PreviewItem("Pekerjaan Ibu", viewModel.pekerjaanIbuIstriValue)
                        PreviewItem("Alamat Ibu", viewModel.alamatIbuIstriValue)
                        PreviewItem("Agama Ibu", viewModel.agamaIbuIstriIdValue)
                        PreviewItem("Kewarganegaraan Ibu", viewModel.kewarganegaraanIbuIstriValue)
                    }
                )
            }

// Step 5 - Informasi Pernikahan
            item {
                PreviewSection(
                    title = "Informasi Pernikahan",
                    content = {
                        PreviewItem("Tempat Pernikahan", viewModel.tempatPernikahanValue)
                        PreviewItem("Hari", viewModel.hariPernikahanValue)
                        PreviewItem("Tanggal Pernikahan", dateFormatterToApiFormat(viewModel.tanggalPernikahanValue))
                        PreviewItem("Jam Pernikahan", viewModel.jamPernikahanValue)
                    }
                )
            }
        }
    }
}

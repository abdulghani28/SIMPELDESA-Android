package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpenghasilan

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKPenghasilanViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKPenghasilanScreen(
    skPenghasilanViewModel: SKPenghasilanViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skPenghasilanViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skPenghasilanViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skPenghasilanViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skPenghasilanViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skPenghasilanViewModel.hasFormData() } }
    val totalSteps = 2

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

    LaunchedEffect(skPenghasilanViewModel.skPenghasilanEvent) {
        skPenghasilanViewModel.skPenghasilanEvent.collect { event ->
            when (event) {
                is SKPenghasilanViewModel.SKPenghasilanEvent.SubmitSuccess -> {

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
                is SKPenghasilanViewModel.SKPenghasilanEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKPenghasilanViewModel.SKPenghasilanEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKPenghasilanViewModel.SKPenghasilanEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKPenghasilanViewModel.SKPenghasilanEvent.StepChanged -> {
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
                    skPenghasilanViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skPenghasilanViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skPenghasilanViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skPenghasilanViewModel.showConfirmationDialog() }
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
                    1 -> SKPenghasilan1Content(
                        viewModel = skPenghasilanViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKPenghasilan2Content(
                        viewModel = skPenghasilanViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKPenghasilan3Content(
                        viewModel = skPenghasilanViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skPenghasilanViewModel,
                    onDismiss = {
                        skPenghasilanViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skPenghasilanViewModel.dismissPreview()
                        skPenghasilanViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skPenghasilanViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skPenghasilanViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skPenghasilanViewModel.dismissConfirmationDialog()
                        skPenghasilanViewModel.showPreview()
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
                        skPenghasilanViewModel.clearError()
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
    viewModel: SKPenghasilanViewModel,
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
                        PreviewItem("Nomor Induk Kependudukan (NIK)", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Alamat Lengkap", viewModel.alamatValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Orang Tua",
                    content = {
                        PreviewItem("NIK Orang Tua", viewModel.nikOrtuValue)
                        PreviewItem("Nama Lengkap", viewModel.namaOrtuValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirOrtuValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirOrtuValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminOrtuValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanOrtuValue)
                        PreviewItem("Penghasilan Rata-rata per Bulan (Rp)", viewModel.penghasilanOrtuValue.toString())
                        PreviewItem("Jumlah Tanggungan Anggota Keluarga", viewModel.tanggunganOrtuValue.toString())
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Anak",
                    content = {
                        PreviewItem("Nomor Induk Kependudukan (NIK)", viewModel.nikAnakValue)
                        PreviewItem("Nama Lengkap", viewModel.namaAnakValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirAnakValue)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahirAnakValue)
                        PreviewItem("Jenis Kelamin", viewModel.jenisKelaminAnakValue)
                        PreviewItem("Nama Sekolah/Universitas", viewModel.namaSekolahAnakValue)
                        PreviewItem("Kelas/Semester", viewModel.kelasAnakValue)
                    }
                )
            }
        }
    }
}

package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sklahirmati

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.lahirmati.SKLahirMatiViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKLahirMatiScreen(
    skLahirMatiViewModel: SKLahirMatiViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skLahirMatiViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skLahirMatiViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skLahirMatiViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skLahirMatiViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skLahirMatiViewModel.hasFormData() } }
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

    LaunchedEffect(skLahirMatiViewModel.skLahirMatiEvent) {
        skLahirMatiViewModel.skLahirMatiEvent.collect { event ->
            when (event) {
                is SKLahirMatiViewModel.SKLahirMatiEvent.SubmitSuccess -> {

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
                is SKLahirMatiViewModel.SKLahirMatiEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKLahirMatiViewModel.SKLahirMatiEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKLahirMatiViewModel.SKLahirMatiEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKLahirMatiViewModel.SKLahirMatiEvent.StepChanged -> {
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
                    title = "SK Lahir Mati",
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
                    skLahirMatiViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skLahirMatiViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skLahirMatiViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skLahirMatiViewModel.showConfirmationDialog() }
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
                    1 -> SKLahirMati1Content(
                        viewModel = skLahirMatiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKLahirMati2Content(
                        viewModel = skLahirMatiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKLahirMati3Content(
                        viewModel = skLahirMatiViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skLahirMatiViewModel,
                    onDismiss = {
                        skLahirMatiViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skLahirMatiViewModel.dismissPreview()
                        skLahirMatiViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skLahirMatiViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skLahirMatiViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skLahirMatiViewModel.dismissConfirmationDialog()
                        skLahirMatiViewModel.showPreview()
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
                        skLahirMatiViewModel.clearError()
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
    viewModel: SKLahirMatiViewModel,
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
                        PreviewItem("Keperluan", viewModel.keperluanValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Ibu",
                    content = {
                        PreviewItem("NIK Ibu", viewModel.nikIbuValue)
                        PreviewItem("Nama Ibu", viewModel.namaIbuValue)
                        PreviewItem("Tempat Lahir Ibu", viewModel.tempatLahirIbuValue)
                        PreviewItem("Tanggal Lahir Ibu", viewModel.tanggalLahirIbuValue)
                        PreviewItem("Agama Ibu", viewModel.agamaIbuIdValue)
                        PreviewItem("Kewarganegaraan Ibu", viewModel.kewarganegaraanIbuIdValue)
                        PreviewItem("Pekerjaan Ibu", viewModel.pekerjaanIbuValue)
                        PreviewItem("Alamat Ibu", viewModel.alamatIbuValue)
                    }
                )
            }

            item {
                PreviewSection(
                    title = "Informasi Kelahiran & Kematian",
                    content = {
                        PreviewItem("Lama Dikandung", viewModel.lamaDikandungValue)
                        PreviewItem("Tanggal Meninggal", viewModel.tanggalMeninggalValue)
                        PreviewItem("Tempat Meninggal", viewModel.tempatMeninggalValue)
                        PreviewItem("Hubungan Pelapor dengan Anak", viewModel.hubunganIdValue)
                    }
                )
            }
        }
    }
}

package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjualbeli

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli.SKJualBeliViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.jualbeli.SKJualBeliViewModel.SKJualBeliEvent
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKJualBeliScreen(
    skJualBeliViewModel: SKJualBeliViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skJualBeliViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skJualBeliViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skJualBeliViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skJualBeliViewModel.isLoadingUserData } }
    val hasFormData by remember { derivedStateOf { skJualBeliViewModel.hasFormData() } }
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

    LaunchedEffect(skJualBeliViewModel.skJualBeliEvent) {
        skJualBeliViewModel.skJualBeliEvent.collect { event ->
            when (event) {
                is SKJualBeliEvent.SubmitSuccess -> {

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
                is SKJualBeliEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKJualBeliEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKJualBeliEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKJualBeliEvent.StepChanged -> {
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
                    title = "SK Jual Beli",
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
                    skJualBeliViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skJualBeliViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skJualBeliViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skJualBeliViewModel.showConfirmationDialog() }
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
                    1 -> SKJualBeli1Content(
                        viewModel = skJualBeliViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKJualBeli2Content(
                        viewModel = skJualBeliViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKJualBeli3Content(
                        viewModel = skJualBeliViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skJualBeliViewModel,
                    onDismiss = {
                        skJualBeliViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skJualBeliViewModel.dismissPreview()
                        skJualBeliViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skJualBeliViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skJualBeliViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skJualBeliViewModel.dismissConfirmationDialog()
                        skJualBeliViewModel.showPreview()
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
                        skJualBeliViewModel.clearError()
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
    viewModel: SKJualBeliViewModel,
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
                    title = "Informasi Penjual",
                    content = {
                        PreviewItem("NIK", viewModel.nik1Value)
                        PreviewItem("Nama Lengkap", viewModel.nama1Value)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahir1Value)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahir1Value)
                        PreviewItem(
                            "Jenis Kelamin",
                            if (viewModel.jenisKelamin1Value == "L") "Laki-laki" else "Perempuan"
                        )
                        PreviewItem("Pekerjaan", viewModel.pekerjaan1Value)
                        PreviewItem("Alamat", viewModel.alamat1Value)
                    }
                )
            }
            item {
                PreviewSection(
                    title = "Informasi Pembeli",
                    content = {
                        PreviewItem("NIK", viewModel.nik2Value)
                        PreviewItem("Nama Lengkap", viewModel.nama2Value)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahir2Value)
                        PreviewItem("Tanggal Lahir", viewModel.tanggalLahir2Value)
                        PreviewItem(
                            "Jenis Kelamin",
                            if (viewModel.jenisKelamin2Value == "L") "Laki-laki" else "Perempuan"
                        )
                        PreviewItem("Pekerjaan", viewModel.pekerjaan2Value)
                        PreviewItem("Alamat", viewModel.alamat2Value)
                    }
                )
            }
            item {
                PreviewSection(
                    title = "Informasi Barang",
                    content = {
                        PreviewItem("Jenis Barang", viewModel.jenisBarangValue)
                        PreviewItem("Rincian Barang", viewModel.rincianBarangValue)
                    }
                )
            }
        }
    }
}

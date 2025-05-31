package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppStepAnimatedContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.ErrorDialog
import com.cvindosistem.simpeldesa.core.components.LoadingScreen
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.pendatang.UsahaPendatang1Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.pendatang.UsahaPendatang2Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.pendatang.UsahaPendatang3Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.warga.UsahaWargaDesa1Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.warga.UsahaWargaDesa2Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.warga.UsahaWargaDesa3Content
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKUsahaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKUsahaScreen(
    viewModel: SKUsahaViewModel,
    navController: NavController
) {
    val tabs = listOf("Warga Desa", "Pendatang")

    // Observing ViewModel state
    val currentTab by remember { derivedStateOf { viewModel.currentTab } }
    val currentStepWargaDesa by remember { derivedStateOf { viewModel.currentStepWargaDesa } }
    val currentStepPendatang by remember { derivedStateOf { viewModel.currentStepPendatang } }
    val showConfirmationDialog by remember { derivedStateOf { viewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { viewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { viewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { viewModel.hasFormData() } }
    val totalSteps = 3

    // Dialog states
    var showSuccessDialog by remember { mutableStateOf(false) }
    var successDialogTitle by remember { mutableStateOf("") }
    var successDialogMessage by remember { mutableStateOf("") }
    var showBackWarningDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorDialogTitle by remember { mutableStateOf("") }
    var errorDialogMessage by remember { mutableStateOf("") }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle back button press
    BackHandler(enabled = hasFormData) {
        if (hasFormData) {
            showBackWarningDialog = true
        } else {
            navController.popBackStack()
        }
    }

    // Handle events from ViewModel
    LaunchedEffect(viewModel.usahaEvent) {
        viewModel.usahaEvent.collect { event ->
            when (event) {
                is SKUsahaViewModel.SKUsahaEvent.SubmitSuccess -> {
                    successDialogTitle = "Berhasil"
                    successDialogMessage = "Surat Keterangan Usaha berhasil diajukan"
                    showSuccessDialog = true

                    delay(1500)
                    showSuccessDialog = false

                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = false
                        }
                    }
                }
                is SKUsahaViewModel.SKUsahaEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKUsahaViewModel.SKUsahaEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKUsahaViewModel.SKUsahaEvent.BidangUsahaLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKUsahaViewModel.SKUsahaEvent.JenisUsahaLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKUsahaViewModel.SKUsahaEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKUsahaViewModel.SKUsahaEvent.StepChanged -> {
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
                    title = "SK Usaha",
                    showBackButton = true,
                    onBackClick = {
                        if (hasFormData) {
                            showBackWarningDialog = true
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
                AppTab(
                    selectedTab = currentTab,
                    tabs = tabs,
                    onTabSelected = { tabIndex ->
                        viewModel.updateCurrentTab(tabIndex)
                    }
                )
            }
        },
        bottomBar = {
            val currentStep = if (currentTab == 0) currentStepWargaDesa else currentStepPendatang

            AppBottomBar(
                onPreviewClick = {
                    viewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { viewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { viewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { viewModel.showConfirmationDialog() }
                } else null
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AnimatedTabContent(
                selectedTab = currentTab,
                paddingValues = PaddingValues(0.dp)
            ) { tabIndex, modifier ->
                when (tabIndex) {
                    0 -> {
                        // Tab Warga Desa
                        AppStepAnimatedContent(
                            currentStep = currentStepWargaDesa,
                            modifier = modifier.fillMaxSize()
                        ) { step ->
                            when (step) {
                                1 -> UsahaWargaDesa1Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                                2 -> UsahaWargaDesa2Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                                3 -> UsahaWargaDesa3Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                    1 -> {
                        // Tab Pendatang
                        AppStepAnimatedContent(
                            currentStep = currentStepPendatang,
                            modifier = modifier.fillMaxSize()
                        ) { step ->
                            when (step) {
                                1 -> UsahaPendatang1Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                                2 -> UsahaPendatang2Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                                3 -> UsahaPendatang3Content(
                                    viewModel = viewModel,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                SKUsahaPreviewDialog(
                    viewModel = viewModel,
                    onDismiss = {
                        viewModel.dismissPreview()
                    },
                    onSubmit = {
                        viewModel.dismissPreview()
                        viewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        viewModel.confirmSubmit()
                    },
                    onDismiss = {
                        viewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        viewModel.dismissConfirmationDialog()
                        viewModel.showPreview()
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

            // Success Dialog
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
                        viewModel.clearError()
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
private fun SKUsahaPreviewDialog(
    viewModel: SKUsahaViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    val previewData = viewModel.getPreviewData()

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
            // Informasi Pelapor
            item {
                PreviewSection(
                    title = "Informasi Pelapor",
                    content = {
                        PreviewItem("NIK", previewData["NIK"] ?: "")
                        PreviewItem("Nama", previewData["Nama"] ?: "")
                        PreviewItem("Tempat Lahir", previewData["Tempat Lahir"] ?: "")
                        PreviewItem("Tanggal Lahir", previewData["Tanggal Lahir"] ?: "")
                        PreviewItem("Jenis Kelamin", previewData["Jenis Kelamin"] ?: "")
                        PreviewItem("Pekerjaan", previewData["Pekerjaan"] ?: "")
                        PreviewItem("Alamat", previewData["Alamat"] ?: "")
                    }
                )
            }

            // Informasi Usaha
            item {
                PreviewSection(
                    title = "Informasi Usaha",
                    content = {
                        PreviewItem("Nama Usaha", previewData["Nama Usaha"] ?: "")
                        PreviewItem("Bidang Usaha", previewData["Bidang Usaha"] ?: "")
                        PreviewItem("Jenis Usaha", previewData["Jenis Usaha"] ?: "")
                        PreviewItem("Alamat Usaha", previewData["Alamat Usaha"] ?: "")
                        PreviewItem("NPWP", previewData["NPWP"] ?: "")
                    }
                )
            }

            // Informasi Pelengkap
            item {
                PreviewSection(
                    title = "Informasi Pelengkap",
                    content = {
                        PreviewItem("Keperluan", previewData["Keperluan"] ?: "")
                    }
                )
            }
        }
    }
}
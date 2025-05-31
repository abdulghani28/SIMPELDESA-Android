package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.ErrorDialog
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.LoadingScreen
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.core.helpers.dateFormatterToApiFormat
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPCatatanKepolisianViewModel

@Composable
fun SPCatatanKepolisianScreen(
    spCatatanKepolisianViewModel: SPCatatanKepolisianViewModel,
    navController: NavController
) {
    val showConfirmationDialog by remember { derivedStateOf { spCatatanKepolisianViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { spCatatanKepolisianViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { spCatatanKepolisianViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { spCatatanKepolisianViewModel.hasFormData() } }
    val validationErrors by spCatatanKepolisianViewModel.validationErrors.collectAsState()


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

    LaunchedEffect(spCatatanKepolisianViewModel.catatanKepolisianEvent) {
        spCatatanKepolisianViewModel.catatanKepolisianEvent.collect { event ->
            when (event) {
                is SPCatatanKepolisianViewModel.SPCatatanKepolisianEvent.SubmitSuccess -> {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = false
                        }
                    }
                    snackbarHostState.showSnackbar(
                        message = "Surat kuasa berhasil diajukan",
                        duration = SnackbarDuration.Long
                    )
                }
                is SPCatatanKepolisianViewModel.SPCatatanKepolisianEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPCatatanKepolisianViewModel.SPCatatanKepolisianEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SPCatatanKepolisianViewModel.SPCatatanKepolisianEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SPCatatanKepolisianViewModel.SPCatatanKepolisianEvent.StepChanged -> {
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
                    title = "SP Catatan Kepolisian",
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
                onPreviewClick = { spCatatanKepolisianViewModel.showPreview() },
                onSubmitClick = { spCatatanKepolisianViewModel.showConfirmationDialog() }
            )
        }
    ) { paddingValues ->
        FormSectionList(
            modifier = Modifier.padding(paddingValues),
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                UseMyDataCheckbox(
                    checked = spCatatanKepolisianViewModel.useMyDataChecked,
                    onCheckedChange = spCatatanKepolisianViewModel::updateUseMyData,
                    isLoading = spCatatanKepolisianViewModel.isLoadingUserData
                )
            }

            item {
                InformasiPelapor(
                    viewModel = spCatatanKepolisianViewModel,
                    validationErrors = validationErrors
                )
            }
        }
        if (showPreviewDialog) {
            PreviewDialog(
                viewModel = spCatatanKepolisianViewModel,
                onDismiss = {
                    spCatatanKepolisianViewModel.dismissPreview()
                },
                onSubmit = {
                    spCatatanKepolisianViewModel.dismissPreview()
                    spCatatanKepolisianViewModel.showConfirmationDialog()
                }
            )
        }

        // Confirmation Dialog
        if (showConfirmationDialog) {
            SubmitConfirmationDialog(
                onConfirm = {
                    spCatatanKepolisianViewModel.confirmSubmit()
                },
                onDismiss = {
                    spCatatanKepolisianViewModel.dismissConfirmationDialog()
                },
                onPreview = {
                    spCatatanKepolisianViewModel.dismissConfirmationDialog()
                    spCatatanKepolisianViewModel.showPreview()
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

        // Error Dialog
        if (showErrorDialog) {
            ErrorDialog(
                title = errorDialogTitle,
                message = errorDialogMessage,
                onDismiss = {
                    showErrorDialog = false
                    spCatatanKepolisianViewModel.clearError()
                }
            )
        }

        // Loading Overlay
        if (isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
private fun InformasiPelapor(
    viewModel: SPCatatanKepolisianViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        SectionTitle("Informasi Pelapor")

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = viewModel.nikValue,
            onValueChange = viewModel::updateNik,
            isError = viewModel.hasFieldError("nik"),
            errorMessage = viewModel.getFieldError("nik"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = viewModel.namaValue,
            onValueChange = viewModel::updateNama,
            isError = viewModel.hasFieldError("nama"),
            errorMessage = viewModel.getFieldError("nama")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTextField(
                    label = "Tempat Lahir",
                    placeholder = "Tempat lahir",
                    value = viewModel.tempatLahirValue,
                    onValueChange = viewModel::updateTempatLahir,
                    isError = viewModel.hasFieldError("tempat_lahir"),
                    errorMessage = viewModel.getFieldError("tempat_lahir")
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = viewModel.tanggalLahirValue,
                    onValueChange = viewModel::updateTanggalLahir,
                    isError = viewModel.hasFieldError("tanggal_lahir"),
                    errorMessage = viewModel.getFieldError("tanggal_lahir"),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.selectedGender,
            onGenderSelected = viewModel::updateGender,
            isError = viewModel.hasFieldError("jenis_kelamin"),
            errorMessage = viewModel.getFieldError("jenis_kelamin"),
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = viewModel.pekerjaanValue,
            onValueChange = viewModel::updatePekerjaan,
            isError = viewModel.hasFieldError("pekerjaan"),
            errorMessage = viewModel.getFieldError("pekerjaan")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatValue,
            onValueChange = viewModel::updateAlamat,
            isError = viewModel.hasFieldError("alamat"),
            errorMessage = viewModel.getFieldError("alamat")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = viewModel.keperluanValue,
            onValueChange = viewModel::updateKeperluan,
            isError = viewModel.hasFieldError("keperluan"),
            errorMessage = viewModel.getFieldError("keperluan")
        )
    }
}

@Composable
private fun PreviewDialog(
    viewModel: SPCatatanKepolisianViewModel,
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
                        PreviewItem("NIK", viewModel.nikValue)
                        PreviewItem("Nama Lengkap", viewModel.namaValue)
                        PreviewItem("Tempat Lahir", viewModel.tempatLahirValue)
                        PreviewItem("Tanggal Lahir", dateFormatterToApiFormat(viewModel.tanggalLahirValue))
                        PreviewItem("Jenis Kelamin", viewModel.selectedGender)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                        PreviewItem("Keperluan", viewModel.alamatValue)
                    }
                )
            }
        }
    }
}
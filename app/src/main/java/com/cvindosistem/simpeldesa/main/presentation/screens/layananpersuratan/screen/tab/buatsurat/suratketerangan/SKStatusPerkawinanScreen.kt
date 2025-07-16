package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.ErrorDialog
import com.cvindosistem.simpeldesa.core.components.FormSectionList
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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.statusperkawinan.SKStatusPerkawinanViewModel
import kotlinx.coroutines.delay


@Composable
fun SKStatusPerkawinanScreen(
    sKStatusPerkawinanViewModel: SKStatusPerkawinanViewModel,
    navController: NavController
) {
    val showConfirmationDialog by remember { derivedStateOf { sKStatusPerkawinanViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { sKStatusPerkawinanViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { sKStatusPerkawinanViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { sKStatusPerkawinanViewModel.hasFormData() } }
    val validationErrors by sKStatusPerkawinanViewModel.validationErrors.collectAsState()

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

    LaunchedEffect(sKStatusPerkawinanViewModel.catatanStatusPerkawinanEvent) {
        sKStatusPerkawinanViewModel.catatanStatusPerkawinanEvent.collect { event ->
            when (event) {
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.SubmitSuccess -> {
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
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.StepChanged -> {
                    // Optional: Show step change feedback
                    snackbarHostState.showSnackbar(
                        message = "Beralih ke langkah ${event.step}",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.AgamaLoadError -> {
                    errorDialogTitle = "Gagal Memuat Agama"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKStatusPerkawinanViewModel.SKStatusPerkawinanEvent.StatusKawinLoadError -> {
                    errorDialogTitle = "Gagal Memuat Status"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Status Perkawinan",
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
                onPreviewClick = { sKStatusPerkawinanViewModel.showPreview() },
                onSubmitClick = { sKStatusPerkawinanViewModel.showConfirmationDialog() }
            )
        }
    ) { paddingValues ->
        FormSectionList(
            modifier = Modifier.padding(paddingValues),
            background = MaterialTheme.colorScheme.background
        ) {
            item {
                UseMyDataCheckbox(
                    checked = sKStatusPerkawinanViewModel.useMyDataChecked,
                    onCheckedChange = sKStatusPerkawinanViewModel::updateUseMyData,
                    isLoading = sKStatusPerkawinanViewModel.isLoadingUserData
                )
            }

            item {
                InformasiPelapor(
                    viewModel = sKStatusPerkawinanViewModel,
                    validationErrors = validationErrors
                )
            }
        }
        if (showPreviewDialog) {
            PreviewDialog(
                viewModel = sKStatusPerkawinanViewModel,
                onDismiss = {
                    sKStatusPerkawinanViewModel.dismissPreview()
                },
                onSubmit = {
                    sKStatusPerkawinanViewModel.dismissPreview()
                    sKStatusPerkawinanViewModel.showConfirmationDialog()
                }
            )
        }

        // Confirmation Dialog
        if (showConfirmationDialog) {
            SubmitConfirmationDialog(
                onConfirm = {
                    sKStatusPerkawinanViewModel.confirmSubmit()
                },
                onDismiss = {
                    sKStatusPerkawinanViewModel.dismissConfirmationDialog()
                },
                onPreview = {
                    sKStatusPerkawinanViewModel.dismissConfirmationDialog()
                    sKStatusPerkawinanViewModel.showPreview()
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
                    sKStatusPerkawinanViewModel.clearError()
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
    viewModel: SKStatusPerkawinanViewModel,
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

        DropdownField(
            label = "Agama",
            value = viewModel.agamaList.find { it.id == viewModel.agamaValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.agamaList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateAgama(it.id) }
            },
            options = viewModel.agamaList.map { it.nama },
            isError = viewModel.hasFieldError("agama_id"),
            errorMessage = viewModel.getFieldError("agama_id"),
            onDropdownExpanded = viewModel::loadAgama
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

        DropdownField(
            label = "Status Perkawinan",
            value = viewModel.statusKawinList.find { it.id == viewModel.statusKawinValue }?.nama.orEmpty(),
            onValueChange = { selectedNama ->
                val selected = viewModel.statusKawinList.find { it.nama == selectedNama }
                selected?.let { viewModel.updateStatusKawin(it.id) }
            },
            options = viewModel.statusKawinList.map { it.nama },
            isError = viewModel.hasFieldError("status_kawin_id"),
            errorMessage = viewModel.getFieldError("status_kawin_id"),
            onDropdownExpanded = viewModel::loadStatusKawin
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
    viewModel: SKStatusPerkawinanViewModel,
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
                        PreviewItem("Agama", viewModel.agamaValue)
                        PreviewItem("Pekerjaan", viewModel.pekerjaanValue)
                        PreviewItem("Alamat", viewModel.alamatValue)
                        PreviewItem("Keperluan", viewModel.alamatValue)
                    }
                )
            }
        }
    }
}
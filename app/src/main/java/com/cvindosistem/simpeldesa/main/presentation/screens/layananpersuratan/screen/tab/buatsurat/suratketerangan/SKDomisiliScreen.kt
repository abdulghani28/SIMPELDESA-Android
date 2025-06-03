package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DatePickerField
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.ErrorDialog
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.LoadingScreen
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.components.BackWarningDialog
import com.cvindosistem.simpeldesa.main.presentation.components.BaseDialog
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewItem
import com.cvindosistem.simpeldesa.main.presentation.components.PreviewSection
import com.cvindosistem.simpeldesa.main.presentation.components.SubmitConfirmationDialog
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.domisili.SKDomisiliViewModel
import kotlinx.coroutines.delay

@Composable
fun SKDomisiliScreen(
    skDomisiliViewModel: SKDomisiliViewModel,
    navController: NavController
) {
    val showConfirmationDialog by remember { derivedStateOf { skDomisiliViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skDomisiliViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skDomisiliViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skDomisiliViewModel.hasFormData() } }
    val validationErrors by skDomisiliViewModel.validationErrors.collectAsState()
    val currentTab by remember { derivedStateOf { skDomisiliViewModel.currentTab } }

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

    val tabs = listOf("Warga Desa", "Pendatang")

    // Handle back button press dari sistem
    BackHandler(enabled = hasFormData) {
        if (hasFormData) {
            showBackWarningDialog = true
        } else {
            navController.popBackStack()
        }
    }

    LaunchedEffect(skDomisiliViewModel.domisiliEvent) {
        skDomisiliViewModel.domisiliEvent.collect { event ->
            when (event) {
                is SKDomisiliViewModel.SKDomisiliEvent.SubmitSuccess -> {

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
                is SKDomisiliViewModel.SKDomisiliEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKDomisiliViewModel.SKDomisiliEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKDomisiliViewModel.SKDomisiliEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKDomisiliViewModel.SKDomisiliEvent.AgamaLoadError -> {
                    errorDialogTitle = "Gagal Memuat Agama"
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
                    title = "SK Domisili",
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
                    onTabSelected = skDomisiliViewModel::updateCurrentTab
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { skDomisiliViewModel.showPreview() },
                onSubmitClick = { skDomisiliViewModel.showConfirmationDialog() }
            )
        }
    ) { paddingValues ->
        AnimatedTabContent(
            selectedTab = currentTab,
            paddingValues = paddingValues
        ) { tabIndex, modifier ->
            when (tabIndex) {
                0 -> WargaDesaContent(
                    modifier = modifier,
                    viewModel = skDomisiliViewModel,
                    validationErrors = validationErrors
                )
                1 -> PendatangContent(
                    modifier = modifier,
                    viewModel = skDomisiliViewModel,
                    validationErrors = validationErrors
                )
            }
        }

        // Preview Dialog
        if (showPreviewDialog) {
            PreviewDialog(
                viewModel = skDomisiliViewModel,
                onDismiss = {
                    skDomisiliViewModel.dismissPreview()
                },
                onSubmit = {
                    skDomisiliViewModel.dismissPreview()
                    skDomisiliViewModel.showConfirmationDialog()
                }
            )
        }

        // Confirmation Dialog
        if (showConfirmationDialog) {
            SubmitConfirmationDialog(
                onConfirm = {
                    skDomisiliViewModel.confirmSubmit()
                },
                onDismiss = {
                    skDomisiliViewModel.dismissConfirmationDialog()
                },
                onPreview = {
                    skDomisiliViewModel.dismissConfirmationDialog()
                    skDomisiliViewModel.showPreview()
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
                    skDomisiliViewModel.clearError()
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
private fun WargaDesaContent(
    modifier: Modifier = Modifier,
    viewModel: SKDomisiliViewModel,
    validationErrors: Map<String, String>
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            InformasiPelapor(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun PendatangContent(
    modifier: Modifier = Modifier,
    viewModel: SKDomisiliViewModel,
    validationErrors: Map<String, String>
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            UseMyDataCheckbox(
                checked = viewModel.useMyDataChecked,
                onCheckedChange = viewModel::updateUseMyData,
                isLoading = viewModel.isLoadingUserData
            )
        }

        item {
            InformasiPelapor(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }

        item {
            KewarganegaraanSection(
                selectedKewarganegaraan = viewModel.selectedKewarganegaraan,
                onSelectedKewarganegaraan = viewModel::updateKewarganegaraan,
                isError = viewModel.hasFieldError("kewarganegaraan"),
                errorMessage = viewModel.getFieldError("kewarganegaraan")
            )
        }

        item {
            AlamatLengkapSection(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }

        item {
            JumlahPengikutField(
                viewModel = viewModel,
                validationErrors = validationErrors
            )
        }
    }
}

@Composable
private fun InformasiPelapor(
    viewModel: SKDomisiliViewModel,
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
                    errorMessage = viewModel.getFieldError("tanggal_lahir")
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = viewModel.selectedGender,
            onGenderSelected = viewModel::updateGender,
            isError = viewModel.hasFieldError("jenis_kelamin"),
            errorMessage = viewModel.getFieldError("jenis_kelamin")
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
private fun AlamatLengkapSection(
    viewModel: SKDomisiliViewModel,
    validationErrors: Map<String, String>
) {
    Column {
        MultilineTextField(
            label = "Alamat Lengkap Sesuai Identitas atau Paspor",
            placeholder = "Masukkan alamat lengkap",
            value = viewModel.alamatSesuaiKTP,
            onValueChange = viewModel::updateAlamatSesuaiKTP,
            isError = viewModel.hasFieldError("alamat_identitas"),
            errorMessage = viewModel.getFieldError("alamat_identitas")
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tempat Tinggal Sekarang",
            placeholder = "Masukkan alamat tempat tinggal sekarang",
            value = viewModel.alamatTinggalSekarang,
            onValueChange = viewModel::updateAlamatTinggalSekarang,
            isError = viewModel.hasFieldError("alamat_tinggal_sekarang"),
            errorMessage = viewModel.getFieldError("alamat_tinggal_sekarang")
        )
    }
}

@Composable
private fun JumlahPengikutField(
    viewModel: SKDomisiliViewModel,
    validationErrors: Map<String, String>
) {
    AppTextField(
        label = "Jumlah Pengikut",
        placeholder = "Masukkan jumlah pengikut",
        value = viewModel.jumlahPengikut,
        onValueChange = viewModel::updateJumlahPengikut,
        isError = viewModel.hasFieldError("jumlah_pengikut"),
        errorMessage = viewModel.getFieldError("jumlah_pengikut"),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun PreviewDialog(
    viewModel: SKDomisiliViewModel,
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
                        val previewData = viewModel.getPreviewData()
                        previewData.forEach { (label, value) ->
                            if (value.isNotBlank()) {
                                PreviewItem(label, value)
                            }
                        }
                    }
                )
            }
        }
    }
}
package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sknikahwarganonmuslim

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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.nikahnonmuslim.SKNikahWargaNonMuslimViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKNikahWargaNonMuslimScreen(
    skNikahWargaNonMuslimViewModel: SKNikahWargaNonMuslimViewModel,
    navController: NavController
) {
    val currentStep by remember { derivedStateOf { skNikahWargaNonMuslimViewModel.currentStep } }
    val showConfirmationDialog by remember { derivedStateOf { skNikahWargaNonMuslimViewModel.showConfirmationDialog } }
    val showPreviewDialog by remember { derivedStateOf { skNikahWargaNonMuslimViewModel.showPreviewDialog } }
    val isLoading by remember { derivedStateOf { skNikahWargaNonMuslimViewModel.isLoading } }
    val hasFormData by remember { derivedStateOf { skNikahWargaNonMuslimViewModel.hasFormData() } }
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

    LaunchedEffect(skNikahWargaNonMuslimViewModel.skNikahEvent) {
        skNikahWargaNonMuslimViewModel.skNikahEvent.collect { event ->
            when (event) {
                is SKNikahWargaNonMuslimViewModel.SKNikahWargaNonMuslimEvent.SubmitSuccess -> {

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
                is SKNikahWargaNonMuslimViewModel.SKNikahWargaNonMuslimEvent.SubmitError -> {
                    errorDialogTitle = "Gagal Mengirim"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKNikahWargaNonMuslimViewModel.SKNikahWargaNonMuslimEvent.UserDataLoadError -> {
                    errorDialogTitle = "Gagal Memuat Data"
                    errorDialogMessage = event.message
                    showErrorDialog = true
                }
                is SKNikahWargaNonMuslimViewModel.SKNikahWargaNonMuslimEvent.ValidationError -> {
                    snackbarHostState.showSnackbar(
                        message = "Mohon lengkapi semua field yang diperlukan",
                        duration = SnackbarDuration.Short
                    )
                }
                is SKNikahWargaNonMuslimViewModel.SKNikahWargaNonMuslimEvent.StepChanged -> {
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
                    title = "Surat Keterangan Izin Orang Tua",
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
                    skNikahWargaNonMuslimViewModel.showPreview()
                },
                onBackClick = if (currentStep > 1) {
                    { skNikahWargaNonMuslimViewModel.previousStep() }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { skNikahWargaNonMuslimViewModel.nextStep() }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    { skNikahWargaNonMuslimViewModel.showConfirmationDialog() }
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
                    1 -> SKNikahWargaNonMuslim1Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> SKNikahWargaNonMuslim2Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> SKNikahWargaNonMuslim3Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    4 -> SKNikahWargaNonMuslim4Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    5 -> SKNikahWargaNonMuslim5Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    6 -> SKNikahWargaNonMuslim6Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    7 -> SKNikahWargaNonMuslim7Content(
                        viewModel = skNikahWargaNonMuslimViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Preview Dialog
            if (showPreviewDialog) {
                PreviewDialog(
                    viewModel = skNikahWargaNonMuslimViewModel,
                    onDismiss = {
                        skNikahWargaNonMuslimViewModel.dismissPreview()
                    },
                    onSubmit = {
                        skNikahWargaNonMuslimViewModel.dismissPreview()
                        skNikahWargaNonMuslimViewModel.showConfirmationDialog()
                    }
                )
            }

            // Confirmation Dialog
            if (showConfirmationDialog) {
                SubmitConfirmationDialog(
                    onConfirm = {
                        skNikahWargaNonMuslimViewModel.confirmSubmit()
                    },
                    onDismiss = {
                        skNikahWargaNonMuslimViewModel.dismissConfirmationDialog()
                    },
                    onPreview = {
                        skNikahWargaNonMuslimViewModel.dismissConfirmationDialog()
                        skNikahWargaNonMuslimViewModel.showPreview()
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
                        skNikahWargaNonMuslimViewModel.clearError()
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
    viewModel: SKNikahWargaNonMuslimViewModel,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    BaseDialog(
        title = "Preview SK Pernikahan Warga Non-Muslim",
        onDismiss = onDismiss,
        onSubmit = onSubmit,
        submitText = "Ajukan Sekarang",
        dismissText = "Tutup"
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // STEP 1: Data Suami & Istri
            item {
                PreviewSection("Data Suami") {
                    PreviewItem("NIK", viewModel.nikSuami)
                    PreviewItem("Nama Lengkap", viewModel.namaSuami)
                    PreviewItem("Tempat, Tanggal Lahir", "${viewModel.tempatLahirSuami}, ${viewModel.tanggalLahirSuami}")
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanSuami)
                    PreviewItem("Warga Negara", viewModel.wargaNegaraSuami)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanSuami)
                    PreviewItem("Pendidikan", viewModel.pendidikanIdSuami)
                    PreviewItem("Status Kawin", viewModel.statusKawinSuami)
                    PreviewItem("Perkawinan Ke-", viewModel.perkawinanKeSuami)
                    PreviewItem("Paspor", viewModel.pasporSuami)
                    PreviewItem("No KK", viewModel.noKkSuami)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiSuami)
                    PreviewItem("Agama", viewModel.agamaSuamiId)
                    PreviewItem("Telepon", viewModel.teleponSuami)
                }
                PreviewSection("Data Istri") {
                    PreviewItem("NIK", viewModel.nikIstri)
                    PreviewItem("Nama Lengkap", viewModel.namaIstri)
                    PreviewItem("Tempat, Tanggal Lahir", "${viewModel.tempatLahirIstri}, ${viewModel.tanggalLahirIstri}")
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanIstri)
                    PreviewItem("Warga Negara", viewModel.wargaNegaraIstri)
                    PreviewItem("Pekerjaan", viewModel.pekerjaanIstri)
                    PreviewItem("Pendidikan", viewModel.pendidikanIdIstri)
                    PreviewItem("Status Kawin", viewModel.statusKawinIstri)
                    PreviewItem("Perkawinan Ke-", viewModel.perkawinanKeIstri)
                    PreviewItem("Paspor", viewModel.pasporIstri)
                    PreviewItem("No KK", viewModel.noKkIstri)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiIstri)
                    PreviewItem("Agama", viewModel.agamaIstriId)
                    PreviewItem("Telepon", viewModel.teleponIstri)
                }
            }

            // STEP 2: Alamat & Anak
            item {
                PreviewSection("Alamat & Anak") {
                    PreviewItem("Alamat Suami", viewModel.alamatSuami)
                    PreviewItem("Alamat Istri", viewModel.alamatIstri)
                    PreviewItem("Anak Ke (Suami)", viewModel.anakKeSuami)
                    PreviewItem("Anak Ke (Istri)", viewModel.anakKeIstri)
                    PreviewItem("Jumlah Anak Diakui", viewModel.jumlahAnakYangDiakui)
                    PreviewItem("Anak 1", "${viewModel.namaAnak1} - ${viewModel.tanggalLahir1} - ${viewModel.noAktaLahir1}")
                    PreviewItem("Anak 2", "${viewModel.namaAnak2} - ${viewModel.tanggalLahir2} - ${viewModel.noAktaLahir2}")
                }
            }

            // STEP 3 & 4: Orang Tua Suami & Istri
            item {
                PreviewSection("Orang Tua Suami") {
                    PreviewItem("Nama Ayah", viewModel.namaAyahSuami)
                    PreviewItem("NIK", viewModel.nikAyahSuami)
                    PreviewItem("TTL", "${viewModel.tempatLahirAyahSuami}, ${viewModel.tanggalLahirAyahSuami}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanAyahSuami)
                    PreviewItem("Alamat", viewModel.alamatAyahSuami)
                    PreviewItem("Telepon", viewModel.teleponAyahSuami)
                    PreviewItem("Agama", viewModel.agamaAyahSuamiId)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanAyahSuami)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiAyahSuami)

                    PreviewItem("Nama Ibu", viewModel.namaIbuSuami)
                    PreviewItem("NIK", viewModel.nikIbuSuami)
                    PreviewItem("TTL", "${viewModel.tempatLahirIbuSuami}, ${viewModel.tanggalLahirIbuSuami}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanIbuSuami)
                    PreviewItem("Alamat", viewModel.alamatIbuSuami)
                    PreviewItem("Telepon", viewModel.teleponIbuSuami)
                    PreviewItem("Agama", viewModel.agamaIbuSuamiId)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanIbuSuami)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiIbuSuami)
                }

                PreviewSection("Orang Tua Istri") {
                    PreviewItem("Nama Ayah", viewModel.namaAyahIstri)
                    PreviewItem("NIK", viewModel.nikAyahIstri)
                    PreviewItem("TTL", "${viewModel.tempatLahirAyahIstri}, ${viewModel.tanggalLahirAyahIstri}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanAyahIstri)
                    PreviewItem("Alamat", viewModel.alamatAyahIstri)
                    PreviewItem("Telepon", viewModel.teleponAyahIstri)
                    PreviewItem("Agama", viewModel.agamaAyahIstriId)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanAyahIstri)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiAyahIstri)

                    PreviewItem("Nama Ibu", viewModel.namaIbuIstri)
                    PreviewItem("NIK", viewModel.nikIbuIstri)
                    PreviewItem("TTL", "${viewModel.tempatLahirIbuIstri}, ${viewModel.tanggalLahirIbuIstri}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanIbuIstri)
                    PreviewItem("Alamat", viewModel.alamatIbuIstri)
                    PreviewItem("Telepon", viewModel.teleponIbuIstri)
                    PreviewItem("Agama", viewModel.agamaIbuIstriId)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanIbuIstri)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiIbuIstri)
                }
            }

            // STEP 5: Saksi Pernikahan
            item {
                PreviewSection("Saksi Pernikahan") {
                    PreviewItem("Nama Saksi 1", viewModel.namaSaksi1)
                    PreviewItem("NIK", viewModel.nikSaksi1)
                    PreviewItem("TTL", "${viewModel.tempatLahirSaksi1}, ${viewModel.tanggalLahirSaksi1}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanSaksi1)
                    PreviewItem("Alamat", viewModel.alamatSaksi1)
                    PreviewItem("Telepon", viewModel.teleponSaksi1)
                    PreviewItem("Agama", viewModel.agamaSaksi1Id)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanSaksi1)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiSaksi1)
                    PreviewItem("Nama Ayah", viewModel.namaAyahSaksi1)

                    PreviewItem("Nama Saksi 2", viewModel.namaSaksi2)
                    PreviewItem("NIK", viewModel.nikSaksi2)
                    PreviewItem("TTL", "${viewModel.tempatLahirSaksi2}, ${viewModel.tanggalLahirSaksi2}")
                    PreviewItem("Pekerjaan", viewModel.pekerjaanSaksi2)
                    PreviewItem("Alamat", viewModel.alamatSaksi2)
                    PreviewItem("Telepon", viewModel.teleponSaksi2)
                    PreviewItem("Agama", viewModel.agamaSaksi2Id)
                    PreviewItem("Kewarganegaraan", viewModel.kewarganegaraanSaksi2)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiSaksi2)
                    PreviewItem("Nama Ayah", viewModel.namaAyahSaksi2)
                }
            }

            // STEP 6: Pernikahan & Pemuka Agama
            item {
                PreviewSection("Pernikahan & Pemuka Agama") {
                    PreviewItem("Agama Pernikahan", viewModel.agamaPernikahanId)
                    PreviewItem("Organisasi", viewModel.namaOrganisasiPernikahan)
                    PreviewItem("Nama Pemuka Agama", viewModel.namaPemukaAgama)
                    PreviewItem("Tanggal Pemberkatan", viewModel.tanggalPemberkatanPernikahan)
                    PreviewItem("Tanggal Melapor", viewModel.tanggalMelaporPernikahan)
                    PreviewItem("Badan Peradilan", viewModel.badanPeradilanPernikahan)
                }
            }

            // STEP 7: Legalitas & Putusan
            item {
                PreviewSection("Legalitas & Putusan Pengadilan") {
                    PreviewItem("Nomor Putusan", viewModel.nomorPutusanPengadilan)
                    PreviewItem("Tanggal Putusan", viewModel.tanggalPutusanPengadilan)
                    PreviewItem("Nomor Izin Perwakilan", viewModel.nomorIzinPerwakilan)
                }
            }
        }
    }
}

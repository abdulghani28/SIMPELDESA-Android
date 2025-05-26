package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.cvindosistem.simpeldesa.core.components.FormSectionList
import com.cvindosistem.simpeldesa.core.components.GenderSelection
import com.cvindosistem.simpeldesa.core.components.KewarganegaraanSection
import com.cvindosistem.simpeldesa.core.components.MultilineTextField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.UseMyDataCheckbox

@Composable
fun SKDomisiliScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Warga Desa", "Pendatang")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Domisili",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
                AppTab(selectedTab, tabs, { selectedTab = it })
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = { },
                onSubmitClick = { }
            )
        }
    ) { paddingValues ->
        AnimatedTabContent(
            selectedTab = selectedTab,
            paddingValues = paddingValues
        ) { tabIndex, modifier ->
            when (tabIndex) {
                0 -> WargaDesaContent(modifier)
                1 -> PendatangContent(modifier)
            }
        }
    }
}

@Composable
private fun WargaDesaContent(
    modifier: Modifier = Modifier
) {
    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            UseMyDataCheckbox()
        }

        item {
            InformasiPelapor()
        }
    }
}

@Composable
private fun PendatangContent(
    modifier: Modifier = Modifier
) {
    var selectedKewarganegaraan by remember { mutableStateOf("") }

    FormSectionList(
        modifier = modifier,
        background = MaterialTheme.colorScheme.background
    ) {
        item {
            UseMyDataCheckbox()
        }

        item {
            InformasiPelapor()
        }

        item {
            KewarganegaraanSection(
                selectedKewarganegaraan = selectedKewarganegaraan,
                onSelectedKewarganegaraan = { selectedKewarganegaraan = it }
            )
        }

        item {
            AlamatLengkapSection()
        }

        item {
            JumlahPengikutField()
        }
    }
}

@Composable
private fun InformasiPelapor() {
    Column {
        SectionTitle("Informasi Pelapor")

        Spacer(modifier = Modifier.height(16.dp))

        var nikValue by remember { mutableStateOf("") }
        var namaValue by remember { mutableStateOf("") }
        var tempatLahirValue by remember { mutableStateOf("") }
        var tanggalLahirValue by remember { mutableStateOf("") }
        var selectedGender by remember { mutableStateOf("") }
        var agamaValue by remember { mutableStateOf("") }
        var pekerjaanValue by remember { mutableStateOf("") }
        var alamatValue by remember { mutableStateOf("") }
        var keperluan by remember { mutableStateOf("") }

        AppTextField(
            label = "Nomor Induk Kependudukan (NIK)",
            placeholder = "Masukkan NIK",
            value = nikValue,
            onValueChange = { nikValue = it },
            isError = false,
            errorMessage = null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            value = namaValue,
            onValueChange = { namaValue = it },
            isError = false,
            errorMessage = null
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
                    value = tempatLahirValue,
                    onValueChange = { tempatLahirValue = it },
                    isError = false,
                    errorMessage = null
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DatePickerField(
                    label = "Tanggal Lahir",
                    value = tanggalLahirValue,
                    onValueChange = { tanggalLahirValue = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelection(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Agama",
            value = agamaValue,
            onValueChange = { agamaValue = it },
            options = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Pekerjaan",
            placeholder = "Masukkan pekerjaan",
            value = pekerjaanValue,
            onValueChange = { pekerjaanValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Lengkap",
            placeholder = "Masukkan alamat lengkap",
            value = alamatValue,
            onValueChange = { alamatValue = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Keperluan",
            placeholder = "Masukkan keperluan",
            value = keperluan,
            onValueChange = { keperluan = it },
            isError = false,
            errorMessage = null
        )
    }
}

@Composable
private fun AlamatLengkapSection() {
    Column {
        var alamatSesuaiKTP by remember { mutableStateOf("Jl. Kangkung Lemas, Kec. Terong Belanda, Kab. Kebun Subur") }
        var alamatTinggalSekarang by remember { mutableStateOf("Jl. Kangkung Lemas, Kec. Terong Belanda, Kab. Kebun Subur") }

        MultilineTextField(
            label = "Alamat Lengkap Sesuai Identitas atau Paspor",
            placeholder = "Masukkan alamat lengkap",
            value = alamatSesuaiKTP,
            onValueChange = { alamatSesuaiKTP = it },
            isError = false,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        MultilineTextField(
            label = "Alamat Tempat Tinggal Sekarang",
            placeholder = "Masukkan alamat tempat tinggal sekarang",
            value = alamatTinggalSekarang,
            onValueChange = { alamatTinggalSekarang = it },
            isError = false,
            errorMessage = null
        )
    }
}

@Composable
private fun JumlahPengikutField() {
    var jumlahPengikut by remember { mutableStateOf("4 Orang") }

    AppTextField(
        label = "Jumlah Pengikut",
        placeholder = "Masukkan jumlah pengikut",
        value = jumlahPengikut,
        onValueChange = { jumlahPengikut = it },
        isError = false,
        errorMessage = null,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
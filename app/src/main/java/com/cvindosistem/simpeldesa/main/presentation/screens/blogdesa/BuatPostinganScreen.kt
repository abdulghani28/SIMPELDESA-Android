package com.cvindosistem.simpeldesa.main.presentation.screens.blogdesa

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppOutlinedTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.ImagePickerBox
import com.cvindosistem.simpeldesa.core.components.LabelFieldText
import com.cvindosistem.simpeldesa.core.components.RichTextEditorSection
import com.cvindosistem.simpeldesa.core.components.TagsInputSection

@Composable
fun BuatPostinganScreen(
    navController: NavController
) {
    var fotoCover by remember { mutableStateOf<Uri?>(null) }
    var selectedKategori by remember { mutableStateOf("") }
    var judulValue by remember { mutableStateOf("") }
    var isiBlogValue by remember { mutableStateOf("") }
    var tagsValue by remember { mutableStateOf("") }

    val kategoriOptions = listOf(
        "Teknologi",
        "Pertanian",
        "Pendidikan",
        "Kesehatan",
        "Ekonomi",
        "Sosial"
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fotoCover = uri
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Buat Postingan",
                showBackButton = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            AppBottomBar(
                onDraftClick = { },
                onSubmitClick = { },
                submitText = "Buat Postingan",
                submitIcon = R.drawable.ic_upload
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Foto Cover Section
            item {
                Column {
                    LabelFieldText("Foto Cover")

                    ImagePickerBox(
                        fotoCover,
                        launcher
                    )
                }
            }

            // Kategori Section
            item {
                Column {
                    DropdownField(
                        label = "Kategori",
                        value = selectedKategori,
                        onValueChange = { selectedKategori = it },
                        options = kategoriOptions,
                        isError = false,
                        errorMessage = null
                    )
                }
            }

            // Judul Section
            item {
                Column {
                    LabelFieldText("Judul")

                    AppOutlinedTextField(
                        value = judulValue,
                        onValueChange = { judulValue = it },
                        isError = false,
                        placeholder = "Judul blog"
                    )
                }
            }

            // Isi Blog Section
            item {
                RichTextEditorSection(
                    value = isiBlogValue,
                    onValueChange = { isiBlogValue = it }
                )
            }

            // Tags Section
            item {
                TagsInputSection(
                    tags = remember { mutableStateListOf<String>() },
                    currentInput = tagsValue,
                    onInputChange = { tagsValue = it }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}
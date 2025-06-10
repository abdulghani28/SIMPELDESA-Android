package com.cvindosistem.simpeldesa.main.presentation.screens.blogdesa

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppOutlinedTextField
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.ImagePickerBox
import com.cvindosistem.simpeldesa.core.components.LabelFieldText
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@Composable
fun BuatPostinganScreen(
    navController: NavController
) {
    var fotoCover by remember { mutableStateOf<Uri?>(null) }
    var selectedKategori by remember { mutableStateOf("") }
    var judulValue by remember { mutableStateOf("") }
    var isiBlogValue by remember { mutableStateOf("") }
    var tagsValue by remember { mutableStateOf("") }
    var showKategoriDropdown by remember { mutableStateOf(false) }

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
                        placeholder = "Teknologi Pertanian yang Meningkatkan Produktivitas Petani Desa"
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

@Composable
private fun TagsInputSection(
    tags: SnapshotStateList<String>,
    currentInput: String,
    onInputChange: (String) -> Unit
) {
    Column {
        LabelFieldText("Tambahkan Tag (opsional)")

        // Tags display
        if (tags.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(tags) { tag ->
                    TagChip(
                        text = tag,
                        onRemove = { tags.remove(tag) }
                    )
                }
            }
        }

        // Input field
        AppOutlinedTextField(
            value = currentInput,
            onValueChange = { newValue ->
                if (newValue.endsWith(" ") && newValue.trim().isNotEmpty()) {
                    val newTag = newValue.trim()
                    if (newTag.isNotEmpty() && !tags.contains(newTag)) {
                        tags.add(newTag)
                    }
                    onInputChange("")
                } else {
                    onInputChange(newValue)
                }
            },
            isError = false,
            placeholder = "Ketik tag dan tekan spasi untuk menambahkan"
        )
    }
}

@Composable
private fun TagChip(
    text: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                Color(0xFFE8F0FE),
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF1565C0)
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close), // Replace with close icon
                contentDescription = "Remove tag",
                modifier = Modifier.size(12.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichTextEditorSection(
    value: String,
    onValueChange: (String) -> Unit
) {
    // Create rich text state
    val richTextState = rememberRichTextState()

    // Initialize dengan value yang ada
    LaunchedEffect(value) {
        if (value.isNotEmpty() && richTextState.toHtml() != value) {
            richTextState.setHtml(value)
        }
    }

    // Update parent ketika content berubah
    LaunchedEffect(richTextState.annotatedString.text) {
        onValueChange(richTextState.toHtml())
    }

    Column {
        LabelFieldText("Isi Blog")

        // Rich Text Toolbar
        RichTextStyleRow(
            state = richTextState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Rich Text Editor
        RichTextEditor(
            state = richTextState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    1.dp,
                    Color(0xFFDCE2FB),
                    RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = RichTextEditorDefaults.richTextEditorColors(
                containerColor = Color(0xFFF8F7FD),
            ),
            placeholder = {
                Text(
                    text = "Tulis isi Blog Anda di sini",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RichTextStyleRow(
    state: RichTextState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        item {
            // Bold Button
            RichTextStyleButton(
                onClick = {
                    state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                },
                isSelected = state.currentSpanStyle.fontWeight == FontWeight.Bold
            ) {
                Text("B", fontWeight = FontWeight.Bold)
            }
        }

        item {
            // Italic Button
            RichTextStyleButton(
                onClick = {
                    state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                },
                isSelected = state.currentSpanStyle.fontStyle == FontStyle.Italic
            ) {
                Text("I", fontStyle = FontStyle.Italic)
            }
        }

        item {
            // Underline Button
            RichTextStyleButton(
                onClick = {
                    state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                },
                isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true
            ) {
                Text("U", textDecoration = TextDecoration.Underline)
            }
        }

        item {
            // Strikethrough Button
            RichTextStyleButton(
                onClick = {
                    state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.LineThrough))
                },
                isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true
            ) {
                Text("S", textDecoration = TextDecoration.LineThrough)
            }
        }

        item {
            // Left Align Button
            RichTextStyleButton(
                onClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.Start
            ) {
                Text("⬅️")
            }
        }

        item {
            // Center Align Button
            RichTextStyleButton(
                onClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.Center
            ) {
                Text("⬜")
            }
        }

        item {
            // Right Align Button
            RichTextStyleButton(
                onClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.End
            ) {
                Text("➡️")
            }
        }

        item {
            // Unordered List Button
            RichTextStyleButton(
                onClick = { state.toggleUnorderedList() },
                isSelected = state.isUnorderedList
            ) {
                Text("•")
            }
        }

        item {
            // Ordered List Button
            RichTextStyleButton(
                onClick = { state.toggleOrderedList() },
                isSelected = state.isOrderedList
            ) {
                Text("1.")
            }
        }

        item {
            // Code Block Button
            RichTextStyleButton(
                onClick = { state.toggleCodeSpan() },
                isSelected = state.isCodeSpan
            ) {
                Text("</>")
            }
        }

        item {
            // Link Button
            RichTextStyleButton(
                onClick = {
                    // Contoh: menambah link
                    state.addLink(
                        text = "Link",
                        url = "https://example.com"
                    )
                },
                isSelected = state.isLink
            ) {
                Text("🔗")
            }
        }
    }
}

@Composable
fun RichTextStyleButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color(0xFF6B73FF).copy(alpha = 0.2f) else Color.Transparent,
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF6B73FF) else Color.Gray.copy(alpha = 0.3f))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    color = if (isSelected) Color(0xFF6B73FF) else Color.Gray
                )
            ) {
                content()
            }
        }
    }
}
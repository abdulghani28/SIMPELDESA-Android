package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

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
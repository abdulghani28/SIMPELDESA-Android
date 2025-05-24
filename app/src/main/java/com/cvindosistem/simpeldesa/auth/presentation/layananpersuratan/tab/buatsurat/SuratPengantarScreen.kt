package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.CardTitleText

@Composable
fun SuratPengantarScreen(
    onNavigateBack: () -> Unit = {},
    onSuratItemClick: (SuratPengantarItem) -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright)
    ) {
        AppTopBar(
            title = "Surat Pengantar",
            showBackButton = true,
            onBackClick = onNavigateBack
        )

        SuratPengantarContent(
            onSuratItemClick = onSuratItemClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SuratPengantarContent(
    onSuratItemClick: (SuratPengantarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getSuratPengantarList()) { suratItem ->
            SuratPengantarItemCard(
                suratItem = suratItem,
                onItemClick = { onSuratItemClick(suratItem) }
            )
        }
    }
}

@Composable
private fun SuratPengantarItemCard(
    suratItem: SuratPengantarItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SuratPengantarIcon(
                emoji = suratItem.emoji,
                backgroundColor = suratItem.iconBackgroundColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            SuratPengantarInfo(
                title = suratItem.title,
                description = suratItem.description,
            )
        }
    }
}

@Composable
private fun SuratPengantarIcon(
    emoji: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun SuratPengantarInfo(
    title: String,
    description: String,
) {
    Column {
        CardTitleText(title)

        Spacer(modifier = Modifier.height(6.dp))

        BodySmallText(description)
    }
}

// Data class untuk item surat Pengantar
data class SuratPengantarItem(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val iconBackgroundColor: Color
)

// Function untuk mendapatkan data surat Pengantar
private fun getSuratPengantarList(): List<SuratPengantarItem> {
    return listOf(
        SuratPengantarItem(
            id = "domisili",
            title = "Surat Pengantar Domisili",
            description = "Menyatakan tempat tinggal seseorang untuk keperluan administrasi.",
            emoji = "🌏",
            iconBackgroundColor = Color(0xFFF0F4FF)
        ),
        SuratPengantarItem(
            id = "tidak_mampu",
            title = "Surat Pengantar Tidak Mampu",
            description = "Menyatakan kondisi ekonomi kurang mampu untuk bantuan atau keringanan biaya.",
            emoji = "⚖️",
            iconBackgroundColor = Color(0xFFFFF4E6)
        ),
        SuratPengantarItem(
            id = "kelahiran",
            title = "Surat Pengantar Kelahiran",
            description = "Mengonfirmasi kelahiran anak untuk pengurusan akta kelahiran.",
            emoji = "👶",
            iconBackgroundColor = Color(0xFFF0FFF4)
        ),
        SuratPengantarItem(
            id = "kematian",
            title = "Surat Pengantar Kematian",
            description = "Menyatakan kematian seseorang untuk urusan administrasi.",
            emoji = "❌",
            iconBackgroundColor = Color(0xFFFFF0F0)
        ),
        SuratPengantarItem(
            id = "usaha",
            title = "Surat Pengantar Usaha",
            description = "Menyatakan kegiatan usaha yang dijalankan untuk keperluan perizinan.",
            emoji = "🏢",
            iconBackgroundColor = Color(0xFFF8F0FF)
        )
    )
}
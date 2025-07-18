package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.CardTitleText
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
fun SuratPengantarScreen(
    onNavigateBack: () -> Unit = {},
    navController: NavController,
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
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
private fun SuratPengantarContent(
    navController: NavController,
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
                onItemClick = {
                    navController.navigate(suratItem.rute)
                }
            )
        }
    }
}

@Composable
private fun SuratPengantarItemCard(
    suratItem: SuratItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier
            .clickable {
                onItemClick()
            }
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

// Function untuk mendapatkan data surat Pengantar
private fun getSuratPengantarList(): List<SuratItem> {
    return listOf(
        SuratItem(
            id = "kepolisian",
            title = "Surat Pengantar Catatan Kepolisian ",
            description = "Dibutuhkan untuk mengurus Surat Keterangan Catatan Kepolisian (SKCK).",
            emoji = "\uD83D\uDC6E\u200D♂\uFE0F",
            iconBackgroundColor = Color(0xFFF0F4FF),
            rute = Screen.SPCatatanKepolisian.route
        ),
        SuratItem(
            id = "kehilangan",
            title = "Surat Pengantar Kehilangan",
            description = "Digunakan untuk melaporkan kehilangan barang atau dokumen.",
            emoji = "❓",
            iconBackgroundColor = Color(0xFFFFF4E6),
            rute = Screen.SPKehilangan.route
        ),
        SuratItem(
            id = "perkawinan",
            title = "Surat Pengantar Perkawinan",
            description = "Diperlukan untuk administrasi pernikahan (Pria atau Wanita).",
            emoji = "\uD83D\uDC8D",
            iconBackgroundColor = Color(0xFFF0FFF4),
            rute = Screen.SPPernikahan.route
        ),
        SuratItem(
            id = "lintasbatas",
            title = "Surat Pengantar Pas Lintas Batas",
            description = "Dibutuhkan untuk pengurusan dokumen lintas batas wilayah negara.",
            emoji = "\uD83C\uDDEF\uD83C\uDDF5", // 🇮🇩 sebagai simbol wilayah/border (opsional bisa diganti 🌐)
            iconBackgroundColor = Color(0xFFE5F6FF),
            rute = Screen.SPPermohonanPenerbitanBukuPasLintasBatas.route
        )
    )
}
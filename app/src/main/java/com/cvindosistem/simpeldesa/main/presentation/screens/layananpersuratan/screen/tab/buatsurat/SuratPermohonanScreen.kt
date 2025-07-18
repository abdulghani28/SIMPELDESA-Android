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
fun SuratPermohonanScreen(
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
            title = "Surat Permohonan",
            showBackButton = true,
            onBackClick = onNavigateBack
        )

        SuratPermohonanContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
private fun SuratPermohonanContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getSuratPermohonanList()) { suratItem ->
            SuratPermohonanItemCard(
                suratItem = suratItem,
                onItemClick = {
                    navController.navigate(suratItem.rute)
                }
            )
        }
    }
}

@Composable
private fun SuratPermohonanItemCard(
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
            SuratPermohonanIcon(
                emoji = suratItem.emoji,
                backgroundColor = suratItem.iconBackgroundColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            SuratPermohonanInfo(
                title = suratItem.title,
                description = suratItem.description,
            )
        }
    }
}

@Composable
private fun SuratPermohonanIcon(
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
private fun SuratPermohonanInfo(
    title: String,
    description: String,
) {
    Column {
        CardTitleText(title)

        Spacer(modifier = Modifier.height(6.dp))

        BodySmallText(description)
    }
}

// Function untuk mendapatkan data surat permohonan
private fun getSuratPermohonanList(): List<SuratItem> {
    return listOf(
        SuratItem(
            id = "akta_lahir",
            title = "Surat Permohonan Akta Lahir",
            description = "Permohonan untuk penerbitan akta kelahiran baru.",
            emoji = "👶",
            iconBackgroundColor = Color(0xFFFFE5F1),
            rute = Screen.SPMAktaLahir.route
        ),
        SuratItem(
            id = "belum_akta_lahir",
            title = "Surat Permohonan Belum Memiliki Akta Lahir",
            description = "Permohonan untuk orang yang belum memiliki akta kelahiran.",
            emoji = "📋",
            iconBackgroundColor = Color(0xFFF0F4FF),
            rute = Screen.SPMBelumAktaLahir.route
        ),
        SuratItem(
            id = "duplikat_kelahiran",
            title = "Surat Permohonan Duplikat Kelahiran",
            description = "Permohonan duplikat atau salinan akta kelahiran yang hilang/rusak.",
            emoji = "📄",
            iconBackgroundColor = Color(0xFFFFF4E6),
            rute = Screen.SPMDuplikatKelahiran.route
        ),
        SuratItem(
            id = "duplikat_nikah",
            title = "Surat Permohonan Duplikat Surat Nikah",
            description = "Permohonan duplikat atau salinan surat nikah yang hilang/rusak.",
            emoji = "💍",
            iconBackgroundColor = Color(0xFFF0FFF4),
            rute = Screen.SPMDuplikatNikah.route
        ),
        SuratItem(
            id = "cerai",
            title = "Surat Permohonan Cerai",
            description = "Permohonan untuk pengurusan surat cerai atau perceraian.",
            emoji = "💔",
            iconBackgroundColor = Color(0xFFFFEBEE),
            rute = Screen.SPMCerai.route
        ),
        SuratItem(
            id = "kartu_keluarga",
            title = "Surat Permohonan Kartu Keluarga",
            description = "Permohonan untuk penerbitan kartu keluarga baru.",
            emoji = "👨‍👩‍👧‍👦",
            iconBackgroundColor = Color(0xFFE8F5E8),
            rute = Screen.SPMKartuKeluarga.route
        ),
        SuratItem(
            id = "perubahan_kk",
            title = "Surat Permohonan Perubahan Kartu Keluarga",
            description = "Permohonan untuk perubahan data pada kartu keluarga.",
            emoji = "✏️",
            iconBackgroundColor = Color(0xFFF5F5DC),
            rute = Screen.SPMPerubahanKK.route
        )
    )
}
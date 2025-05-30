package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.buatsurat

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
fun SuratRekomendasiScreen(
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
            title = "Surat Rekomendasi",
            showBackButton = true,
            onBackClick = onNavigateBack
        )

        SuratRekomendasiContent(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SuratRekomendasiContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getSuratRekomendasiList()) { suratItem ->
            SuratRekomendasiItemCard(
                suratItem = suratItem,
                onItemClick = {
                    navController.navigate(suratItem.rute)
                }
            )
        }
    }
}

@Composable
private fun SuratRekomendasiItemCard(
    suratItem: SuratRekomendasiItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier.clickable{
            onItemClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SuratRekomendasiIcon(
                emoji = suratItem.emoji,
                backgroundColor = suratItem.iconBackgroundColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            SuratRekomendasiInfo(
                title = suratItem.title,
                description = suratItem.description,
            )
        }
    }
}

@Composable
private fun SuratRekomendasiIcon(
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
private fun SuratRekomendasiInfo(
    title: String,
    description: String,
) {
    Column {
        CardTitleText(title)

        Spacer(modifier = Modifier.height(6.dp))

        BodySmallText(description)
    }
}

// Data class untuk item surat rekomendasi
data class SuratRekomendasiItem(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val iconBackgroundColor: Color,
    val rute: String
)

// Function untuk mendapatkan data surat rekomendasi
private fun getSuratRekomendasiList(): List<SuratRekomendasiItem> {
    return listOf(
        SuratRekomendasiItem(
            id = "keramaian",
            title = "Surat Rekomendasi Izin Keramaian",
            description = "Diperlukan untuk mendapatkan izin mengadakan acara atau kegiatan yang melibatkan keramaian.",
            emoji = "\uD83C\uDF89",
            iconBackgroundColor = Color(0xFFF0F4FF),
            rute = Screen.SRKeramaian.route
        )
    )
}
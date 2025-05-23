package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppSearchBar
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.components.TitleSmallText
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.CardTitleText
import com.itextpdf.layout.element.Text

@Composable
fun LayananPersuratanScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Buat Surat", "Surat Saya")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Layanan Persuratan",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)),
            modifier = Modifier.fillMaxSize()
        ) {
            Crossfade(
                targetState = selectedTab,
                animationSpec = tween(300),
                modifier = Modifier.fillMaxSize(),
                label = "tab_crossfade"
            ) { targetTab ->
                when (targetTab) {
                    0 -> {
                        BuatSuratContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        )
                    }
                    1 -> {
                        SuratSayaContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BuatSuratContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceBright)
            .padding(16.dp)
    ) {
        AppSearchBar(
            value = "",
            onValueSearch = { },
            placeholder = {
                Text(
                    text = "Cari Jenis Surat yang Akan Dibuat",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SuratTypeSection()

        Spacer(modifier = Modifier.height(24.dp))

        RecentLetterSection()
    }
}

@Composable
private fun SuratSayaContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceBright),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Daftar surat yang telah Anda buat akan muncul di sini",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp)
        )
    }
}

@Composable
private fun SuratTypeSection() {
    Column {
        TitleMediumText("Pilih Jenis Surat")

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(getSuratTypes()) { suratType ->
                SuratTypeCard(suratType = suratType)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SuratTypeCard(
    suratType: SuratType
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(suratType.icon),
                contentDescription = suratType.title,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TitleSmallText(suratType.title)

            Spacer(modifier = Modifier.height(4.dp))

            BodySmallText(suratType.subtitle)
        }
    }
}

@Composable
private fun RecentLetterSection() {
    Column {
        TitleMediumText("Surat Terakhir Dibuat")

        RecentLetterCard()
    }
}

@Composable
private fun RecentLetterCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { },
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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFFF5F7FE),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("\uD83C\uDF0F")
            }

            Spacer(modifier = Modifier.height(16.dp))

            CardTitleText("Surat Keterangan Domisili")

            Spacer(modifier = Modifier.height(6.dp))

            BodySmallText("Menyatakan tempat tinggal seseorang untuk keperluan administrasi.")
        }
    }
}

// Data class untuk tipe surat
data class SuratType(
    val title: String,
    val subtitle: String,
    val icon: Int
)

// Function untuk mendapatkan data tipe surat
private fun getSuratTypes(): List<SuratType> {
    return listOf(
        SuratType(
            title = "Surat Keterangan",
            subtitle = "14 Pilihan Surat",
            icon = R.drawable.ic_surat_keterangan,
        ),
        SuratType(
            title = "Surat Pengantar",
            subtitle = "3 Pilihan Surat",
            icon = R.drawable.ic_surat_pengantar,
        ),
        SuratType(
            title = "Surat Rekomendasi",
            subtitle = "1 Pilihan Surat",
            icon = R.drawable.ic_surat_rekomendasi,
        ),
        SuratType(
            title = "Surat Lainnya",
            subtitle = "2 Pilihan Surat",
            icon = R.drawable.ic_surat_lainnya,
        )
    )
}
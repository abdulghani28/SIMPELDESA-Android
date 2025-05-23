package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppSearchBar
import com.cvindosistem.simpeldesa.core.components.AppTopBar

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
        Text(
            text = "Pilih Jenis Surat",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(280.dp)
        ) {
            items(getSuratTypes()) { suratType ->
                SuratTypeCard(suratType = suratType)
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
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        suratType.iconBackgroundColor,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = suratType.icon,
                    contentDescription = suratType.title,
                    tint = suratType.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = suratType.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = suratType.subtitle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun RecentLetterSection() {
    Column {
        Text(
            text = "Surat Terakhir Dibuat",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        RecentLetterCard()
    }
}

@Composable
private fun RecentLetterCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF2196F3).copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Surat Keterangan Domisili",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Surat Keterangan Domisili",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2196F3)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Menyatakan tempat tinggal seseorang untuk keperluan administrasi.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

// Data class untuk tipe surat
data class SuratType(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconColor: Color,
    val iconBackgroundColor: Color
)

// Function untuk mendapatkan data tipe surat
private fun getSuratTypes(): List<SuratType> {
    return listOf(
        SuratType(
            title = "Surat Keterangan",
            subtitle = "14 Pilihan Surat",
            icon = Icons.Default.CheckCircle,
            iconColor = Color(0xFF8BC34A),
            iconBackgroundColor = Color(0xFF8BC34A).copy(alpha = 0.1f)
        ),
        SuratType(
            title = "Surat Pengantar",
            subtitle = "3 Pilihan Surat",
            icon = Icons.Default.Send,
            iconColor = Color(0xFFFF9800),
            iconBackgroundColor = Color(0xFFFF9800).copy(alpha = 0.1f)
        ),
        SuratType(
            title = "Surat Rekomendasi",
            subtitle = "1 Pilihan Surat",
            icon = Icons.Default.Star,
            iconColor = Color(0xFF9C27B0),
            iconBackgroundColor = Color(0xFF9C27B0).copy(alpha = 0.1f)
        ),
        SuratType(
            title = "Surat Lainnya",
            subtitle = "2 Pilihan Surat",
            icon = Icons.Default.List,
            iconColor = Color(0xFF607D8B),
            iconBackgroundColor = Color(0xFF607D8B).copy(alpha = 0.1f)
        )
    )
}
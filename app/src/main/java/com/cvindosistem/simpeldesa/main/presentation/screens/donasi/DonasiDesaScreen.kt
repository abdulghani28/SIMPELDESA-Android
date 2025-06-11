package com.cvindosistem.simpeldesa.main.presentation.screens.donasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.CategoryChips
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.core.components.SectionTitle
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
fun DonasiDesaScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Aktif") }

    val categories = listOf("Aktif", "Diproses", "Selesai", "Dibatalkan")
    val allDonations = getAllDonations()

    val filteredDonations = remember(searchQuery, selectedCategory) {
        allDonations.filter { donation ->
            val matchesSearch = searchQuery.isEmpty() ||
                    donation.title.contains(searchQuery, ignoreCase = true)
            val matchesCategory = donation.category == selectedCategory
            matchesSearch && matchesCategory
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Donasi Desa",
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                showNavigation = true,
                onNavigateClick = { /* Handle navigation */ }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AppSearchBarAndFilter(
                    value = searchQuery,
                    onValueSearch = { searchQuery = it },
                    placeholder = "Cari Keyword",
                    onFilterClick = { },
                    showFilter = true
                )
            }

            item {
                CategoryChips(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            items(filteredDonations) { donation ->
                DonationCard(
                    donation = donation,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DonationCard(
    donation: Donation,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image
            AsyncImage(
                model = donation.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            TitleMediumText(donation.title)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rp ${formatRupiah(donation.collected)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF2196F3)
                )

                Text(
                    text = "/ Rp ${formatRupiah(donation.target)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Period
            Text(
                text = "Periode: ${donation.period}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// Data class dan fungsi helper
data class Donation(
    val id: Int,
    val title: String,
    val collected: Long,
    val target: Long,
    val period: String,
    val category: String,
    val imageRes: Int
)

private fun getAllDonations(): List<Donation> {
    return listOf(
        Donation(
            id = 1,
            title = "Bantuan Untuk Korban Banjir",
            collected = 35000000,
            target = 50000000,
            period = "11 Maret 2024 s/d 30 April 2024",
            category = "Aktif",
            imageRes = R.drawable.sample_blog
        ),
        Donation(
            id = 2,
            title = "Pengobatan Mata Untuk Agus yang Sedih",
            collected = 15000000,
            target = 50000000,
            period = "11 Maret 2024 s/d 30 April 2024",
            category = "Aktif",
            imageRes = R.drawable.sample_blog
        ),
        Donation(
            id = 3,
            title = "Renovasi Jembatan Desa",
            collected = 25000000,
            target = 40000000,
            period = "15 Februari 2024 s/d 15 Mei 2024",
            category = "Aktif",
            imageRes = R.drawable.sample_blog
        ),
        Donation(
            id = 4,
            title = "Bantuan Modal UMKM Desa",
            collected = 30000000,
            target = 30000000,
            period = "1 Januari 2024 s/d 31 Maret 2024",
            category = "Selesai",
            imageRes = R.drawable.sample_blog
        )
    )
}

@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Box(
        modifier = modifier.background(
            Color(0xFFE0E0E0),
            RoundedCornerShape(8.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = contentDescription,
            tint = Color.Gray,
            modifier = Modifier.size(48.dp)
        )
    }
}

private fun formatRupiah(amount: Long): String {
    return String.format("%,d", amount).replace(',', '.')
}
package com.cvindosistem.simpeldesa.main.presentation.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.BlogItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.BlogSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.DonationItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.DonationSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.HeaderSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.ProductItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.ProductSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.RecentActivitySection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section.ServicesGrid

@Composable
fun BerandaScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection()
        }

        item {
            ServicesGrid()
        }

        item {
            RecentActivitySection(
                status = "Menunggu",
                date = "25 September 2024",
                title = "Surat Keterangan Status Perkawinan",
                description = "Surat ini berhasil diajukan ke pihak desa dan akan segera diproses.",
                onSeeAllClick = {}
            )
        }

        item {
            val blogSample = listOf(
                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru")
            )
            BlogSection(blogList = blogSample, onSeeAllClick = {})
        }

        item {
            val products = listOf(
                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
                ProductItem(R.drawable.telur, "Daging Sapi Wagyu A69 1 Kg", "Rp 15,179")
            )

            ProductSection("Sedang Laris 🤑", products)
        }

        item {
            val donations = listOf(
                DonationItem(
                    imageRes = R.drawable.sample_blog,
                    title = "Bantuan Untuk Korban Banjir",
                    collected = "Rp 35,000,000",
                    target = "Rp 50,000,000",
                    period = "11 Maret 2024 s/d 30 April 2024"
                ),
                DonationItem(
                    imageRes = R.drawable.sample_blog,
                    title = "Bantuan Untuk Korban Banjir",
                    collected = "Rp 35,000,000",
                    target = "Rp 50,000,000",
                    period = "11 Maret 2024 s/d 30 April 2024"
                ),
                DonationItem(
                    imageRes = R.drawable.sample_blog,
                    title = "Bantuan Untuk Korban Banjir",
                    collected = "Rp 35,000,000",
                    target = "Rp 50,000,000",
                    period = "11 Maret 2024 s/d 30 April 2024"
                ),
                DonationItem(
                    imageRes = R.drawable.sample_blog,
                    title = "Bantuan Untuk Korban Banjir",
                    collected = "Rp 35,000,000",
                    target = "Rp 50,000,000",
                    period = "11 Maret 2024 s/d 30 April 2024"
                ),
            )
            DonationSection("Donasi Aktif ❤️", donations)
        }
    }
}
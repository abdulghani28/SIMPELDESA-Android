package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.main.navigation.Screen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.BlogItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.BlogSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.DonationItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.DonationSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.HeaderSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.ProductItem
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.ProductSection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.RecentActivitySection
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section.ServicesGrid
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun BerandaScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    // Handle events
    LaunchedEffect(homeViewModel) {
        homeViewModel.homeEvent.collect { event ->
            when (event) {
                is HomeViewModel.HomeEvent.DataLoaded -> {
                    // Handle successful data load if needed
                }
                is HomeViewModel.HomeEvent.Error -> {
                    // Handle error, maybe show snackbar
                    Log.e("BerandaScreen", "Error loading data: ${event.message}")
                }
            }
        }
    }

    // Show loading or error state
    if (homeViewModel.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection(
                homeViewModel = homeViewModel,
                onNotifikasiClick = {
                    navController.navigate(Screen.Notification.route)
                },
                onDesaClick = {
                    navController.navigate(Screen.VillageInformation.route)
                }
            )
        }

        item {
            ServicesGrid(navController = navController)
        }

//        item {
//            RecentActivitySection(
//                status = "Menunggu",
//                date = "25 September 2024",
//                title = "Surat Keterangan Status Perkawinan",
//                description = "Surat ini berhasil diajukan ke pihak desa dan akan segera diproses.",
//                onSeeAllClick = {}
//            )
//        }
//
//        item {
//            val blogSample = listOf(
//                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
//                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
//                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
//                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru"),
//                BlogItem(R.drawable.sample_blog, "Potensi Desa", "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru")
//            )
//            BlogSection(blogList = blogSample, onSeeAllClick = {})
//        }
//
//        item {
//            val products = listOf(
//                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
//                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
//                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
//                ProductItem(R.drawable.telur, "Telur Ayam 1 Papan", "Rp 15,179"),
//                ProductItem(R.drawable.telur, "Daging Sapi Wagyu A69 1 Kg", "Rp 15,179")
//            )
//
//            ProductSection("Sedang Laris 🤑", products)
//        }
//
//        item {
//            val donations = listOf(
//                DonationItem(
//                    imageRes = R.drawable.sample_blog,
//                    title = "Bantuan Untuk Korban Banjir",
//                    collected = "Rp 35,000,000",
//                    target = "Rp 50,000,000",
//                    period = "11 Maret 2024 s/d 30 April 2024"
//                ),
//                DonationItem(
//                    imageRes = R.drawable.sample_blog,
//                    title = "Bantuan Untuk Korban Banjir",
//                    collected = "Rp 35,000,000",
//                    target = "Rp 50,000,000",
//                    period = "11 Maret 2024 s/d 30 April 2024"
//                ),
//                DonationItem(
//                    imageRes = R.drawable.sample_blog,
//                    title = "Bantuan Untuk Korban Banjir",
//                    collected = "Rp 35,000,000",
//                    target = "Rp 50,000,000",
//                    period = "11 Maret 2024 s/d 30 April 2024"
//                ),
//                DonationItem(
//                    imageRes = R.drawable.sample_blog,
//                    title = "Bantuan Untuk Korban Banjir",
//                    collected = "Rp 35,000,000",
//                    target = "Rp 50,000,000",
//                    period = "11 Maret 2024 s/d 30 April 2024"
//                ),
//            )
//            DonationSection("Donasi Aktif ❤️", donations)
//        }
    }

    // Refresh greeting setiap menit (opsional)
    LaunchedEffect(Unit) {
        while (true) {
            delay(60000) // 1 menit
            homeViewModel.updateGreeting()
        }
    }
}
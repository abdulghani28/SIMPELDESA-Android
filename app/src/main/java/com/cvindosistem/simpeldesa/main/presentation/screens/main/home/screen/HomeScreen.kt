package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    // Initialize notification state
    LaunchedEffect(Unit) {
        // Check initial notification state
        homeViewModel.checkInitialNotificationState(context)
    }

    // Register broadcast receiver for real-time updates
    DisposableEffect(context, homeViewModel) {
        val intentFilter = IntentFilter("com.cvindosistem.simpeldesa.NOTIFICATION_RECEIVED")
        val receiver = object : android.content.BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: android.content.Intent?) {
                if (context == null || intent == null) return

                val hasUnread = intent.getBooleanExtra("has_unread", false)
                homeViewModel.setHasUnreadNotifications(hasUnread)
            }
        }

        // Register receiver with proper flags
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context.registerReceiver(receiver, intentFilter)
        }

        // Cleanup receiver when composable is disposed
        onDispose {
            try {
                context.unregisterReceiver(receiver)
            } catch (e: Exception) {
                // Receiver might already be unregistered
                Log.w("BerandaScreen", "Failed to unregister receiver: ${e.message}")
            }
        }
    }

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

                HomeViewModel.HomeEvent.NotificationsLoaded -> {

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

        // Testing button - hapus ini setelah testing selesai
        item {
            Button(
                onClick = {
                    homeViewModel.onNewNotificationReceived()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Simulate New Notification (Testing)")
            }
        }
    }

    // Refresh greeting setiap menit (opsional)
    LaunchedEffect(Unit) {
        while (true) {
            delay(60000) // 1 menit
            homeViewModel.updateGreeting()
        }
    }
}
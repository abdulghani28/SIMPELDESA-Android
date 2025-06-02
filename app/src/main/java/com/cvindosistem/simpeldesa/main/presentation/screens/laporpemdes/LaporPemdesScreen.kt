package com.cvindosistem.simpeldesa.main.presentation.screens.laporpemdes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.main.presentation.screens.laporpemdes.tab.BuatLaporanContent
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasLaporanSayaTab

@Composable
fun LaporPemdesScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Buat Laporan", "Laporan Saya")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Lapor Pemdes",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
                AppTab(
                    selectedTab = selectedTab,
                    tabs = tabs,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AnimatedTabContent(
                selectedTab = selectedTab,
                paddingValues = PaddingValues(0.dp)
            ) { tabIndex, modifier ->
                when (tabIndex) {
                    0 -> BuatLaporanContent(modifier)
                    1 -> AktivitasLaporanSayaTab(modifier)
                }
            }
        }
    }
}
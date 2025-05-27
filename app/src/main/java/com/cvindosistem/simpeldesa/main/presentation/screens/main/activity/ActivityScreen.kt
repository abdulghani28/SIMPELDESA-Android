package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasLaporanSayaTab
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasPesananTab
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasSuratSayaTab

@Composable
fun AktivitasScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Surat Saya", "Laporan", "Pesanan")

    Scaffold(
        topBar = {
            Column {
                AppTab(
                    selectedTab = selectedTab,
                    tabs = tabs,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { paddingValues ->
        AnimatedTabContent(
            selectedTab = selectedTab,
            paddingValues = paddingValues
        ) { tabIndex, modifier ->
            when (tabIndex) {
                0 -> AktivitasSuratSayaTab(modifier)
                1 -> AktivitasLaporanSayaTab(modifier)
                2 -> AktivitasPesananTab(modifier)
            }
        }
    }
}
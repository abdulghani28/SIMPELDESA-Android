package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.SuratSayaContent
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasLaporanSayaTab
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasPesananTab
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section.AktivitasSuratSayaTab

@Composable
fun AktivitasScreen(
    navController: NavController,
    suratSayaViewModel: SuratSayaViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Surat Saya", "Laporan", "Pesanan")

    Scaffold(
        topBar = {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
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
                0 -> SuratSayaContent(
                    modifier = modifier,
                    viewModel = suratSayaViewModel,
                    navController = navController
                )
//                1 -> AktivitasLaporanSayaTab(modifier)
//                2 -> AktivitasPesananTab(modifier)
                1 -> Text("Laporan", modifier.fillMaxSize())
                2 -> Text("Pesanan", modifier.fillMaxSize())
            }
        }
    }
}
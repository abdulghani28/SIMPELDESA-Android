package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.BuatSuratContent
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.tab.SuratSayaContent
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel

@Composable
fun LayananPersuratanScreen(
    navController: NavController,
    suratSayaViewModel: SuratSayaViewModel
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
                0 -> BuatSuratContent(modifier, navController = navController)
                1 -> SuratSayaContent(
                    modifier = modifier,
                    viewModel = suratSayaViewModel
                )
            }
        }
    }
}
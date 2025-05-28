package com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village

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
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.PegawaiDesaContent
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.ProfilDesaTab
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.tab.infoapbdes.InfoAPBDesContent

@Composable
fun InformasiDesaScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Profil Desa", "Pegawai Desa", "Info APBDes")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Informasi Desa",
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
                0 -> ProfilDesaTab(modifier)
                1 -> PegawaiDesaContent(modifier)
                2 -> InfoAPBDesContent(modifier)
            }
        }
    }
}

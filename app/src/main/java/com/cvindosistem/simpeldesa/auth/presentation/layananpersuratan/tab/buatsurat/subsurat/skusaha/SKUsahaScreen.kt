package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.pendatang.UsahaPendatang1Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.pendatang.UsahaPendatang2Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.pendatang.UsahaPendatang3Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.warga.UsahaWargaDesa1Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.warga.UsahaWargaDesa2Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.skusaha.warga.UsahaWargaDesa3Content
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar

@Composable
fun SKUsahaScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Warga Desa", "Pendatang")

    // State untuk navigasi internal dalam setiap tab
    var wargaDesaStep by remember { mutableIntStateOf(1) }
    var pendatangStep by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Usaha",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
                AppTab(selectedTab, tabs, { selectedTab = it })
            }
        }
    ) { paddingValues ->
        AnimatedTabContent(
            selectedTab = selectedTab,
            paddingValues = paddingValues
        ) { tabIndex, modifier ->
            when (tabIndex) {
                0 -> {
                    // Tab Warga Desa dengan navigasi internal
                    when (wargaDesaStep) {
                        1 -> UsahaWargaDesa1Content(
                            modifier = modifier,
                            onContinueClick = { wargaDesaStep = 2 }
                        )
                        2 -> UsahaWargaDesa2Content(
                            modifier = modifier,
                            onBackClick = { wargaDesaStep = 1 },
                            onContinueClick = { wargaDesaStep = 3 }
                        )
                        3 -> UsahaWargaDesa3Content(
                            modifier = modifier,
                            onBackClick = { wargaDesaStep = 2 },
                            onContinueClick = { /* Handle submit */ }
                        )
                    }
                }
                1 -> {
                    // Tab Pendatang dengan navigasi internal
                    when (pendatangStep) {
                        1 -> UsahaPendatang1Content(
                            modifier = modifier,
                            onContinueClick = { pendatangStep = 2 }
                        )
                        2 -> UsahaPendatang2Content(
                            modifier = modifier,
                            onBackClick = { pendatangStep = 1 },
                            onContinueClick = { pendatangStep = 3 }
                        )
                        3 -> UsahaPendatang3Content(
                            modifier = modifier,
                            onBackClick = { pendatangStep = 2 },
                            onContinueClick = { /* Handle submit */ }
                        )
                    }
                }
            }
        }
    }
}
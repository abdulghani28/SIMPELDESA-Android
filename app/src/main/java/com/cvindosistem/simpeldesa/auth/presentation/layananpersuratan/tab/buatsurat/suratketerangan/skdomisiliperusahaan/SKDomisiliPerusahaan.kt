package com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.pendatang.DomisiliPendatang1Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.pendatang.DomisiliPendatang2Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.pendatang.DomisiliPendatang3Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.warga.DomisiliWargaDesa1Content
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.suratketerangan.skdomisiliperusahaan.warga.DomisiliWargaDesa2Content
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar

@Composable
fun SKDomisiliPerusahaanScreen(
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
                    title = "SK Domisili Perusahaan",
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
                        1 -> DomisiliWargaDesa1Content(
                            modifier = modifier,
                            onContinueClick = { wargaDesaStep = 2 }
                        )
                        2 -> DomisiliWargaDesa2Content(
                            modifier = modifier,
                            onBackClick = { wargaDesaStep = 1 },
                            onSubmitClick = {  }
                        )
                    }
                }
                1 -> {
                    // Tab Pendatang dengan navigasi internal
                    when (pendatangStep) {
                        1 -> DomisiliPendatang1Content(
                            modifier = modifier,
                            onContinueClick = { pendatangStep = 2 }
                        )
                        2 -> DomisiliPendatang2Content(
                            modifier = modifier,
                            onBackClick = { pendatangStep = 1 },
                            onContinueClick = { pendatangStep = 3 }
                        )
                        3 -> DomisiliPendatang3Content(
                            modifier = modifier,
                            onBackClick = { pendatangStep = 2 },
                            onSubmit = { /* Handle submit */ }
                        )
                    }
                }
            }
        }
    }
}
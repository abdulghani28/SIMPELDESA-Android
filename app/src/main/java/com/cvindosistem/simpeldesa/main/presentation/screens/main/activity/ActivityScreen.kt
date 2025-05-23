package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cvindosistem.simpeldesa.main.presentation.components.ActivityTabLayout

@Composable
fun ActivityScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Surat Saya", "Laporan", "Pesanan")

    Column(modifier = Modifier.fillMaxSize()) {
        ActivityTabLayout(
            tabs = tabs,
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        when (selectedTab) {
            0 -> LettersList()
            1 -> Text("Laporan Tab")
            2 -> Text("Pesanan Tab")
        }
    }
}
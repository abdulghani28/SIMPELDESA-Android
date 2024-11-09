package com.cvindosistem.simpeldesa.presentation.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.presentation.screens.main.activity.ActivityScreen
import com.cvindosistem.simpeldesa.presentation.screens.main.home.HomeScreen
import com.cvindosistem.simpeldesa.presentation.screens.main.others.OtherScreen

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Beranda") },
                    label = { Text("Beranda") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Aktivitas") },
                    label = { Text("Aktivitas") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Lainnya") },
                    label = { Text("Lainnya") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> ActivityScreen()
                2 -> OtherScreen()
            }
        }
    }
}
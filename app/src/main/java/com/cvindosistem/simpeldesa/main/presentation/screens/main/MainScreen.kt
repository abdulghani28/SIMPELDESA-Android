package com.cvindosistem.simpeldesa.main.presentation.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.AktivitasScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.BerandaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.ProfilScreen

// Data class untuk item bottom navigation
private data class BottomNavItem(
    val route: String,
    val title: String,
    val iconName: String
)

// Enum untuk bottom navigation items
private enum class BottomNavScreen(
    val route: String,
    val title: String,
    val iconName: String
) {
    BERANDA("beranda", "Beranda", "home"),
    AKTIVITAS("aktivitas", "Aktivitas", "aktivitas"),
    PROFIL("profil", "Profil", "profil")
}

@Composable
fun MainScreen(navController: NavController) {
    val internalNavController = rememberNavController()
    val currentRoute = internalNavController.currentBackStackEntryAsState().value?.destination?.route

    val bottomNavItems = BottomNavScreen.entries.map { screen ->
        BottomNavItem(
            route = screen.route,
            title = screen.title,
            iconName = screen.iconName
        )
    }

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { route ->
                    if (currentRoute != route) {
                        internalNavController.navigate(route) {
                            popUpTo(internalNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = internalNavController,
            startDestination = BottomNavScreen.BERANDA.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.BERANDA.route) {
                BerandaScreen()
            }

            composable(BottomNavScreen.AKTIVITAS.route) {
                AktivitasScreen(navController = navController)
            }

            composable(BottomNavScreen.PROFIL.route) {
                ProfilScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun CustomBottomNavigationBar(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = {
                    DynamicIcon(
                        iconName = item.iconName,
                        isSelected = isSelected,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Unspecified,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = Color.Transparent,
                    disabledIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    unselectedIconColor = Color.Unspecified
                )
            )
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
private fun DynamicIcon(
    iconName: String,
    isSelected: Boolean,
    contentDescription: String
) {
    val context = LocalContext.current
    val suffix = if (isSelected) "selected" else "unselected"
    val resourceName = "ic_${iconName}_$suffix"

    // Mendapatkan resource ID secara dinamis
    val resourceId = context.resources.getIdentifier(
        resourceName,
        "drawable",
        context.packageName
    )

    // Fallback ke icon default jika tidak ditemukan
    val drawableRes = if (resourceId != 0) resourceId else R.drawable.ic_home_unselected

    Icon(
        painter = painterResource(id = drawableRes),
        contentDescription = contentDescription
    )
}
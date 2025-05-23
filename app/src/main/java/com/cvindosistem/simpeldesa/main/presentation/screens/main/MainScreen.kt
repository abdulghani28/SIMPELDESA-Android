package com.cvindosistem.simpeldesa.main.presentation.screens.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavController) {
    val internalNavController = rememberNavController()
    val currentRoute = internalNavController.currentBackStackEntryAsState().value?.destination?.route

//    LaunchedEffect(Unit) {
//        viewModel.loginEvent.collectLatest { event ->
//            if (event is AuthViewModel.LoginEvent.Logout) {
//                navController.navigate(Screen.Login.route) {
//                    popUpTo(Screen.MainScreen.route) { inclusive = true }
//                }
//            }
//        }
//    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                BottomNavigationItem(
                    selected = currentRoute == "modul_aplikasi",
                    onClick = {
                        if (currentRoute != "modul_aplikasi") {
                            internalNavController.navigate("modul_aplikasi") {
                                popUpTo(internalNavController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_modul),
//                            contentDescription = "Modul Aplikasi"
//                        )
                    },
                    label = { Text("Modul Aplikasi") }
                )

                BottomNavigationItem(
                    selected = currentRoute == "lainnya",
                    onClick = {
                        if (currentRoute != "lainnya") {
                            internalNavController.navigate("lainnya") {
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_others_menu),
//                            contentDescription = "Lainnya"
//                        )
                    },
                    label = { Text("Lainnya") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = internalNavController,
            startDestination = "modul_aplikasi",
            modifier = Modifier.padding(innerPadding)
        ) {
//            composable("modul_aplikasi") {
//                ModulAplikasiScreen(
//                    mainNavController = navController
//                )
//            }

//            composable("lainnya") {
//                LainnyaScreen(
//                    navController = navController,
//                    viewModel = profileViewModel,
//                    authViewModel = viewModel,
//                )
//            }
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        colors = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.background,
            selectedTextColor = MaterialTheme.colorScheme.background,
            selectedIndicatorColor = MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
            disabledIconColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
            disabledTextColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
            unselectedTextColor = MaterialTheme.colorScheme.background,
            unselectedIconColor = MaterialTheme.colorScheme.background
        )
    )
}
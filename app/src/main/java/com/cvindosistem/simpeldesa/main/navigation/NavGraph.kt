package com.cvindosistem.simpeldesa.main.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.LoginScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.ForgotPasswordScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.OtpVerificationScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.PasswordResetViewModel
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.ResetPasswordScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.LayananPersuratanScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.SuratKeteranganScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.SuratLainnyaScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.SuratPengantarScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.SuratRekomendasiScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.SKDomisiliScreen
import com.cvindosistem.simpeldesa.auth.presentation.layananpersuratan.tab.buatsurat.subsurat.SKTidakMampu
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    userPreferences: UserPreferences = koinInject()
) {
    val isLoggedIn = userPreferences.isLoggedIn()

    val activeModule = userPreferences.getActiveModule() ?: ""

//    val initialStartDestination = when {
//        !isLoggedIn -> Screen.Login.route
//        activeModule.isNotEmpty() -> activeModule
//        else -> Screen.MainScreen.route
//    }

    val initialStartDestination = Screen.SKDomisiliScreen.route

    val passwordResetViewModel: PasswordResetViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = initialStartDestination
    ) {
        // ===== Auth =====
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                userPreferences = userPreferences
            )
        }


        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                viewModel = passwordResetViewModel
            )
        }

        composable("${Screen.OtpVerification.route}/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpVerificationScreen(
                navController = navController,
                email = email,
                viewModel = passwordResetViewModel
            )
        }

        composable("${Screen.ResetPassword.route}/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetPasswordScreen(
                navController = navController,
                email = email,
                viewModel = passwordResetViewModel
            )
        }

        // ===== Main =====
        composable(Screen.MainScreen.route) {
            Text("Main Screen")
        }

        // ===== Layanan Persuratan =====
        composable(Screen.LayananPersuratan.route) {
            LayananPersuratanScreen(
                navController = navController
            )
        }

        composable(Screen.SuratKeterangan.route) {
            SuratKeteranganScreen()
        }

        composable(Screen.SuratPengantar.route) {
            SuratPengantarScreen()
        }

        composable(Screen.SuratRekomendasi.route) {
            SuratRekomendasiScreen()
        }

        composable(Screen.SuratLainnya.route) {
            SuratLainnyaScreen()
        }

        composable(Screen.SKDomisiliScreen.route) {
            SKDomisiliScreen(
                navController = navController
            )
        }

        composable(Screen.SKTidakMampu.route) {
            SKTidakMampu(
                navController = navController
            )
        }
    }
}
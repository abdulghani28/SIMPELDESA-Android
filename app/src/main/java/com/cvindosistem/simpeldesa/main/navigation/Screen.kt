package com.cvindosistem.simpeldesa.main.navigation

sealed class Screen(val route: String) {
    // ===== Auth =====
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    object OtpVerification : Screen("otp_verification")
    object ResetPassword : Screen("reset_password")

    // ===== Menu Utama =====
    object MainScreen : Screen("main_screen")
}
package com.cvindosistem.simpeldesa.main.navigation

sealed class Screen(val route: String) {
    // ===== Auth =====
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    object OtpVerification : Screen("otp_verification")
    object ResetPassword : Screen("reset_password")

    // ===== Menu Utama =====
    object MainScreen : Screen("main_screen")

    // ===== Layanan Persuratan =====
    object LayananPersuratan : Screen("layanan_persuratan")
    object SuratKeterangan : Screen("surat_keterangan")
    object SuratPengantar : Screen("surat_pengantar")
    object SuratRekomendasi : Screen("surat_rekomendasi")
    object SuratLainnya : Screen("surat_lainnya")
    object SKDomisiliScreen : Screen("sk_domisili_screen")
    object SKTidakMampu : Screen("sk_tidak_mampu")
    object SKKematian1 : Screen("sk_kematian_1")
    object SKKematian2 : Screen("sk_kematian_2")
    object SKKematian3 : Screen("sk_kematian_3")
    object SKUsaha : Screen("sk_usaha")
}
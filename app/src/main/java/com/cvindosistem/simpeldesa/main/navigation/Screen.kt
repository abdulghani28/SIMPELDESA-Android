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
    object SKKematian : Screen("sk_kematian")
    object SKUsaha : Screen("sk_usaha")
    object SKBerpergian : Screen("sk_berpergian")
    object SKTidakMasukKerja : Screen("sk_tidak_masuk_kerja")
    object SKPenghasilan : Screen("sk_penghasilan")
    object SKStatusPerkawinan : Screen("sk_status_perkawinan")
    object SKResiKTPSementara : Screen("sk_resi_ktp_sementara")
    object SKDomisiliPerusahaan : Screen("sk_resi_ktp_sementara")
    object SKJandaDuda : Screen("sk_janda_duda")
    object SKBedaIdentitas : Screen("sk_beda_identitas")
    object SKGhaib : Screen("sk_ghaib")

    object SPCatatanKepolisian : Screen("sp_catatan_kepolisian")
    object SPKehilangan : Screen("sp_kehilangan")
    object SPPernikahan : Screen("sp_pernikahan")

    object SRKeramaian : Screen("sr_keramaian")

    object SuratTugas : Screen("surat_tugas")
    object SuratKuasa : Screen("surat_kuasa")

    object Notification : Screen("notifikasi")
    object VillageInformation : Screen("informasi_desa")
}
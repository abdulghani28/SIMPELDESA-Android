package com.cvindosistem.simpeldesa.main.navigation

/**
 * Representasi seluruh rute (halaman) yang tersedia dalam aplikasi.
 *
 * `Screen` adalah sealed class yang digunakan sebagai sumber kebenaran tunggal
 * untuk semua rute navigasi dalam aplikasi ini.
 *
 * Setiap object di dalamnya mewakili satu screen dan memiliki nilai `route` unik
 * yang digunakan oleh `NavHost` dalam fungsi `NavGraph` untuk mendefinisikan tujuan navigasi.
 *
 * Keuntungan utama pendekatan ini:
 * - Menghindari hardcoded string route di berbagai tempat.
 * - Memudahkan navigasi yang konsisten dan aman terhadap kesalahan ketik.
 * - Mempermudah refactor dan pencarian rute secara global.
 *
 * Contoh penggunaan:
 * ```kotlin
 * navController.navigate(Screen.SKPenghasilan.route)
 * ```
 *
 * Catatan:
 * - Gunakan nama object yang deskriptif dan konsisten.
 * - Rute harus unik dan tidak mengandung karakter ilegal untuk URI.
 */

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
    object DetailSurat : Screen("detail_surat")

    object SuratKeterangan : Screen("surat_keterangan")
    object SuratPengantar : Screen("surat_pengantar")
    object SuratRekomendasi : Screen("surat_rekomendasi")
    object SuratLainnya : Screen("surat_lainnya")

    object SKDomisiliScreen : Screen("sk_domisili_screen")
    object SKTidakMampu : Screen("sk_tidak_mampu")
    object SKKematian : Screen("sk_kematian")
    object SKKelahiran : Screen("sk_kelahiran")
    object SKUsaha : Screen("sk_usaha")
    object SKBerpergian : Screen("sk_berpergian")
    object SKTidakMasukKerja : Screen("sk_tidak_masuk_kerja")
    object SKPenghasilan : Screen("sk_penghasilan")
    object SKStatusPerkawinan : Screen("sk_status_perkawinan")
    object SKResiKTPSementara : Screen("sk_resi_ktp_sementara")
    object SKDomisiliPerusahaan : Screen("sk_domisili_perusahaan")
    object SKJandaDuda : Screen("sk_janda_duda")
    object SKBedaIdentitas : Screen("sk_beda_identitas")
    object SKGhaib : Screen("sk_ghaib")
    object SKBelumMemilikiPBB : Screen("sk_belum_memiliki_pbb")
    object SKJamkesos : Screen("sk_jamkesos")
    object SKJualBeli : Screen("sk_jual_beli")
    object SKKTPDalamProses : Screen("sk_ktp_dalam_proses")

    object SPCatatanKepolisian : Screen("sp_catatan_kepolisian")
    object SPKehilangan : Screen("sp_kehilangan")
    object SPPernikahan : Screen("sp_pernikahan")

    object SRKeramaian : Screen("sr_keramaian")

    object SuratTugas : Screen("surat_tugas")
    object SuratKuasa : Screen("surat_kuasa")

    object Notification : Screen("notifikasi")
    object VillageInformation : Screen("informasi_desa")

    // Lapor Pemdes
    object LaporPemdes : Screen("lapor_pemdes")

    // Blog Desa
    object BlogDesa : Screen("blog_desa")
    object Postingan : Screen("postingan")
    object BuatPostingan : Screen("buat_postingan")
    object LayananKesehatan : Screen("layanan_kesehatan")

    // Layanan Kesehatan
    object PemeriksaanIbu : Screen("pemeriksaan_ibu")
    object PemeriksaanBalita : Screen("pemeriksaan_balita")
    object PemeriksaanWusPus : Screen("pemeriksaan_wus_pus")

    object TambahPemeriksaanIbu : Screen("tambah_pemeriksaan_ibu")
    object TambahPemeriksaanBalita : Screen("tambah_pemeriksaan_balita")
    object TambahPemeriksaanWusPus : Screen("tambah_pemeriksaan_wus_pus")

    object LayananDonorDarah : Screen("donor_darah")
    object DonasiDesa : Screen("donasi_desa")
}
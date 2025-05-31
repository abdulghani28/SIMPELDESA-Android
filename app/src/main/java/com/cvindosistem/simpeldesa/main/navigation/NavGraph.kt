package com.cvindosistem.simpeldesa.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cvindosistem.simpeldesa.auth.presentation.auth.login.LoginScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.ForgotPasswordScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.OtpVerificationScreen
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.PasswordResetViewModel
import com.cvindosistem.simpeldesa.auth.presentation.auth.resetpassword.ResetPasswordScreen
import com.cvindosistem.simpeldesa.core.data.local.preferences.UserPreferences
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.LayananPersuratanScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.SuratDetailScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.SuratKeteranganScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.SuratLainnyaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.SuratPengantarScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.SuratRekomendasiScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.SKDomisiliScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.SKResiKTPSementaraScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.SKStatusPerkawinanScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.SKTidakMampuScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skbedaidentitas.SKBedaIdentitasScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skberpergian.SKBerpergianScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skdomisiliperusahaan.SKDomisiliPerusahaanScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skgaib.SKGhaibScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skjandaduda.SKJandaDudaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkelahiran.SKKelahiranScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skkematian.SKKematianScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skpenghasilan.SKPenghasilanScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.sktidakmasukkerja.SKTidakMasukKerjaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skusaha.SKUsahaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.suratkuasa.SuratKuasaScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratlainnya.surattugas.SuratTugasScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.SPCatatanKepolisianScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.spkehilangan.SPKehilanganScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratpengantar.sppernikahan.SPPernikahanScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratrekomendasi.SRKeramaianScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratDetailViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPCatatanKepolisianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPPernikahanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratrekomendasi.SRKeramaianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.SuratSayaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKBedaIdentitasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKBerpergianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliPerusahaanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKDomisiliViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKGhaibViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKJandaDudaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKelahiranViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKKematianViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKPenghasilanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKResiKTPSementaraViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKStatusPerkawinanViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKTidakMampuViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKTidakMasukKerjaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratketerangan.SKUsahaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratKuasaViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratlainnya.SuratTugasViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.viewmodel.suratpengantar.SPKehilanganViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.MainScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.main.home.viewmodel.HomeViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.main.profile.viewmodel.ProfileViewModel
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.notification.NotificationScreen
import com.cvindosistem.simpeldesa.main.presentation.screens.submain.home.village.InformasiDesaScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    userPreferences: UserPreferences = koinInject()
) {
    val isLoggedIn = userPreferences.isLoggedIn()

    val initialStartDestination = when {
        !isLoggedIn -> Screen.Login.route
        else -> Screen.MainScreen.route
    }

//    val initialStartDestination = Screen.MainScreen.route

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
            val homeViewModel: HomeViewModel = koinViewModel()
            val profileViewModel: ProfileViewModel = koinViewModel()
            val suratSayaViewModel: SuratSayaViewModel = koinViewModel()

            MainScreen(
                navController = navController,
                homeViewModel = homeViewModel,
                profileViewModel = profileViewModel,
                suratSayaViewModel = suratSayaViewModel
            )
        }

        // ===== Layanan Persuratan =====
        composable(Screen.LayananPersuratan.route) {
            val suratSayaViewModel: SuratSayaViewModel = koinViewModel()

            LayananPersuratanScreen(
                navController = navController,
                suratSayaViewModel = suratSayaViewModel
            )
        }

        composable(
            route = "${Screen.DetailSurat.route}/{Id}",
            arguments = listOf(
                navArgument("Id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("Id") ?: ""
            val detailViewModel: SuratDetailViewModel = koinViewModel()

            SuratDetailScreen(
                navController = navController,
                suratId = id,
                viewModel = detailViewModel
            )
        }

        composable(Screen.SuratKeterangan.route) {
            SuratKeteranganScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(Screen.SuratPengantar.route) {
            SuratPengantarScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(Screen.SuratRekomendasi.route) {
            SuratRekomendasiScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(Screen.SuratLainnya.route) {
            SuratLainnyaScreen(
                onNavigateBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable(Screen.SKDomisiliScreen.route) {
            val skDomisiliViewModel: SKDomisiliViewModel = koinViewModel()
            SKDomisiliScreen(
                skDomisiliViewModel = skDomisiliViewModel,
                navController = navController
            )
        }

        composable(Screen.SKTidakMampu.route) {
            val skTidakMampuViewModel: SKTidakMampuViewModel = koinViewModel()
            SKTidakMampuScreen(
                sKTidakMampuViewModel = skTidakMampuViewModel,
                navController = navController
            )
        }

        composable(Screen.SKKematian.route) {
            val skKematianViewModel: SKKematianViewModel = koinViewModel()
            SKKematianScreen(
                skKematianViewModel = skKematianViewModel,
                navController = navController
            )
        }

        composable(Screen.SKKelahiran.route) {
            val skKelahiranViewModel: SKKelahiranViewModel = koinViewModel()
            SKKelahiranScreen(
                skKelahiranViewModel = skKelahiranViewModel,
                navController = navController
            )
        }

        composable(Screen.SKUsaha.route) {
            val skUsahaViewModel: SKUsahaViewModel = koinViewModel()
            SKUsahaScreen(
                viewModel = skUsahaViewModel,
                navController = navController
            )
        }

        composable(Screen.SKBerpergian.route) {
            val skBerpergianViewModel: SKBerpergianViewModel = koinViewModel()
            SKBerpergianScreen(
                skBerpergianViewModel = skBerpergianViewModel,
                navController = navController
            )
        }

        composable(Screen.SKTidakMasukKerja.route) {
            val skTidakMasukKerjaViewModel: SKTidakMasukKerjaViewModel = koinViewModel()
            SKTidakMasukKerjaScreen(
                skTidakMasukKerjaViewModel = skTidakMasukKerjaViewModel,
                navController = navController
            )
        }

        composable(Screen.SKPenghasilan.route) {
            val skPenghasilanViewModel: SKPenghasilanViewModel = koinViewModel()
            SKPenghasilanScreen(
                skPenghasilanViewModel = skPenghasilanViewModel,
                navController = navController
            )
        }

        composable(Screen.SKStatusPerkawinan.route) {
            val skStatusPerkawinanViewModel: SKStatusPerkawinanViewModel = koinViewModel()
            SKStatusPerkawinanScreen(
                sKStatusPerkawinanViewModel = skStatusPerkawinanViewModel,
                navController = navController
            )
        }

        composable(Screen.SKResiKTPSementara.route) {
            val skResiKTPSementaraViewModel: SKResiKTPSementaraViewModel = koinViewModel()
            SKResiKTPSementaraScreen(
                sKResiKTPSementaraViewModel = skResiKTPSementaraViewModel,
                navController = navController
            )
        }

        composable(Screen.SKDomisiliPerusahaan.route) {
            val skDomisiliPerusahaanViewModel: SKDomisiliPerusahaanViewModel = koinViewModel()
            SKDomisiliPerusahaanScreen(
                viewModel = skDomisiliPerusahaanViewModel,
                navController = navController
            )
        }

        composable(Screen.SKJandaDuda.route) {
            val skJandaDudaViewModel: SKJandaDudaViewModel = koinViewModel()
            SKJandaDudaScreen(
                skJandaDudaViewModel = skJandaDudaViewModel,
                navController = navController
            )
        }

        composable(Screen.SKBedaIdentitas.route) {
            val skBedaIdentitasViewModel: SKBedaIdentitasViewModel = koinViewModel()
            SKBedaIdentitasScreen(
                skBedaIdentitasViewModel = skBedaIdentitasViewModel,
                navController = navController
            )
        }

        composable(Screen.SKGhaib.route) {
            val skGhaibViewModel: SKGhaibViewModel = koinViewModel()
            SKGhaibScreen(
                skGhaibViewModel = skGhaibViewModel,
                navController = navController
            )
        }

        composable(Screen.SPCatatanKepolisian.route) {
            val spCatatanKepolisianViewModel: SPCatatanKepolisianViewModel = koinViewModel()
            SPCatatanKepolisianScreen(
                navController = navController,
                spCatatanKepolisianViewModel = spCatatanKepolisianViewModel
            )
        }

        composable(Screen.SPKehilangan.route) {
            val spKehilanganViewModel: SPKehilanganViewModel = koinViewModel()

            SPKehilanganScreen(
                spKehilanganViewModel = spKehilanganViewModel,
                navController = navController
            )
        }

        composable(Screen.SPPernikahan.route) {
            val spPernikahanViewModel: SPPernikahanViewModel = koinViewModel()
            SPPernikahanScreen(
                spPernikahanViewModel = spPernikahanViewModel,
                navController = navController
            )
        }

        composable(Screen.SRKeramaian.route) {
            val srKeramaianViewModel: SRKeramaianViewModel = koinViewModel()
            SRKeramaianScreen(
                navController = navController,
                srKeramaianViewModel = srKeramaianViewModel
            )
        }

        composable(Screen.SuratKuasa.route) {
            val suratKuasaViewModel: SuratKuasaViewModel = koinViewModel()
            SuratKuasaScreen(
                suratKuasaViewModel = suratKuasaViewModel,
                navController = navController
            )
        }

        composable(Screen.SuratTugas.route) {
            val suratTugasViewModel: SuratTugasViewModel = koinViewModel()
            SuratTugasScreen(
                suratTugasViewModel = suratTugasViewModel,
                navController = navController
            )
        }

        composable(Screen.Notification.route) {
            NotificationScreen(
                navController = navController
            )
        }

        composable(Screen.VillageInformation.route) {
            InformasiDesaScreen(
                navController = navController
            )
        }
    }
}
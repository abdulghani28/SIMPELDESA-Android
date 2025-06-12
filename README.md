
![carbon](https://github.com/user-attachments/assets/8a4d103f-db17-42f3-8792-3309f74cb2d1)

🔧 app/

Folder utama berisi keseluruhan source code dan konfigurasi proyek Android.

📄 manifests/
* AndroidManifest.xml: Konfigurasi deklaratif aplikasi seperti permission, activity, service, receiver, dll.

🧠 kotlin+java/com.cvindosistem.simpeldesa/
Berisi semua logika aplikasi berdasarkan domain dan fitur.

📁 app/
* App.kt: Entry point aplikasi (kelas Application).
* MainActivity: Host utama Compose UI.
* di/: Dependency Injection untuk modul app seperti modul umum, permission handler, dan scanning activity.

🔐 auth/
Modul otentikasi pengguna (login, reset password, FCM token, dll).
* data/remote/api/: API interface untuk login dan user.
* dto/: Data Transfer Objects (untuk request & response dari API).
    * login/, resetpassword/, fcm/, user.response/
* repository/: Implementasi repository dari domain.
* di/: Modul Koin untuk auth.
* domain/model/: Hasil domain (sealed class seperti LoginResult, LogoutResult, dll).
* domain/usecases/: Use case untuk autentikasi.
* presentation.auth/: UI logic berbasis Jetpack Compose.
    * login/: Komponen visual (form, header, link sosial).
    * resetpassword/: Halaman dan viewmodel reset password.

⚙️ core/
Modul inti aplikasi yang digunakan lintas fitur.
* components/: Komponen UI yang bisa digunakan ulang.
* data/fcm/: Integrasi Firebase Cloud Messaging (FCM).
* local.preferences/: Manajemen data lokal menggunakan SharedPreferences (mis. token, license code).
* remote/api/: API umum seperti FileApi.
* remote/interceptor/: Interceptor seperti token autentikasi.
* di/: Modul umum seperti CoreModule, NetworkModule.
* domain/model/, repository/, usecases/: Domain dan use case untuk file upload.
* helpers/: Fungsi bantu seperti format tanggal, image loader, dsb.

📩 main/
Modul utama aplikasi setelah pengguna login.
* data/remote/api/: API Referensi dan Surat.
* data/dto/: DTO untuk data surat & referensi.
* repository/: Implementasi repository untuk surat dan referensi.
* di/: Modul Koin untuk fitur main.
* domain/: Model domain dan use case untuk surat dan referensi.
* navigation/: Navigasi Compose (NavGraph, Screen).
* presentation/:
    * components/: Komponen UI surat seperti card, dialog, dsb.
    * screens/: Layar utama aplikasi:
        * blogdesa/: Blog & postingan desa.
        * donasi/: Donasi desa.
        * laporpemdes/: Laporan ke pemerintah desa.
        * layanankesehatan/: Submenu layanan kesehatan.
        * layananpersuratan/: Layanan persuratan (screen & viewmodel).
        * main/: Beranda dan profil pengguna.
        * submain/: Aktivitas lainnya.

🎨 ui.theme/
Tema aplikasi berbasis Material 3: warna, tipografi, dan tema global.

🧪 com.cvindosistem.simpeldesa (test/androidTest)/
* Unit test (test) dan instrumented test (androidTest).

🔧 Gradle & Konfigurasi
* build.gradle.kts (Project & Module): Konfigurasi build dan dependency.
* libs.versions.toml: Dependency version catalog.
* proguard-rules.pro: Konfigurasi ProGuard (minify & obfuscation).
* settings.gradle.kts: Setting proyek dan sub-modul.

🧭 Arsitektur
📚 Clean Architecture
* Presentation → Compose UI + ViewModel.
* Domain → UseCase + Model + Repository Interface.
* Data → DTO + Repository Implementation + API Interface.
🔗 Dependency Injection
Menggunakan Koin untuk dependency injection di setiap modul (di/ folder).

✅ Catatan Implementasi
* Validasi form: Tersebar di ViewModel, memastikan user experience responsif.
* FCM: Token FCM dikelola secara otomatis setelah login.
* UserPreferences: Data lokal disimpan pakai SharedPreferences.

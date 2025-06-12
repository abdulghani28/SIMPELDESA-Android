package com.cvindosistem.simpeldesa.auth.domain.model

/**
 * SEMUA RESULT MEMILIKI POLA SERUPA
 */

/**
 * Representasi hasil dari proses penyimpanan atau pembaruan token FCM.
 *
 * Gunakan sealed class ini untuk menangani hasil proses token FCM dalam ViewModel atau UseCase.
 */
sealed class FcmTokenResult {
    /**
     * Menandakan bahwa token FCM berhasil disimpan atau diperbarui.
     */
    object Success : FcmTokenResult()

    /**
     * Menandakan bahwa proses penyimpanan atau pembaruan token FCM gagal.
     *
     * @param message Pesan kesalahan yang dapat ditampilkan ke pengguna atau digunakan untuk debugging.
     */
    data class Error(val message: String) : FcmTokenResult()
}

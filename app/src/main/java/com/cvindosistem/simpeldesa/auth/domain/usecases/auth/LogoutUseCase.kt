package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.domain.model.LogoutResult

/**
 * SEMUA USE CASE MEMILIKI POLA SERUPA
 */

/**
 * Use case untuk melakukan logout pengguna.
 *
 * Memanggil metode logout pada [AuthRepository] dan mengembalikan hasilnya dalam bentuk [LogoutResult].
 *
 * @property authRepository Repository untuk autentikasi pengguna.
 */
class LogoutUseCase(private val authRepository: AuthRepository) {
    /**
     * Menjalankan proses logout pengguna.
     *
     * @return [LogoutResult] hasil dari proses logout.
     */
    suspend operator fun invoke(): LogoutResult {
        return authRepository.logout()
    }
}

package com.cvindosistem.simpeldesa.auth.domain.usecases.auth

import com.cvindosistem.simpeldesa.auth.data.repository.AuthRepository
import com.cvindosistem.simpeldesa.auth.domain.model.LoginResult

/**
 * SEMUA USE CASE MEMILIKI POLA SERUPA
 */

/**
 * Use case untuk melakukan proses login pengguna.
 *
 * Memanggil metode login pada [AuthRepository] dan mengembalikan hasilnya dalam bentuk [LoginResult].
 *
 * @property authRepository Repository untuk autentikasi pengguna.
 */
class LoginUseCase(private val authRepository: AuthRepository) {
    /**
     * Menjalankan proses login dengan email, password, dan kode lisensi.
     *
     * @return [LoginResult] hasil dari proses login.
     */
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return authRepository.login(email, password)
    }
}

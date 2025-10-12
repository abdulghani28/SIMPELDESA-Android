package com.cvindosistem.simpeldesa.auth.data.remote.dto.auth.login

data class LoginResponse(
    val `data`: Data,
    val token: String
) {
    data class Data(
        val agama_id: String?,
        val alamat: String?,
        val created_at: String?,
        val deleted_at: Any?,
        val disabilitas_id: String?,
        val email: String?,
        val golongan_darah: String?,
        val id: String?,
        val is_active: Boolean?,
        val jenis_kelamin: String?,
        val kewarganegaraan: String?,
        val nama_warga: String?,
        val nik: String?,
        val no_kk: String?,
        val no_telp: String?,
        val pekerjaan_id: String?,
        val pendidikan_id: String?,
        val photo: Any?,
        val status_hubungan: String?,
        val status_kawin_id: String?,
        val tanggal_lahir: String?,
        val tempat_lahir: String?,
        val updated_at: String?
    )
}

data class ErrorResponse(
    val error: String,
    val message: String
)
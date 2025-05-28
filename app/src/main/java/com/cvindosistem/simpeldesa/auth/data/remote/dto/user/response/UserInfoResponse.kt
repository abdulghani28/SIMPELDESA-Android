package com.cvindosistem.simpeldesa.auth.data.remote.dto.user.response

data class UserInfoResponse(
    val `data`: Data
) {
    data class Data(
        val agama: String,
        val agama_id: String,
        val alamat: String,
        val created_at: String,
        val deleted_at: String?,
        val disabilitas_id: String,
        val dusun: String,
        val email: String,
        val golongan_darah: String,
        val id: String,
        val is_active: Boolean,
        val jenis_kelamin: String,
        val kewarganegaraan: String,
        val nama_warga: String,
        val nik: String,
        val no_kk: String,
        val no_telp: String,
        val pekerjaan: String,
        val pekerjaan_id: String,
        val pendidikan: String,
        val pendidikan_id: String,
        val photo: String?,
        val rt: String,
        val rw: String,
        val status_hubungan: String,
        val status_kawin: String,
        val status_kawin_id: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
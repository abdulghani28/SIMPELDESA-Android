package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratrekomendasi

data class SRKeramaianResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val created_at: String,
        val deleted_at: Any,
        val dimulai: String,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val hari: String,
        val id: String,
        val jenis_kelamin: String,
        val kontak: String,
        val nama: String,
        val nama_acara: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val penanggung_jawab: String,
        val selesai: String,
        val status: String,
        val tanggal: String,
        val tanggal_lahir: String,
        val tempat_acara: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
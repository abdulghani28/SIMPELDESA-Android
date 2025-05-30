package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKGhaibResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val hilang_sejak: String,
        val hubungan_id: String,
        val id: String,
        val jenis_kelamin: String,
        val keperluan: String,
        val nama: String,
        val nama_orang_hilang: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val status: String,
        val updated_at: String,
        val usia: Int
    )
}
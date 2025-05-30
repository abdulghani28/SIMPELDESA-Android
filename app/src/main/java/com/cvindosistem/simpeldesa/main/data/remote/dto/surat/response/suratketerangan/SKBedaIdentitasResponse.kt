package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKBedaIdentitasResponse(
    val `data`: Data
) {
    data class Data(
        val alamat_1: String,
        val alamat_2: String,
        val created_at: String,
        val deleted_at: Any,
        val diajukan_oleh_nik: String,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val keperluan: String,
        val nama_1: String,
        val nama_2: String,
        val nomor_1: String,
        val nomor_2: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val perbedaan_id: String,
        val status: String,
        val tanggal_lahir_1: String,
        val tanggal_lahir_2: String,
        val tempat_lahir_1: String,
        val tempat_lahir_2: String,
        val tercantum_id: String,
        val tercantum_id_2: String,
        val updated_at: String
    )
}
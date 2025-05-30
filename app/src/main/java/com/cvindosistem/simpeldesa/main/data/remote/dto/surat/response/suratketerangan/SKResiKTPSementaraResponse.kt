package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKResiKTPSementaraResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id: String,
        val alamat: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jenis_kelamin: String,
        val keperluan: String,
        val nama: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
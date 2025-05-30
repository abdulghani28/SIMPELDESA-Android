package com.cvindosistem.simpeldesa.main.data.remote.dto.surat

data class SuratDetailResponse(
    val `data`: Data,
    val meta: Meta
) {
    data class Data(
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
        val nama_pejabat: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val updated_at: String
    )

    data class Meta(
        val jenis_surat: String
    )
}
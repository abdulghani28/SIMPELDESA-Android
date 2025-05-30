package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKUsahaResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val alamat_usaha: String,
        val bidang_usaha_id: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jenis_kelamin: String,
        val jenis_usaha_id: String,
        val keperluan: String,
        val nama: String,
        val nama_usaha: String,
        val nik: String,
        val nomor_surat: String,
        val npwp: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val updated_at: String,
        val warga_desa: Boolean
    )
}
package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKLahirMatiResponse(
    val `data`: Data
) {
    data class Data(
        val agama_ibu_id: String,
        val alamat: String,
        val alamat_ibu: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val hubungan_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val keperluan: String,
        val kewarganegaraan_ibu_id: String,
        val kode_belakang: String,
        val kode_depan: String,
        val lama_dikandung: String,
        val nama: String,
        val nama_ibu: String,
        val nik: String,
        val nik_ibu: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan_ibu: String,
        val status: String,
        val tanggal_lahir: Any?,
        val tanggal_lahir_ibu: String,
        val tanggal_meninggal: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val tempat_lahir_ibu: String,
        val tempat_meninggal: String,
        val updated_at: String
    )
}
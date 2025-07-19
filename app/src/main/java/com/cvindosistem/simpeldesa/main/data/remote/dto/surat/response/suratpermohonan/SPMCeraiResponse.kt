package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan

data class SPMCeraiResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id_istri: String,
        val agama_id_suami: String,
        val alamat_istri: String,
        val alamat_suami: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val keperluan: String,
        val kode_belakang: String,
        val kode_depan: String,
        val nama_istri: String,
        val nama_suami: String,
        val nik_istri: String,
        val nik_suami: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan_istri: String,
        val pekerjaan_suami: String,
        val sebab_cerai: String,
        val status: String,
        val tanggal_lahir_istri: String,
        val tanggal_lahir_suami: String,
        val tanggal_surat: String,
        val tempat_lahir_istri: String,
        val tempat_lahir_suami: String,
        val updated_at: String
    )
}
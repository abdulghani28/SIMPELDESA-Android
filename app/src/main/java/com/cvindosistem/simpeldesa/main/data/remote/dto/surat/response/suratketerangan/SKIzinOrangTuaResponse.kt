package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKIzinOrangTuaResponse(
    val `data`: Data
) {
    data class Data(
        val agama_2_id: String,
        val agama_id: String,
        val alamat: String,
        val alamat_2: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diberi_izin: String,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val keperluan: String,
        val kewarganegaraan: String,
        val kewarganegaraan_2: String,
        val kode_belakang: String,
        val kode_depan: String,
        val masa_kontrak: String,
        val memberi_izin: String,
        val nama: String,
        val nama_2: String,
        val nama_perusahaan: String,
        val negara_tujuan: String,
        val nik: String,
        val nik_2: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val pekerjaan_2: String,
        val status: String,
        val status_pekerjaan: String,
        val tanggal_lahir: String,
        val tanggal_lahir_2: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val tempat_lahir_2: String,
        val updated_at: String
    )
}
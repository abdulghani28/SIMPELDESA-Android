package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpengantar

data class SPPermohonanPenerbitanBukuPasLintasBatasResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id: String,
        val alamat: String,
        val anggota_keluarga: Any?,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val jenis_kelamin: String,
        val kepala_keluarga: String,
        val keperluan: String,
        val kewarganegaraan: String,
        val kode_belakang: String,
        val kode_depan: String,
        val nama: String,
        val nik: String,
        val no_kk: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val status_kawin_id: String,
        val tanggal_lahir: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
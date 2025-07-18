package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan

data class SPMDuplikatSuratNikahResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id: String,
        val alamat: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jenis_kelamin: String,
        val kecamatan_kua: String,
        val kepala_keluarga: String,
        val keperluan: String,
        val kewarganegaraan: String,
        val kode_belakang: String,
        val kode_depan: String,
        val nama: String,
        val nama_pasangan: String,
        val nik: String,
        val no_kk: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val pendidikan_id: String,
        val status: String,
        val tanggal_lahir: String,
        val tanggal_nikah: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
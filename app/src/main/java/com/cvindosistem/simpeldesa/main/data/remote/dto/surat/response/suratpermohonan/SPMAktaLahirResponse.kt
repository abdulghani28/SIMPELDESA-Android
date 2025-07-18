package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratpermohonan

data class SPMAktaLahirResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val alamat_anak: String,
        val alamat_orang_tua: String,
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
        val nama: String,
        val nama_anak: String,
        val nama_ayah: String,
        val nama_ibu: String,
        val nik: String,
        val nik_ayah: String,
        val nik_ibu: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tanggal_lahir_anak: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val tempat_lahir_anak: String,
        val updated_at: String
    )
}
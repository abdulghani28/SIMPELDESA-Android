package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKKelahiranResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val alamat_ayah: String,
        val alamat_ibu: String,
        val anak_ke: Int,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jam_lahir: String,
        val jenis_kelamin: String,
        val keperluan: String,
        val nama: String,
        val nama_ayah: String,
        val nama_ibu: String,
        val nik_ayah: String,
        val nik_ibu: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val status: String,
        val tanggal_lahir: String,
        val tanggal_lahir_ayah: String,
        val tanggal_lahir_ibu: String,
        val tempat_lahir: String,
        val tempat_lahir_ayah: String,
        val tempat_lahir_ibu: String,
        val updated_at: String
    )
}
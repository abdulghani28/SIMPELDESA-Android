package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKKelahiranRequest(
    val alamat_ayah: String,
    val alamat_ibu: String,
    val anak_ke: Int,
    val disahkan_oleh: String,
    val jam_lahir: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val nama: String,
    val nama_ayah: String,
    val nama_ibu: String,
    val nik_ayah: String,
    val nik_ibu: String,
    val tanggal_lahir: String,
    val tanggal_lahir_ayah: String,
    val tanggal_lahir_ibu: String,
    val tempat_lahir: String,
    val tempat_lahir_ayah: String,
    val tempat_lahir_ibu: String
)
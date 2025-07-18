package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan

data class SPMDuplikatKelahiranRequest(
    val agama_id_anak: String,
    val alamat: String,
    val alamat_anak: String,
    val alamat_ayah: String,
    val alamat_ibu: String,
    val jenis_kelamin_anak: String,
    val keperluan: String,
    val nama: String,
    val nama_anak: String,
    val nama_ayah: String,
    val nama_ibu: String,
    val nik: String,
    val nik_anak: String,
    val nik_ayah: String,
    val nik_ibu: String,
    val pekerjaan: String,
    val pekerjaan_ayah: String,
    val pekerjaan_ibu: String,
    val tanggal_lahir: String,
    val tanggal_lahir_anak: String,
    val tempat_lahir: String,
    val tempat_lahir_anak: String
)
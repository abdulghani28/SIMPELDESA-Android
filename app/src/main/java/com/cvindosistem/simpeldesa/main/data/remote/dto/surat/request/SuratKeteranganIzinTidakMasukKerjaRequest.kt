package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request

data class SuratIzinTidakMasukKerjaRequest(
    val agama_id: String,
    val alamat: String,
    val alasan_izin: String,
    val disahkan_oleh: String,
    val jabatan: String,
    val jenis_kelamin: String,
    val keperluan: String,
    val lama: Int,
    val nama: String,
    val nama_perusahaan: String,
    val nik: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String,
    val terhitung_dari: String
)
package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKDomisiliPerusahaanRequest(
    val agama_id: String,
    val alamat: String,
    val alamat_identitas: String,
    val disahkan_oleh: String,
    val jenis_kelamin: String,
    val jumlah_pengikut: Int,
    val keperluan: String,
    val kewarganegaraan: String,
    val nama: String,
    val nik: String,
    val pekerjaan: String,
    val tanggal_lahir: String,
    val tempat_lahir: String,
    val warga_desa: Boolean
)
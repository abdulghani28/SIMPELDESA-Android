package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratketerangan

data class SKKematianRequest(
    val alamat: String,
    val alamat_mendiang: String,
    val disahkan_oleh: String,
    val hari_meninggal: String,
    val hubungan_id: String,
    val jenis_kelamin_mendiang: String,
    val keperluan: String,
    val nama: String,
    val nama_mendiang: String,
    val nik_mendiang: String,
    val sebab_meninggal: String,
    val tanggal_lahir_mendiang: String,
    val tanggal_meninggal: String,
    val tempat_lahir_mendiang: String,
    val tempat_meninggal: String
)
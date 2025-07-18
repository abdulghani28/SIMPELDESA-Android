package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar

data class SPPermohonanPenerbitanBukuPasLintasBatasRequest(
    val agama_id: String,
    val alamat: String,
    val anggota_keluarga: List<AnggotaKeluarga>,
    val jenis_kelamin: String,
    val kepala_keluarga: String,
    val keperluan: String,
    val kewarganegaraan: String,
    val nama: String,
    val nik: String,
    val no_kk: String,
    val pekerjaan: String,
    val status_kawin_id: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
) {
    data class AnggotaKeluarga(
        val keterangan: String,
        val nik: String
    )
}
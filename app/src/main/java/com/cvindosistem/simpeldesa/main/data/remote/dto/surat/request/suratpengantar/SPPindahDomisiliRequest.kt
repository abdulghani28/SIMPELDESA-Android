package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpengantar

data class SPPindahDomisiliRequest(
    val agama_id: String,
    val alamat: String,
    val alamat_pindah: String,
    val alasan_pindah: String,
    val disahkan_oleh: String,
    val jumlah_anggota: Int,
    val keperluan: String,
    val nama: String,
    val nik: String,
    val tanggal_lahir: String,
    val tempat_lahir: String
)
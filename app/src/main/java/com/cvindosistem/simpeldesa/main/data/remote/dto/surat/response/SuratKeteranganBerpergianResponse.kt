package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response

data class SuratKeteranganBerpergianResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jenis_kelamin: String,
        val jumlah_pengikut: Int,
        val keperluan: String,
        val lama: Int,
        val maksud_tujuan: String,
        val nama: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val satuan_lama: String,
        val status: String,
        val tanggal_keberangkatan: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val tempat_tujuan: String,
        val updated_at: String
    )
}
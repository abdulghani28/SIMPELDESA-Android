package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratlainnya

data class SuratKuasaResponse(
    val `data`: Data
) {
    data class Data(
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val disposisi_kuasa_sebagai: String,
        val disposisi_kuasa_untuk: String,
        val id: String,
        val jabatan_pemberi: String,
        val jabatan_penerima: String,
        val nama_pemberi: String,
        val nama_penerima: String,
        val nik_pemberi: String,
        val nik_penerima: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val status: String,
        val updated_at: String
    )
}
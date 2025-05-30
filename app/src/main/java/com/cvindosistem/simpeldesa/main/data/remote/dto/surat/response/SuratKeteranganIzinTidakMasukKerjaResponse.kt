package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response

data class SuratKeteranganIzinTidakMasukKerjaResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id: String,
        val alamat: String,
        val alasan_izin: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val jabatan: String,
        val jenis_kelamin: String,
        val keperluan: String,
        val lama: Int,
        val nama: String,
        val nama_perusahaan: String,
        val nik: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tempat_lahir: String,
        val terhitung_dari: String,
        val updated_at: String
    )
}
package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKKematianResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val alamat_mendiang: String,
        val created_at: String,
        val deleted_at: Any,
        val diajukan_oleh_nik: String,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val hari_meninggal: String,
        val hubungan_id: String,
        val id: String,
        val jenis_kelamin_mendiang: String,
        val keperluan: String,
        val nama: String,
        val nama_mendiang: String,
        val nik_mendiang: String,
        val nomor_surat: String,
        val organisasi_id: String,
        val sebab_meninggal: String,
        val status: String,
        val tanggal_lahir_mendiang: String,
        val tanggal_meninggal: String,
        val tempat_lahir_mendiang: String,
        val tempat_meninggal: String,
        val updated_at: String
    )
}
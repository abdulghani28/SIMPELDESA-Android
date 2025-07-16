package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKJualBeliResponse(
    val `data`: Data
) {
    data class Data(
        val alamat_1: String,
        val alamat_2: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val jenis_barang: String,
        val jenis_kelamin_1: String,
        val jenis_kelamin_2: String,
        val kode_belakang: String,
        val kode_depan: String,
        val nama_1: String,
        val nama_2: String,
        val nik_1: String,
        val nik_2: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan_1: String,
        val pekerjaan_2: String,
        val rincian_barang: String,
        val status: String,
        val tanggal_lahir_1: String,
        val tanggal_lahir_2: String,
        val tanggal_surat: String,
        val tempat_lahir_1: String,
        val tempat_lahir_2: String,
        val updated_at: String
    )
}
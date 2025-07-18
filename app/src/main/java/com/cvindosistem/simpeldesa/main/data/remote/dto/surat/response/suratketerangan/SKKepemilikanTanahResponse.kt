package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKKepemilikanTanahResponse(
    val `data`: Data
) {
    data class Data(
        val alamat: String,
        val asal_kepemilikan_tanah: String,
        val atas_nama: String,
        val bagian_surat_id: String,
        val batas_barat: String,
        val batas_selatan: String,
        val batas_timur: String,
        val batas_utara: String,
        val bukti_kepemilikan_tanah: String,
        val bukti_kepemilikan_tanah_tanah: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val jenis_kelamin: String,
        val jenis_tanah: String,
        val keperluan: String,
        val kode_belakang: String,
        val kode_depan: String,
        val luas_tanah: String,
        val nama: String,
        val nik: String,
        val nomor_bukti_kepemilikan: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val status: String,
        val tanggal_lahir: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val updated_at: String
    )
}
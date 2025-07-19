package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.response.suratketerangan

data class SKPengantarCeraiRujukResponse(
    val `data`: Data
) {
    data class Data(
        val agama_id: String,
        val agama_id_pasangan: String,
        val alamat: String,
        val alamat_pasangan: String,
        val bagian_surat_id: String,
        val created_at: String,
        val deleted_at: Any?,
        val diproses_oleh: String,
        val diproses_oleh_id: String,
        val disahkan_oleh: String,
        val disahkan_oleh_id: String,
        val id: String,
        val is_warga_desa: Boolean,
        val keperluan: String,
        val kewarganegaraan: String,
        val kewarganegaraan_pasangan: String,
        val kode_belakang: String,
        val kode_depan: String,
        val nama: String,
        val nama_ayah: String,
        val nama_ayah_pasangan: String,
        val nama_pasangan: String,
        val nik: String,
        val nik_ayah: String,
        val nik_ayah_pasangan: String,
        val nik_pasangan: String,
        val nomor_surat: String,
        val nomor_surat_deprecated: String,
        val organisasi_id: String,
        val pekerjaan: String,
        val pekerjaan_pasangan: String,
        val status: String,
        val tanggal_lahir: String,
        val tanggal_lahir_pasangan: String,
        val tanggal_surat: String,
        val tempat_lahir: String,
        val tempat_lahir_pasangan: String,
        val updated_at: String
    )
}
package com.cvindosistem.simpeldesa.main.data.remote.dto.surat.request.suratpermohonan

data class SPMCeraiRequest(
    val agama_id_istri: String,
    val agama_id_suami: String,
    val alamat_istri: String,
    val alamat_suami: String,
    val keperluan: String,
    val nama_istri: String,
    val nama_suami: String,
    val nik_istri: String,
    val nik_suami: String,
    val pekerjaan_istri: String,
    val pekerjaan_suami: String,
    val sebab_cerai: String,
    val tanggal_lahir_istri: String,
    val tanggal_lahir_suami: String,
    val tempat_lahir_istri: String,
    val tempat_lahir_suami: String
)
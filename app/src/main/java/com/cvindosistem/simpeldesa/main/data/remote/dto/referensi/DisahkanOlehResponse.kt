package com.cvindosistem.simpeldesa.main.data.remote.dto.referensi

data class DisahkanOlehResponse(
    val `data`: List<Data>
) {
    data class Data(
        val id: String,
        val jabatan: String,
        val nama_pejabat: String
    )
}
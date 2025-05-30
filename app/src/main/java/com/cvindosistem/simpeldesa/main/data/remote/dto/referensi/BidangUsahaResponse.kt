package com.cvindosistem.simpeldesa.main.data.remote.dto.referensi

data class BidangUsahaResponse(
    val `data`: List<Data>
) {
    data class Data(
        val id: String,
        val nama: String
    )
}
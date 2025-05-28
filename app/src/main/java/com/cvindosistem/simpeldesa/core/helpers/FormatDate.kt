package com.cvindosistem.simpeldesa.core.helpers

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatTanggalLahir(tanggalLahir: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(tanggalLahir)
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale("id", "ID"))
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        tanggalLahir // fallback jika parsing gagal
    }
}
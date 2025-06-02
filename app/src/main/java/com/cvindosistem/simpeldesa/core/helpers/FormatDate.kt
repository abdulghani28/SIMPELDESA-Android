package com.cvindosistem.simpeldesa.core.helpers

import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun dateFormatterToApiFormat(dateString: String): String {
    return try {
        if (dateString.isBlank()) return ""

        val parsedDate = OffsetDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        parsedDate.format(formatter)
    } catch (e: Exception) {
        dateString
    }
}

fun formatDateTime(isoString: String): String {
    val zonedDateTime = ZonedDateTime.parse(isoString)
        .withZoneSameInstant(java.time.ZoneId.systemDefault()) // konversi ke zona lokal
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd-MMM-yyyy, HH:mm", Locale("id"))
    return zonedDateTime.format(formatter)
}
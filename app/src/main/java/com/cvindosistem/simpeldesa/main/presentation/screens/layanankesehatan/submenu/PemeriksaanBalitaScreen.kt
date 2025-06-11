package com.cvindosistem.simpeldesa.main.presentation.screens.layanankesehatan.submenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.InfoRow
import com.cvindosistem.simpeldesa.core.components.RecordsScreen
import com.cvindosistem.simpeldesa.core.components.TwoColumnInfoRow

@Composable
fun BalitaScreen(navController: NavController) {
    val records = listOf(
        BalitaRecord(1, "08/12/2024", "Mariyani Tika", "Mario Teguh", "Laki-laki", "8 Bulan", "4 kg", "60 cm", "Sudah", "Sehat", "N (berat badan naik)"),
        BalitaRecord(2, "08/12/2024", "Mariyani Tika", "Mario Teguh", "Laki-laki", "8 Bulan", "4 kg", "60 cm", "Sudah", "Sehat", "T (berat badan tetap/turun)")
    )

    RecordsScreen(
        title = "Balita",
        records = records,
        navController = navController,
        route = "",
        getDate = { it.date },
    ) { record ->
        BalitaRecordContent(record)
    }
}

data class BalitaRecord(
    val id: Int,
    val date: String,
    val namaIbu: String,
    val namaBalita: String,
    val jenisKelaminBalita: String,
    val usiaBalita: String,
    val beratBadanBalita: String,
    val tinggiBadanBalita: String,
    val imunisasi: String,
    val statusGizi: String,
    val hasilPemeriksaan: String
)

@Composable
fun BalitaRecordContent(record: BalitaRecord) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InfoRow(
            label = "Nama Ibu:",
            value = record.namaIbu
        )

        InfoRow(
            label = "Nama Balita:",
            value = record.namaBalita
        )

        TwoColumnInfoRow(
            leftLabel = "Jenis Kelamin Balita:",
            leftValue = record.jenisKelaminBalita,
            rightLabel = "Usia Balita:",
            rightValue = record.usiaBalita
        )

        TwoColumnInfoRow(
            leftLabel = "Berat Badan Balita:",
            leftValue = record.beratBadanBalita,
            rightLabel = "Tinggi Badan Balita:",
            rightValue = record.tinggiBadanBalita
        )

        TwoColumnInfoRow(
            leftLabel = "Imunisasi:",
            leftValue = record.imunisasi,
            rightLabel = "Status Gizi:",
            rightValue = record.statusGizi
        )

        InfoRow(
            label = "Hasil Pemeriksaan:",
            value = record.hasilPemeriksaan
        )
    }
}
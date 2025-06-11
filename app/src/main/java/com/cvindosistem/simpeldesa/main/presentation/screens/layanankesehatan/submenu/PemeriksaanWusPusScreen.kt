package com.cvindosistem.simpeldesa.main.presentation.screens.layanankesehatan.submenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.main.presentation.components.InfoRow
import com.cvindosistem.simpeldesa.main.presentation.components.RecordsScreen
import com.cvindosistem.simpeldesa.main.presentation.components.TwoColumnInfoRow
import com.cvindosistem.simpeldesa.main.navigation.Screen

data class WusPusRecord(
    val id: Int,
    val date: String,
    val namaIbu: String,
    val usia: Int,
    val tingkatKS: String,
    val jumlahAnakHidup: Int,
    val jumlahAnakMeninggal: Int,
    val jenisKontrasepsi: String,
    val kondisiKesehatan: String
)

// WUS/PUS Record Content
@Composable
fun WusPusRecordContent(record: WusPusRecord) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InfoRow(
            label = "Nama Ibu:",
            value = record.namaIbu
        )

        TwoColumnInfoRow(
            leftLabel = "Usia:",
            leftValue = "${record.usia} Tahun",
            rightLabel = "Tingkat KS:",
            rightValue = record.tingkatKS
        )

        TwoColumnInfoRow(
            leftLabel = "Jumlah Anak Hidup:",
            leftValue = record.jumlahAnakHidup.toString(),
            rightLabel = "Jumlah Anak Meninggal:",
            rightValue = record.jumlahAnakMeninggal.toString()
        )

        TwoColumnInfoRow(
            leftLabel = "Jenis Kontrasepsi:",
            leftValue = record.jenisKontrasepsi,
            rightLabel = "Kondisi Kesehatan:",
            rightValue = record.kondisiKesehatan
        )
    }
}

@Composable
fun WusPusScreen(navController: NavController) {
    val records = listOf(
        WusPusRecord(1, "08/12/2024", "Siti Nurbaya", 34, "II", 2, 0, "IUD", "Sehat"),
        WusPusRecord(2, "08/11/2024", "Siti Nurbaya", 34, "II", 2, 0, "IUD", "Kurang Sehat")
    )

    RecordsScreen(
        title = "WUS/PUS",
        records = records,
        navController = navController,
        route = Screen.TambahPemeriksaanWusPus.route,
        getDate = { it.date }
    ) { record ->
        WusPusRecordContent(record)
    }
}
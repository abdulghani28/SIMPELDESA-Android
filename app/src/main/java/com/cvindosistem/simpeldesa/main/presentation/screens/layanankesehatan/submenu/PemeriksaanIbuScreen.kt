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

@Composable
fun IbuScreen(navController: NavController) {
    val records = listOf(
        IbuRecord(1, "08/12/2024", "Ibu Melahirkan", "Siti Nurbaya", 34, "3 Bulan", null, "Sehat", "Sehat", null, null, null, null, "Iya"),
        IbuRecord(2, "08/12/2024", "Ibu Menyusui", "Siti Nurbaya", 34, null, null, "Sehat", null, "Sehat", "15 September 2024", "Normal", "Sudah", null),
        IbuRecord(3, "08/12/2024", "Ibu Hamil", "Siti Nurbaya", 34, null, "1 Bulan", "Sehat", "Sehat", null, "15 September 2024", null, null, null)
    )

    RecordsScreen(
        title = "Ibu",
        records = records,
        navController = navController,
        route = Screen.TambahPemeriksaanIbu.route,
        getDate = { it.date }
    ) { record ->
        IbuRecordContent(record)
    }
}

@Composable
fun IbuRecordContent(record: IbuRecord) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InfoRow(
            label = "Jenis Pemeriksaan:",
            value = record.jenisPemeriksaan
        )

        InfoRow(
            label = "Nama Ibu:",
            value = record.namaIbu
        )

        // Dynamic content based on examination type
        when (record.jenisPemeriksaan) {
            "Ibu Hamil" -> {
                TwoColumnInfoRow(
                    leftLabel = "Usia Ibu:",
                    leftValue = "${record.usiaIbu} Tahun",
                    rightLabel = "Usia Kehamilan:",
                    rightValue = record.usiaKehamilan ?: ""
                )

                TwoColumnInfoRow(
                    leftLabel = "Kondisi Ibu:",
                    leftValue = record.kondisiIbu,
                    rightLabel = "Kondisi Janin:",
                    rightValue = record.kondisiJanin ?: ""
                )

                record.tanggalPerkiraanPersalinan?.let {
                    InfoRow(
                        label = "Tanggal Perkiraan Persalinan:",
                        value = it
                    )
                }
            }

            "Ibu Melahirkan" -> {
                TwoColumnInfoRow(
                    leftLabel = "Usia Ibu:",
                    leftValue = "${record.usiaIbu} Tahun",
                    rightLabel = "Usia Bayi:",
                    rightValue = record.usiaBayi ?: ""
                )

                TwoColumnInfoRow(
                    leftLabel = "Kondisi Ibu:",
                    leftValue = record.kondisiIbu,
                    rightLabel = "Kondisi Janin:",
                    rightValue = record.kondisiJanin ?: ""
                )

                record.statusASIEksklusif?.let {
                    InfoRow(
                        label = "Status ASI Eksklusif:",
                        value = it
                    )
                }
            }

            "Ibu Menyusui" -> {
                InfoRow(
                    label = "Usia Ibu:",
                    value = "${record.usiaIbu} Tahun"
                )

                record.tanggalPerkiraanPersalinan?.let {
                    InfoRow(
                        label = "Tanggal Perkiraan Persalinan:",
                        value = it
                    )
                }

                TwoColumnInfoRow(
                    leftLabel = "Jenis Persalinan:",
                    leftValue = record.jenisPersalinan ?: "",
                    rightLabel = "Pemeriksaan Pasca Persalinan:",
                    rightValue = record.pemeriksaanPascaPersalinan ?: ""
                )

                TwoColumnInfoRow(
                    leftLabel = "Kondisi Ibu:",
                    leftValue = record.kondisiIbu,
                    rightLabel = "Kondisi Bayi:",
                    rightValue = record.kondisiBayi ?: ""
                )
            }
        }
    }
}

data class IbuRecord(
    val id: Int,
    val date: String,
    val jenisPemeriksaan: String,
    val namaIbu: String,
    val usiaIbu: Int,
    val usiaBayi: String? = null,
    val usiaKehamilan: String? = null,
    val kondisiIbu: String,
    val kondisiJanin: String? = null,
    val kondisiBayi: String? = null,
    val tanggalPerkiraanPersalinan: String? = null,
    val jenisPersalinan: String? = null,
    val pemeriksaanPascaPersalinan: String? = null,
    val statusASIEksklusif: String? = null
)
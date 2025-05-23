package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvindosistem.simpeldesa.main.presentation.components.LetterItem
import com.cvindosistem.simpeldesa.core.components.SearchBar

data class Letter(
    val status: String,
    val title: String,
    val description: String,
    val date: String
)

@Composable
fun LettersList() {
    val letters = listOf(
        Letter(
            status = "Menunggu",
            title = "Surat Keterangan Status Perkawinan",
            description = "Surat ini berhasil diajukan ke pihak desa dan akan segera diproses.",
            date = "25 September 2024"
        ),
        Letter(
            status = "Diproses",
            title = "Surat Pengantar Pernikahan",
            description = "Surat yang Anda ajukan saat ini sedang dalam proses oleh pihak desa.",
            date = "25 September 2024"
        ),
        Letter(
            status = "Selesai",
            title = "Surat Kuasa",
            description = "Surat yang Anda ajukan sudah selesai diproses dan siap untuk diambil di kantor desa.",
            date = "25 September 2024"
        ),
        Letter(
            status = "Ditolak",
            title = "Surat Keterangan Domisili",
            description = "Mohon maaf, surat yang Anda ajukan telah ditolak oleh pihak desa.",
            date = "25 September 2024"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar()
        LazyColumn {
            items(letters) { letter ->
                LetterItem(
                    status = letter.status,
                    title = letter.title,
                    description = letter.description,
                    date = letter.date
                )
            }
        }
    }
}

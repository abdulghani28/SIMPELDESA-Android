package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppContainer
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.CardTitleText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.components.TitleSmallText
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
internal fun BuatSuratContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    AppContainer(
        background = MaterialTheme.colorScheme.surfaceBright,
        modifier = modifier
    ) {
//        AppSearchBarAndFilter(
//            placeholder = "Cari Jenis Surat...",
//            value = "",
//            onValueSearch = { },
//            onFilterClick = { },
//            showFilter = true
//        )

        Spacer(modifier = Modifier.height(24.dp))

        SuratTypeSection(navController = navController)

        Spacer(modifier = Modifier.height(24.dp))

//        RecentLetterSection()
    }
}

@Composable
private fun SuratTypeSection(
    navController: NavController
) {
    Column {
        TitleMediumText("Pilih Jenis Surat")

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(getSuratTypes()) { suratType ->
                SuratTypeCard(
                    suratType = suratType,
                    navController = navController
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SuratTypeCard(
    suratType: SuratType,
    navController: NavController
) {
    AppCard(
        modifier = Modifier
            .clickable {
                navController.navigate(suratType.navigation)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(suratType.icon),
                contentDescription = suratType.title,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TitleSmallText(suratType.title)

            Spacer(modifier = Modifier.height(4.dp))

            BodySmallText(suratType.subtitle)
        }
    }
}

@Composable
private fun RecentLetterSection() {
    Column {
        TitleMediumText("Surat Terakhir Dibuat")

        RecentLetterCard()
    }
}

@Composable
private fun RecentLetterCard() {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFFF5F7FE),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "\uD83C\uDF0F",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CardTitleText("Surat Keterangan Domisili")

            Spacer(modifier = Modifier.height(6.dp))

            BodySmallText("Menyatakan tempat tinggal seseorang untuk keperluan administrasi.")
        }
    }
}

// Data class untuk tipe surat
data class SuratType(
    val title: String,
    val subtitle: String,
    val icon: Int,
    val navigation: String
)

// Function untuk mendapatkan data tipe surat
private fun getSuratTypes(): List<SuratType> {
    return listOf(
        SuratType(
            title = "Surat Keterangan",
            subtitle = "14 Pilihan Surat",
            icon = R.drawable.ic_surat_keterangan,
            navigation = Screen.SuratKeterangan.route
        ),
        SuratType(
            title = "Surat Pengantar",
            subtitle = "3 Pilihan Surat",
            icon = R.drawable.ic_surat_pengantar,
            navigation = Screen.SuratPengantar.route
        ),
        SuratType(
            title = "Surat Rekomendasi",
            subtitle = "1 Pilihan Surat",
            icon = R.drawable.ic_surat_rekomendasi,
            navigation = Screen.SuratRekomendasi.route
        ),
        SuratType(
            title = "Surat Lainnya",
            subtitle = "2 Pilihan Surat",
            icon = R.drawable.ic_surat_lainnya,
            navigation = Screen.SuratLainnya.route
        )
    )
}
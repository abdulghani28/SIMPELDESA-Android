package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodySmallText

@Composable
internal fun ServicesGrid() {
    val services = listOf(
        ServiceItem(R.drawable.ic_persuratan, "Layanan\nPersuratan") {},
        ServiceItem(R.drawable.ic_lapor, "Lapor\nPemdes") {},
        ServiceItem(R.drawable.ic_artikel, "Artikel &\nBlog Desa") {},
        ServiceItem(R.drawable.ic_donasi, "Donasi\nDesa") {},
        ServiceItem(R.drawable.ic_kesehatan, "Layanan\nKesehatan") {},
        ServiceItem(R.drawable.ic_bumdes, "Bumdes &\nPasar Desa") {}
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        AppCard(Modifier.fillMaxWidth()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(24.dp)
                    .height(260.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(services) { service ->
                    ServiceCard(service)
                }
            }
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { service.onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = service.iconResId),
            contentDescription = service.title,
            modifier = Modifier
                .size(36.dp)
                .padding(bottom = 8.dp)
        )

        BodySmallText(
            text = service.title,
            textAlign = TextAlign.Center
        )
    }
}

private data class ServiceItem(
    val iconResId: Int,
    val title: String,
    val onClick: () -> Unit
)

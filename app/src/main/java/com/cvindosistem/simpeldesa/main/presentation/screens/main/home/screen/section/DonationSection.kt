package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.NavigationSectionTitle


data class DonationItem(
    val imageRes: Int,
    val title: String,
    val collected: String,
    val target: String,
    val period: String
)

@Composable
private fun DonationCard(item: DonationItem) {
    AppCard(
        Modifier
            .width(280.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(item.title, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${item.collected} / ${item.target}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            BodySmallText("Periode: ${item.period}")
        }
    }
}

@Composable
internal fun DonationSection(title: String, donations: List<DonationItem>) {
    Column {
        NavigationSectionTitle(
            onSeeAllClick = {},
            titleSection = title
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            items(donations) { donation ->
                DonationCard(donation)
            }
        }
    }
}
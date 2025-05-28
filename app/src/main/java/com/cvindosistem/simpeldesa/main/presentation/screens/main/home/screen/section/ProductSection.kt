package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.screen.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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

@Composable
internal fun ProductSection(title: String, products: List<ProductItem>) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        NavigationSectionTitle(
            titleSection = title,
            onSeeAllClick = {}
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            items(products) { product ->
                ProductCard(product)
            }
        }
    }
}


@Composable
private fun ProductCard(item: ProductItem) {
    AppCard(
        Modifier
            .width(160.dp)
            .height(200.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            BodySmallText(item.name)
            Text(item.price, fontWeight = FontWeight.Bold)
        }
    }
}

data class ProductItem(
    val imageRes: Int,
    val name: String,
    val price: String
)

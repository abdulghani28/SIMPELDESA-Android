package com.cvindosistem.simpeldesa.main.presentation.screens.main.home.section

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.NavigationSectionTitle

data class BlogItem(val imageRes: Int, val category: String, val title: String)

@Composable
internal fun BlogSection(
    blogList: List<BlogItem>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        NavigationSectionTitle(
            titleSection = "Blog Terbaru 🔍",
            onSeeAllClick = onSeeAllClick
        )

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(blogList) { blog ->
                BlogCard(blog)
            }
        }
    }
}

@Composable
private fun BlogCard(blog: BlogItem) {
    AppCard(
        Modifier.width(260.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = blog.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    blog.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF3F51B5),
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                BodyMediumText(
                    blog.title,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
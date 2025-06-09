package com.cvindosistem.simpeldesa.main.presentation.screens.blogdesa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.AppTopBar

@Composable
fun BlogDesaScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }

    val categories = listOf("Berita Desa", "Potensi Desa", "Pemberdayaan Masyarakat")
    val allBlogPosts = getAllBlogPosts()

    val filteredPosts = remember(searchQuery, selectedCategory) {
        allBlogPosts.filter { post ->
            val matchesSearch = searchQuery.isEmpty() ||
                    post.title.contains(searchQuery, ignoreCase = true) ||
                    post.description.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == "Semua" || post.category == selectedCategory
            matchesSearch && matchesCategory
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Blog Desa",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Bar
            item {
                AppSearchBarAndFilter(
                    value = searchQuery,
                    onValueSearch = { searchQuery = it },
                    placeholder = "Cari...",
                    onFilterClick = {},
                    showFilter = false
                )
            }

            // Category Chips
            item {
                CategoryChips(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Latest Posts Section
            item {
                SectionTitle(
                    title = "Postingan Terbaru",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Blog Posts
            val latestPosts = filteredPosts.take(2)
            items(latestPosts) { post ->
                BlogPostCard(
                    post = post,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Popular Posts Section
            if (filteredPosts.size > 2) {
                item {
                    SectionTitle(
                        title = "Postingan Terpopuler",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                val popularPosts = filteredPosts.drop(2)
                items(popularPosts) { post ->
                    BlogPostCard(
                        post = post,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                selected = selectedCategory == category,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedCategory == category,
                    borderColor = if (selectedCategory == category)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
private fun BlogPostCard(
    post: BlogPost,
    modifier: Modifier = Modifier
) {
    AppCard {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Image
            AsyncImage(
                model = post.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Category
                    Text(
                        text = post.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Title
                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Description
                    Text(
                        text = post.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// Data Classes
data class BlogPost(
    val id: Int,
    val imageRes: Int,
    val category: String,
    val title: String,
    val description: String
)

// Sample Data
private fun getAllBlogPosts(): List<BlogPost> {
    return listOf(
        BlogPost(
            id = 1,
            imageRes = R.drawable.sample_blog,
            category = "Berita Desa",
            title = "Cara Efektif Meningkatkan Hasil Pertanian di Desa",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec. Nam pulvinar a felis ultricies tristique. Sed vitae volutpat vitae r..."
        ),
        BlogPost(
            id = 2,
            imageRes = R.drawable.sample_blog,
            category = "Potensi Desa",
            title = "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru",
            description = "Desa Sukaramai Baru memiliki potensi alam yang luar biasa dengan pemandangan yang memukau dan tradisi yang masih terjaga dengan baik..."
        ),
        BlogPost(
            id = 3,
            imageRes = R.drawable.sample_blog,
            category = "Pemberdayaan Masyarakat",
            title = "Kisah Sukses Pemberdayaan Masyarakat Bersama UMKM",
            description = "Program pemberdayaan masyarakat melalui UMKM telah memberikan dampak positif bagi perekonomian desa..."
        ),
        BlogPost(
            id = 4,
            imageRes = R.drawable.sample_blog,
            category = "Pertanian & Kehutanan",
            title = "Cara Efektif Meningkatkan Hasil Pertanian di Desa",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec. Nam pulvinar a felis ultricies tristique. Sed vitae volutpat vitae r..."
        ),
        BlogPost(
            id = 5,
            imageRes = R.drawable.sample_blog,
            category = "Potensi Desa",
            title = "Keindahan Alam dan Kekayaan Tradisi di Desa Sukaramai Baru",
            description = "Desa dengan panorama alam yang indah dan budaya tradisional yang masih lestari..."
        ),
        BlogPost(
            id = 6,
            imageRes = R.drawable.sample_blog,
            category = "Pemberdayaan Masyarakat",
            title = "Kisah Sukses Pemberdayaan Masyarakat Bersama UMKM",
            description = "Transformasi ekonomi desa melalui pengembangan usaha mikro, kecil, dan menengah..."
        )
    )
}

@Composable
fun AsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    // Placeholder for AsyncImage - replace with actual implementation
    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.surfaceVariant,
            RoundedCornerShape(8.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
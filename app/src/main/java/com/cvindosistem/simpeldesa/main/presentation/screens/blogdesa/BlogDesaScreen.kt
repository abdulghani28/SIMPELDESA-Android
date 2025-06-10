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
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.BodyLargeText
import com.cvindosistem.simpeldesa.core.components.BodyMediumText
import com.cvindosistem.simpeldesa.core.components.CategoryChips
import com.cvindosistem.simpeldesa.core.components.LabelSmallText
import com.cvindosistem.simpeldesa.core.components.LargeText
import com.cvindosistem.simpeldesa.core.components.SectionTitle

@Composable
fun BlogDesaScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }

    // Tambahkan "Semua" ke daftar kategori
    val categories = listOf("Semua", "Berita Desa", "Potensi Desa", "Pemberdayaan Masyarakat", "Teknologi & Inovasi")
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

    // Tentukan apakah menggunakan layout filter (ketika kategori bukan "Semua")
    val isFilterLayout = selectedCategory != "Semua"

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
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(paddingValues)
                .padding(24.dp),
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
                    onCategorySelected = { selectedCategory = it }
                )
            }

            if (isFilterLayout) {
                // Layout untuk kategori yang dipilih (filter aktif)
                item {
                    Text(
                        text = "Kategori $selectedCategory",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // Grid layout dengan 2 kolom untuk tampilan filter
                val chunkedPosts = filteredPosts.chunked(2)
                items(chunkedPosts) { rowPosts ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowPosts.forEach { post ->
                            FilterBlogPostCard(
                                post = post,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Tambahkan spacer jika hanya ada 1 item di row terakhir
                        if (rowPosts.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            } else {
                // Layout default (semua kategori)
                // Latest Posts Section
                item {
                    SectionTitle(
                        title = "Postingan Terbaru"
                    )
                }

                // Featured Blog Post (Large Card with Description)
                val featuredPost = filteredPosts.firstOrNull()
                if (featuredPost != null) {
                    item {
                        FeaturedBlogPostCard(
                            post = featuredPost,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Popular Posts Section
                if (filteredPosts.size > 1) {
                    // Horizontal Scrollable Cards
                    item {
                        val popularPosts = filteredPosts.drop(1)
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 0.dp)
                        ) {
                            items(popularPosts) { post ->
                                CompactBlogPostCard(
                                    post = post,
                                    modifier = Modifier.width(280.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Card untuk tampilan filter (grid 2 kolom)
@Composable
private fun FilterBlogPostCard(
    post: BlogPost,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Column {
            // Image
            AsyncImage(
                model = post.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Title
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Description
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// Featured Blog Post Card (Large with description)
@Composable
private fun FeaturedBlogPostCard(
    post: BlogPost,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Column {
            // Image
            AsyncImage(
                model = post.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Category
                Text(
                    text = post.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Title
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

// Compact Blog Post Card (Horizontal scrollable, title only)
@Composable
private fun CompactBlogPostCard(
    post: BlogPost,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Column {
            // Image
            AsyncImage(
                model = post.imageRes,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Content
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Category
                Text(
                    text = post.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Title (no description for compact cards)
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
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

// Sample Data dengan kategori "Teknologi & Inovasi" ditambahkan
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
            category = "Teknologi & Inovasi",
            title = "Memanfaatkan Teknologi untuk Pembangunan Desa",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec. Nam pulvinar a felis ultricies tristique. Sed vitae volutpat vitae r..."
        ),
        BlogPost(
            id = 5,
            imageRes = R.drawable.sample_blog,
            category = "Teknologi & Inovasi",
            title = "Teknologi Pertanian yang Meningkatkan Produktivitas Petani Desa",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec..."
        ),
        BlogPost(
            id = 6,
            imageRes = R.drawable.sample_blog,
            category = "Teknologi & Inovasi",
            title = "Membangun Koneksi untuk Mempercepat Pembangunan Ekonomi",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec..."
        ),
        BlogPost(
            id = 7,
            imageRes = R.drawable.sample_blog,
            category = "Teknologi & Inovasi",
            title = "Solusi Energi Ramah Lingkungan untuk Desa",
            description = "Eget pellentesque tortor a vel justo id ultrices. Venenatis in sed semper donec. Nam pulvinar a felis ultricies tristique. Sed vitae volutpat vitae r..."
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
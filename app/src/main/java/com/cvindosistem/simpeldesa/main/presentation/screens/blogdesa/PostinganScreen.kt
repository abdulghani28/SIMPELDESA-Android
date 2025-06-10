package com.cvindosistem.simpeldesa.main.presentation.screens.blogdesa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.R
import com.cvindosistem.simpeldesa.core.components.AnimatedTabContent
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.AppTab
import com.cvindosistem.simpeldesa.core.components.AppTopBar
import com.cvindosistem.simpeldesa.core.components.DropdownField
import com.cvindosistem.simpeldesa.main.navigation.Screen

@Composable
fun PostinganScreen(
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    @Composable
    fun PostinganTabContent(
        tabIndex: Int,
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        selectedTimeFilter: String,
        onTimeFilterChange: (String) -> Unit,
        timeFilterOptions: List<String>,
        modifier: Modifier = Modifier
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceBright)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Search Bar
            item {
                AppSearchBarAndFilter(
                    value = searchQuery,
                    onValueSearch = onSearchQueryChange,
                    placeholder = "Cari Keyword",
                    onFilterClick = { },
                    showFilter = false
                )
                DropdownField(
                    label = "",
                    value = selectedTimeFilter,
                    onValueChange = onTimeFilterChange,
                    options = timeFilterOptions,
                    isError = false,
                    errorMessage = null
                )
            }

            // Post Items based on selected tab
            val posts = when (tabIndex) {
                0 -> getAktifPosts() // Aktif posts
                1 -> getDraftPosts() // Draft posts
                2 -> getArsipPosts() // Arsip posts
                else -> emptyList()
            }

            items(posts) { post ->
                PostinganItemCard(
                    post = post,
                    showStats = tabIndex == 0, // Only show stats for Aktif tab
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    var searchQuery by remember { mutableStateOf("") }
    var selectedTimeFilter by remember { mutableStateOf("Semua Waktu") }

    val tabs = listOf("Aktif", "Draft", "Arsip")
    val timeFilterOptions = listOf("Semua Waktu", "Hari Ini", "Minggu Ini", "Bulan Ini", "Tahun Ini")

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Postingan",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() },
                    showCreate = true,
                    onCreateClick = { navController.navigate(Screen.BuatPostingan.route) }
                )
                AppTab(
                    selectedTab = selectedTab,
                    tabs = tabs,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            AnimatedTabContent(
                selectedTab = selectedTab,
                paddingValues = PaddingValues(0.dp)
            ) { tabIndex, modifier ->
                PostinganTabContent(
                    tabIndex = tabIndex,
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    selectedTimeFilter = selectedTimeFilter,
                    onTimeFilterChange = { selectedTimeFilter = it },
                    timeFilterOptions = timeFilterOptions,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun PostinganItemCard(
    post: PostinganItem,
    showStats: Boolean = true,
    modifier: Modifier = Modifier
) {
    AppCard(modifier) {
        Row(
            modifier = Modifier.padding(16.dp)
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

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Date
                Text(
                    text = post.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

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
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                if (showStats) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Stats Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Views
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_eye),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = post.views.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Comments
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_comment),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = post.comments.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Love
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_love),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = post.loves.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // More options button
            IconButton(
                onClick = { /* Show more options */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Data class for PostinganItem
data class PostinganItem(
    val id: Int,
    val imageRes: Int,
    val date: String,
    val category: String,
    val title: String,
    val views: Int = 0,
    val comments: Int = 0,
    val loves: Int = 0
)

// Sample data for different tabs
private fun getAktifPosts(): List<PostinganItem> {
    return listOf(
        PostinganItem(
            id = 1,
            imageRes = R.drawable.sample_blog,
            date = "04/12/2024",
            category = "Teknologi & Inovasi",
            title = "Memanfaatkan Teknologi untuk Pembangunan Desa",
            views = 2721,
            comments = 21,
            loves = 27
        ),
        PostinganItem(
            id = 2,
            imageRes = R.drawable.sample_blog,
            date = "04/12/2024",
            category = "Teknologi & Inovasi",
            title = "Teknologi Pertanian yang Meningkatkan Produktivitas Petani Desa",
            views = 2721,
            comments = 21,
            loves = 27
        ),
        PostinganItem(
            id = 3,
            imageRes = R.drawable.sample_blog,
            date = "04/12/2024",
            category = "Teknologi & Inovasi",
            title = "Membangun Koneksi untuk Mempercepat Pembangunan Ekonomi",
            views = 2721,
            comments = 21,
            loves = 27
        ),
        PostinganItem(
            id = 4,
            imageRes = R.drawable.sample_blog,
            date = "04/12/2024",
            category = "Teknologi & Inovasi",
            title = "Solusi Energi Ramah Lingkungan untuk Desa",
            views = 2721,
            comments = 21,
            loves = 27
        ),
        PostinganItem(
            id = 5,
            imageRes = R.drawable.sample_blog,
            date = "04/12/2024",
            category = "Teknologi & Inovasi",
            title = "Aplikasi Mobile untuk Memudahkan Akses Informasi dan Layanan",
            views = 2721,
            comments = 21,
            loves = 27
        )
    )
}

private fun getDraftPosts(): List<PostinganItem> {
    return listOf(
        PostinganItem(
            id = 6,
            imageRes = R.drawable.sample_blog,
            date = "03/12/2024",
            category = "Berita Desa",
            title = "Draft: Rencana Pembangunan Infrastruktur Desa Tahun 2025"
        ),
        PostinganItem(
            id = 7,
            imageRes = R.drawable.sample_blog,
            date = "02/12/2024",
            category = "Potensi Desa",
            title = "Draft: Potensi Wisata Alam yang Belum Tergali"
        )
    )
}

private fun getArsipPosts(): List<PostinganItem> {
    return listOf(
        PostinganItem(
            id = 8,
            imageRes = R.drawable.sample_blog,
            date = "15/11/2024",
            category = "Pemberdayaan Masyarakat",
            title = "Pelatihan Keterampilan Masyarakat yang Telah Selesai"
        ),
        PostinganItem(
            id = 9,
            imageRes = R.drawable.sample_blog,
            date = "10/11/2024",
            category = "Berita Desa",
            title = "Laporan Kegiatan Gotong Royong Bulan November"
        )
    )
}
package com.cvindosistem.simpeldesa.main.presentation.screens.main.activity.section

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cvindosistem.simpeldesa.core.components.AppCard
import com.cvindosistem.simpeldesa.core.components.AppContainer
import com.cvindosistem.simpeldesa.core.components.AppSearchBarAndFilter
import com.cvindosistem.simpeldesa.core.components.BodySmallText
import com.cvindosistem.simpeldesa.core.components.TitleMediumText
import com.cvindosistem.simpeldesa.core.components.TitleSmallText

// Models
data class PesananItem(
    val id: Int,
    val kategori: PesananKategori,
    val tanggal: String,
    val status: PesananStatus,
    val totalHarga: Int,
    // Unified fields for all types
    val produkNama: String? = null,
    val produkJumlah: Int? = null,
    val produkHarga: Int? = null,
    val nomorTelpon: String? = null,
    val deskripsi: String? = null
)

enum class PesananKategori(
    val displayName: String,
    val icon: ImageVector
) {
    BELANJA("Belanja", Icons.Default.ShoppingCart),
    PULSA_SELULER("Pulsa Seluler", Icons.Default.Phone),
    TOKEN_LISTRIK("Token Listrik", Icons.Default.Build)
}

enum class PesananStatus(
    val displayName: String,
    val backgroundColor: Color,
    val textColor: Color
) {
    MENUNGGU_KONFIRMASI(
        displayName = "Menunggu Konfirmasi",
        backgroundColor = Color(0xFFE3F2FD),
        textColor = Color(0xFF1976D2)
    ),
    DIKIRIM(
        displayName = "Dikirim",
        backgroundColor = Color(0xFFFFF3E0),
        textColor = Color(0xFFF57C00)
    ),
    SELESAI(
        displayName = "Selesai",
        backgroundColor = Color(0xFFE8F5E8),
        textColor = Color(0xFF4CAF50)
    )
}

// Main Composable
@Composable
fun AktivitasPesananTab(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatusFilter by remember { mutableStateOf("Semua Status") }
    var selectedProductFilter by remember { mutableStateOf("Semua Produk") }
    var selectedTimeFilter by remember { mutableStateOf("Semua Waktu") }

    val pesananList = remember { createSampleData() }

    val filteredPesananList = remember(searchQuery, selectedStatusFilter) {
        filterPesananList(pesananList, searchQuery, selectedStatusFilter)
    }

    AppContainer(
        background = MaterialTheme.colorScheme.surfaceBright,
        modifier = modifier
    ) {
        AppSearchBarAndFilter(
            placeholder = "Cari Keyword",
            value = searchQuery,
            onValueSearch = { searchQuery = it },
            onFilterClick = { },
            showFilter = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        ScrollableFilterRow(
            selectedStatus = selectedStatusFilter,
            selectedProduct = selectedProductFilter,
            selectedTime = selectedTimeFilter,
            onStatusChange = { selectedStatusFilter = it },
            onProductChange = { selectedProductFilter = it },
            onTimeChange = { selectedTimeFilter = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PesananList(
            pesananList = filteredPesananList,
            onItemClick = { pesanan ->
                // Handle item click
            }
        )
    }
}

// Scrollable Filter Row
@Composable
private fun ScrollableFilterRow(
    selectedStatus: String,
    selectedProduct: String,
    selectedTime: String,
    onStatusChange: (String) -> Unit,
    onProductChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        item {
            FilterChip(
                label = selectedStatus,
                onClick = { onStatusChange(getNextStatus(selectedStatus)) }
            )
        }

        item {
            FilterChip(
                label = selectedProduct,
                onClick = { onProductChange(getNextProduct(selectedProduct)) }
            )
        }

        item {
            FilterChip(
                label = selectedTime,
                onClick = { onTimeChange(getNextTime(selectedTime)) }
            )
        }
    }
}

// Filter Chip Component
@Composable
private fun FilterChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TitleSmallText(label)
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Pesanan List Component
@Composable
private fun PesananList(
    pesananList: List<PesananItem>,
    onItemClick: (PesananItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pesananList) { pesanan ->
            PesananCard(
                pesanan = pesanan,
                onClick = { onItemClick(pesanan) }
            )
        }
    }
}

// Unified Pesanan Card
@Composable
private fun PesananCard(
    pesanan: PesananItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier
            .clickable{
                onClick
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PesananCardHeader(
                kategori = pesanan.kategori,
                tanggal = pesanan.tanggal,
                status = pesanan.status
            )

            Spacer(modifier = Modifier.height(12.dp))

            PesananCardContent(pesanan = pesanan)
        }
    }
}

// Card Header
@Composable
private fun PesananCardHeader(
    kategori: PesananKategori,
    tanggal: String,
    status: PesananStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = kategori.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            Column {
                TitleSmallText(kategori.displayName)
                BodySmallText(tanggal)
            }
        }

        StatusChip(status = status)
    }
}

// Unified Card Content
@Composable
private fun PesananCardContent(
    pesanan: PesananItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Icon based on category
        CategoryIcon(kategori = pesanan.kategori)

        Column(
            modifier = Modifier.weight(1f)
        ) {
            when (pesanan.kategori) {
                PesananKategori.BELANJA -> {
                    TitleSmallText(pesanan.produkNama ?: "Produk")
                    BodySmallText("${pesanan.produkJumlah ?: 1} Produk")
                }
                PesananKategori.PULSA_SELULER -> {
                    TitleSmallText(pesanan.nomorTelpon ?: "No. Telepon")
                    BodySmallText(pesanan.deskripsi ?: "Pulsa")
                }
                PesananKategori.TOKEN_LISTRIK -> {
                    TitleSmallText("Token Listrik")
                    BodySmallText(pesanan.deskripsi ?: "Token PLN")
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (pesanan.totalHarga > 0) {
                TitleMediumText("Rp ${formatRupiah(pesanan.totalHarga)}")
            }
        }
    }
}

// Category Icon Component
@Composable
private fun CategoryIcon(
    kategori: PesananKategori,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, iconColor) = when (kategori) {
        PesananKategori.BELANJA -> Color(0xFF4CAF50) to Color.White
        PesananKategori.PULSA_SELULER -> Color(0xFFFF5722) to Color.White
        PesananKategori.TOKEN_LISTRIK -> Color(0xFFFFB74D) to Color.White
    }

    Box(
        modifier = modifier
            .size(60.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = kategori.icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(30.dp)
        )
    }
}

// Status Chip
@Composable
private fun StatusChip(
    status: PesananStatus,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = status.backgroundColor
    ) {
        TitleSmallText(
            status.displayName,
            color = status.textColor
        )
    }
}

// Helper Functions
private fun createSampleData(): List<PesananItem> {
    return listOf(
        PesananItem(
            id = 1,
            kategori = PesananKategori.BELANJA,
            tanggal = "08/12/2024",
            status = PesananStatus.MENUNGGU_KONFIRMASI,
            totalHarga = 330000,
            produkNama = "Sensatia Botanicals Relaxation Massage",
            produkJumlah = 2,
            produkHarga = 330000
        ),
        PesananItem(
            id = 2,
            kategori = PesananKategori.BELANJA,
            tanggal = "08/12/2024",
            status = PesananStatus.DIKIRIM,
            totalHarga = 155000,
            produkNama = "CeraVe Moisturiser",
            produkJumlah = 1,
            produkHarga = 155000
        ),
        PesananItem(
            id = 3,
            kategori = PesananKategori.PULSA_SELULER,
            tanggal = "08/12/2024",
            status = PesananStatus.SELESAI,
            totalHarga = 51000,
            nomorTelpon = "081234567890",
            deskripsi = "Pulsa Telkomsel Rp 50,000"
        ),
        PesananItem(
            id = 4,
            kategori = PesananKategori.TOKEN_LISTRIK,
            tanggal = "08/12/2024",
            status = PesananStatus.SELESAI,
            totalHarga = 20000,
            deskripsi = "Token PLN Rp 20,000"
        )
    )
}

private fun filterPesananList(
    pesananList: List<PesananItem>,
    searchQuery: String,
    statusFilter: String
): List<PesananItem> {
    return pesananList.filter { pesanan ->
        val matchesSearch = if (searchQuery.isEmpty()) {
            true
        } else {
            pesanan.produkNama?.contains(searchQuery, ignoreCase = true) == true ||
                    pesanan.nomorTelpon?.contains(searchQuery, ignoreCase = true) == true ||
                    pesanan.deskripsi?.contains(searchQuery, ignoreCase = true) == true
        }

        val matchesStatus = statusFilter == "Semua Status" ||
                pesanan.status.displayName == statusFilter

        matchesSearch && matchesStatus
    }
}

private fun getNextStatus(current: String): String {
    val statuses = listOf("Semua Status", "Menunggu Konfirmasi", "Dikirim", "Selesai")
    val currentIndex = statuses.indexOf(current)
    return statuses[(currentIndex + 1) % statuses.size]
}

private fun getNextProduct(current: String): String {
    val products = listOf("Semua Produk", "Belanja", "Pulsa Seluler", "Token Listrik")
    val currentIndex = products.indexOf(current)
    return products[(currentIndex + 1) % products.size]
}

private fun getNextTime(current: String): String {
    val times = listOf("Semua Waktu", "Hari Ini", "Minggu Ini", "Bulan Ini")
    val currentIndex = times.indexOf(current)
    return times[(currentIndex + 1) % times.size]
}

@SuppressLint("DefaultLocale")
private fun formatRupiah(amount: Int): String {
    return String.format("%,d", amount).replace(',', '.')
}